package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.os.Bundle
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import java.io.File

internal data class AutoJs4ExecutionRequest(
    val executionId: String,
    val sourceType: String,
    val scriptPath: String,
    val workingDirectory: String,
    val projectDirectory: String?,
    val arguments: Map<String, Any>,
    val delay: Long,
    val interval: Long,
    val loopTimes: Int,
)

/** Values extracted from a request Bundle before validation; keeps normalization testable on the JVM. */
internal data class AutoJs4RequestValues(
    val protocolVersion: Int,
    val executionId: String?,
    val sourceType: String?,
    val scriptPath: String?,
    val workingDirectory: String?,
    val projectDirectory: String?,
    val arguments: Map<String, Any?>?,
    val delay: Long,
    val interval: Long,
    val loopTimes: Int,
)

internal object AutoJs4ExecutionRequestParser {
    private val executionIdPattern = Regex("[A-Za-z0-9._-]{1,${AutoJs4RuntimeContract.MAX_EXECUTION_ID_LENGTH}}")
    private val sourceTypes = setOf(
        AutoJs4RuntimeContract.SOURCE_TYPE_JAVASCRIPT,
        AutoJs4RuntimeContract.SOURCE_TYPE_AUTO,
    )
    private val allowedKeys = setOf(
        AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION,
        AutoJs4RuntimeContract.KEY_EXECUTION_ID,
        AutoJs4RuntimeContract.KEY_SOURCE_TYPE,
        AutoJs4RuntimeContract.KEY_SCRIPT_PATH,
        AutoJs4RuntimeContract.KEY_WORKING_DIRECTORY,
        AutoJs4RuntimeContract.KEY_PROJECT_DIRECTORY,
        AutoJs4RuntimeContract.KEY_ARGUMENTS,
        AutoJs4RuntimeContract.KEY_DELAY,
        AutoJs4RuntimeContract.KEY_INTERVAL,
        AutoJs4RuntimeContract.KEY_LOOP_TIMES,
    )

    fun isValidExecutionId(value: String): Boolean = executionIdPattern.matches(value)

    fun parse(request: Bundle?): AutoJs4ExecutionRequest {
        requireNotNull(request) { "Request is missing" }
        require(request.keySet().all(allowedKeys::contains)) { "Request contains unsupported keys" }
        val arguments = request.getBundle(AutoJs4RuntimeContract.KEY_ARGUMENTS)?.let { bundle ->
            bundle.keySet().associateWith { key ->
                @Suppress("DEPRECATION")
                bundle.get(key)
            }
        }
        return normalize(
            AutoJs4RequestValues(
                protocolVersion = request.getInt(AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION, -1),
                executionId = request.getString(AutoJs4RuntimeContract.KEY_EXECUTION_ID),
                sourceType = request.getString(AutoJs4RuntimeContract.KEY_SOURCE_TYPE),
                scriptPath = request.getString(AutoJs4RuntimeContract.KEY_SCRIPT_PATH),
                workingDirectory = request.getString(AutoJs4RuntimeContract.KEY_WORKING_DIRECTORY),
                projectDirectory = request.getString(AutoJs4RuntimeContract.KEY_PROJECT_DIRECTORY),
                arguments = arguments,
                delay = request.getLong(AutoJs4RuntimeContract.KEY_DELAY, 0L),
                interval = request.getLong(AutoJs4RuntimeContract.KEY_INTERVAL, 0L),
                loopTimes = request.getInt(AutoJs4RuntimeContract.KEY_LOOP_TIMES, AutoJs4RuntimeContract.DEFAULT_LOOP_TIMES),
            ),
        )
    }

    fun normalize(values: AutoJs4RequestValues): AutoJs4ExecutionRequest {
        require(values.protocolVersion == AutoJs4RuntimeContract.PROTOCOL_VERSION) {
            "Unsupported AutoJs4 runtime protocol version"
        }
        val executionId = values.executionId.orEmpty()
        require(isValidExecutionId(executionId)) { "Invalid execution ID" }
        val sourceType = values.sourceType.orEmpty()
        require(sourceType in sourceTypes) { "Unsupported source type" }
        val scriptPath = requirePath(values.scriptPath, "Script path")
        val workingDirectory = requirePath(values.workingDirectory, "Working directory")
        val projectDirectory = values.projectDirectory?.let { requirePath(it, "Project directory") }
        require(values.delay >= 0L) { "Invalid delay" }
        require(values.interval >= 0L) { "Invalid interval" }
        require(values.loopTimes >= 0) { "Invalid loop times" }
        val arguments = normalizeArguments(values.arguments)
        return AutoJs4ExecutionRequest(
            executionId = executionId,
            sourceType = sourceType,
            scriptPath = scriptPath,
            workingDirectory = workingDirectory,
            projectDirectory = projectDirectory,
            arguments = arguments,
            delay = values.delay,
            interval = values.interval,
            loopTimes = values.loopTimes,
        )
    }

    private fun requirePath(value: String?, label: String): String {
        require(!value.isNullOrBlank()) { "$label is missing" }
        require('\u0000' !in value) { "$label cannot contain NUL" }
        require(!value.hasUnpairedSurrogate()) { "$label contains invalid Unicode" }
        require(value.toByteArray(Charsets.UTF_8).size <= AutoJs4RuntimeContract.MAX_PATH_BYTES) {
            "$label is too long"
        }
        require(File(value).isAbsolute) { "$label must be absolute" }
        return value
    }

    private fun normalizeArguments(arguments: Map<String, Any?>?): Map<String, Any> {
        if (arguments == null) return emptyMap()
        require(arguments.size <= AutoJs4RuntimeContract.MAX_ARGUMENTS) { "Too many arguments" }
        val normalized = LinkedHashMap<String, Any>(arguments.size)
        for ((key, value) in arguments) {
            require(key.isNotEmpty() && key.toByteArray(Charsets.UTF_8).size <= AutoJs4RuntimeContract.MAX_ARGUMENT_KEY_BYTES) {
                "Invalid argument key"
            }
            require('\u0000' !in key && !key.hasUnpairedSurrogate()) { "Invalid argument key" }
            val accepted: Any = when (value) {
                is String -> {
                    require(value.toByteArray(Charsets.UTF_8).size <= AutoJs4RuntimeContract.MAX_ARGUMENT_VALUE_BYTES) {
                        "Argument value is too long: $key"
                    }
                    require(!value.hasUnpairedSurrogate()) { "Argument value contains invalid Unicode: $key" }
                    value
                }
                is Boolean, is Int, is Long, is Double -> value
                else -> throw IllegalArgumentException("Unsupported argument type: $key")
            }
            normalized[key] = accepted
        }
        return normalized
    }

    private fun String.hasUnpairedSurrogate(): Boolean {
        var index = 0
        while (index < length) {
            val character = this[index]
            when {
                character.isHighSurrogate() -> {
                    if (index + 1 >= length || !this[index + 1].isLowSurrogate()) return true
                    index += 2
                }
                character.isLowSurrogate() -> return true
                else -> index++
            }
        }
        return false
    }
}
