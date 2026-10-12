package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class AutoJs4ExecutionRequestParserTest {

    private val scriptPath = File(System.getProperty("java.io.tmpdir"), "autojs4/main.js").absolutePath
    private val workingDirectory = File(System.getProperty("java.io.tmpdir"), "autojs4").absolutePath

    private fun values(
        protocolVersion: Int = AutoJs4RuntimeContract.PROTOCOL_VERSION,
        executionId: String? = "autojs4-engine-1-abc",
        sourceType: String? = AutoJs4RuntimeContract.SOURCE_TYPE_JAVASCRIPT,
        scriptPath: String? = this.scriptPath,
        workingDirectory: String? = this.workingDirectory,
        projectDirectory: String? = null,
        arguments: Map<String, Any?>? = null,
        delay: Long = 0L,
        interval: Long = 0L,
        loopTimes: Int = 1,
    ) = AutoJs4RequestValues(
        protocolVersion, executionId, sourceType, scriptPath, workingDirectory, projectDirectory,
        arguments, delay, interval, loopTimes,
    )

    @Test
    fun normalizesAValidJavaScriptRequest() {
        val request = AutoJs4ExecutionRequestParser.normalize(
            values(arguments = mapOf("name" to "value", "count" to 3, "flag" to true, "ratio" to 0.5, "big" to 7L)),
        )
        assertEquals("autojs4-engine-1-abc", request.executionId)
        assertEquals(AutoJs4RuntimeContract.SOURCE_TYPE_JAVASCRIPT, request.sourceType)
        assertEquals(scriptPath, request.scriptPath)
        assertEquals(workingDirectory, request.workingDirectory)
        assertNull(request.projectDirectory)
        assertEquals(5, request.arguments.size)
        assertEquals("value", request.arguments["name"])
        assertEquals(1, request.loopTimes)
    }

    @Test
    fun acceptsAutoFilesProjectsAndUnlimitedLoops() {
        val request = AutoJs4ExecutionRequestParser.normalize(
            values(
                sourceType = AutoJs4RuntimeContract.SOURCE_TYPE_AUTO,
                projectDirectory = workingDirectory,
                delay = 500L,
                interval = 1_000L,
                loopTimes = 0,
            ),
        )
        assertEquals(AutoJs4RuntimeContract.SOURCE_TYPE_AUTO, request.sourceType)
        assertEquals(workingDirectory, request.projectDirectory)
        assertEquals(500L, request.delay)
        assertEquals(1_000L, request.interval)
        assertEquals(0, request.loopTimes)
    }

    @Test
    fun rejectsProtocolAndIdentityProblems() {
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(protocolVersion = 2))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(executionId = "has space"))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(executionId = "x".repeat(129)))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(sourceType = "python"))
        }
        assertTrue(AutoJs4ExecutionRequestParser.isValidExecutionId("a.b_c-D9"))
        assertFalse(AutoJs4ExecutionRequestParser.isValidExecutionId(""))
        assertFalse(AutoJs4ExecutionRequestParser.isValidExecutionId("slash/in/id"))
    }

    @Test
    fun rejectsInvalidPathsTimingAndArguments() {
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(scriptPath = "relative/main.js"))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(workingDirectory = ""))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(scriptPath = "$scriptPath\u0000"))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(delay = -1L))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(loopTimes = -1))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(arguments = mapOf("object" to listOf(1))))
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(
                values(arguments = (0..AutoJs4RuntimeContract.MAX_ARGUMENTS).associate { "k$it" to it }),
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(
                values(arguments = mapOf("long" to "x".repeat(AutoJs4RuntimeContract.MAX_ARGUMENT_VALUE_BYTES + 1))),
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            AutoJs4ExecutionRequestParser.normalize(values(arguments = mapOf("bad" to "\uD83D")))
        }
    }
}
