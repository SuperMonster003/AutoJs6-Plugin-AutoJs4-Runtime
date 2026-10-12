package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.stardust.autojs.ScriptEngineService
import com.stardust.autojs.execution.ExecutionConfig
import com.stardust.autojs.execution.ScriptExecution
import com.stardust.autojs.execution.ScriptExecutionListener
import com.stardust.autojs.project.ProjectConfig
import com.stardust.autojs.runtime.exception.ScriptInterruptedException
import com.stardust.autojs.script.AutoFileSource
import com.stardust.autojs.script.JavaScriptFileSource
import com.stardust.autojs.script.JavaScriptSource
import com.stardust.autojs.script.ScriptSource
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui.SettingsActivity
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimeCallback
import org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimePlugin
import org.autojs.plugin.common.api.PluginInfo
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Protocol V1 runtime service. `runScript` admits a request and starts it through AutoJs4's own
 * `ScriptEngineService`; everything afterwards (start, console lines, completion) travels through
 * the oneway callback. See `docs/dev/autojs4-runtime-protocol-v1.md` in the AutoJs6 repository.
 *
 * zh-CN: 协议 V1 运行时服务. `runScript` 受理请求并经 AutoJs4 自己的 `ScriptEngineService` 启动执行,
 * 之后的开始, 控制台行与结束都通过 oneway 回调传递. 契约见 AutoJs6 仓库的
 * `docs/dev/autojs4-runtime-protocol-v1.md`.
 */
class AutoJs4RuntimeService : Service() {

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onBind(intent: Intent?): IBinder = binder

    private val binder = object : IAutoJs4RuntimePlugin.Stub() {
        override fun getInfo(): PluginInfo {
            enforcePluginCaller()
            return applicationContext.autoJs4PluginInfo()
        }

        override fun getRuntimeInfo(): Bundle {
            enforcePluginCaller()
            return runtimeInfo()
        }

        override fun cancelScript(executionId: String?): Boolean {
            enforcePluginCaller()
            if (executionId == null || !AutoJs4ExecutionRequestParser.isValidExecutionId(executionId)) {
                return false
            }
            val record = ExecutionRegistry[executionId]
            if (record == null) {
                ExecutionRegistry.rememberPendingCancellation(executionId)
                return true
            }
            requestStop(record)
            return true
        }

        override fun runScript(requestBundle: Bundle?, callback: IAutoJs4RuntimeCallback?): Bundle {
            enforcePluginCaller()
            var executionId = ""
            try {
                val request = AutoJs4ExecutionRequestParser.parse(requestBundle)
                executionId = request.executionId
                requireNotNull(callback) { "Runtime callback is missing" }
                return admit(request, callback)
            } catch (error: IllegalArgumentException) {
                return rejection(executionId, AutoJs4RuntimeContract.ERROR_INVALID_REQUEST, error.message ?: "Invalid AutoJs4 request")
            } catch (error: Throwable) {
                Log.e(TAG, "AutoJs4 admission failed for $executionId", error)
                return rejection(executionId, AutoJs4RuntimeContract.ERROR_INTERNAL, error.message ?: error.toString())
            }
        }
    }

    private fun admit(request: AutoJs4ExecutionRequest, callback: IAutoJs4RuntimeCallback): Bundle {
        val executionId = request.executionId
        if (ExecutionRegistry.consumePendingCancellation(executionId)) {
            return rejection(executionId, AutoJs4RuntimeContract.ERROR_CANCELLED, "Execution was cancelled before dispatch")
        }
        if (!AutoJs4Runtime.isInitialized || ScriptEngineService.getInstance() == null) {
            return rejection(executionId, AutoJs4RuntimeContract.ERROR_RUNTIME_UNAVAILABLE, "AutoJs4 engine is not initialised")
        }
        val scriptFile = File(request.scriptPath)
        val needsStorage = !PluginPermissions.isAppPrivatePath(this, request.scriptPath) ||
            !PluginPermissions.isAppPrivatePath(this, request.workingDirectory)
        if (needsStorage && !PluginPermissions.hasStoragePermission(this)) {
            openPermissionGuide(AutoJs4RuntimeContract.PERMISSION_STORAGE)
            return rejection(
                executionId,
                AutoJs4RuntimeContract.ERROR_PERMISSION_DENIED,
                "AutoJs4 Runtime has no storage permission",
            ).apply { putString(AutoJs4RuntimeContract.KEY_PERMISSION, AutoJs4RuntimeContract.PERMISSION_STORAGE) }
        }
        if (!scriptFile.isFile || !scriptFile.canRead()) {
            return rejection(executionId, AutoJs4RuntimeContract.ERROR_FILE_NOT_FOUND, "Script file is not readable: ${request.scriptPath}")
        }
        val projectConfig = request.projectDirectory?.let { projectDirectory ->
            runCatching { ProjectConfig.fromProjectDir(projectDirectory) }.getOrNull()
                ?: return rejection(executionId, AutoJs4RuntimeContract.ERROR_PROJECT_INVALID, "project.json is missing or invalid: $projectDirectory")
        }
        if (ExecutionRegistry.activeCount >= AutoJs4RuntimeContract.MAX_CONCURRENT_EXECUTIONS) {
            return rejection(executionId, AutoJs4RuntimeContract.ERROR_BUSY, "AutoJs4 runtime reached ${AutoJs4RuntimeContract.MAX_CONCURRENT_EXECUTIONS} concurrent executions")
        }

        val source: ScriptSource = when (request.sourceType) {
            AutoJs4RuntimeContract.SOURCE_TYPE_AUTO -> AutoFileSource(scriptFile)
            else -> JavaScriptFileSource(scriptFile)
        }
        val uiMode = (source as? JavaScriptSource)?.let { javaScriptSource ->
            runCatching { javaScriptSource.executionMode and JavaScriptSource.EXECUTION_MODE_UI != 0 }.getOrDefault(false)
        } ?: false

        val config = ExecutionConfig(
            workingDirectory = request.workingDirectory,
            delay = request.delay,
            interval = request.interval,
            loopTimes = request.loopTimes,
        )
        projectConfig?.features?.let { config.scriptConfig.features = it }
        request.arguments.forEach { (key, value) -> config.setArgument(key, value) }

        val record = ExecutionRecord(executionId, HostEventSink(callback, executionId) { onHostDied(executionId) }, uiMode)
        if (!ExecutionRegistry.register(record)) {
            record.sink.release()
            return rejection(executionId, AutoJs4RuntimeContract.ERROR_INVALID_REQUEST, "Execution ID is already active")
        }

        val listener = object : ScriptExecutionListener {
            override fun onStart(execution: ScriptExecution) {
                record.execution = execution
                ExecutionRegistry.bindEngine(record, execution.id)
                record.sink.started(record.uiMode)
                if (record.cancelRequested.get()) {
                    requestStop(record)
                }
            }

            override fun onSuccess(execution: ScriptExecution, result: Any?) {
                finish(record, succeeded = !record.cancelRequested.get(), error = null, result = result)
            }

            override fun onException(execution: ScriptExecution, e: Throwable) {
                finish(record, succeeded = false, error = e, result = null)
            }
        }

        try {
            val execution = runOnMainThread(ADMISSION_TIMEOUT_MILLIS) {
                ScriptEngineService.getInstance().execute(source, listener, config)
            }
            record.execution = execution
            ExecutionRegistry.bindEngine(record, execution.id)
            if (record.cancelRequested.get()) {
                requestStop(record)
            }
        } catch (error: Throwable) {
            ExecutionRegistry.remove(record)
            record.sink.release()
            Log.e(TAG, "AutoJs4 failed to start $executionId", error)
            return rejection(executionId, AutoJs4RuntimeContract.ERROR_INTERNAL, error.message ?: error.toString())
        }
        return acceptance(executionId, uiMode)
    }

    private fun finish(record: ExecutionRecord, succeeded: Boolean, error: Throwable?, result: Any?) {
        if (!record.finished.compareAndSet(false, true)) return
        ExecutionRegistry.remove(record)
        val cancelled = record.cancelRequested.get() || (error != null && ScriptInterruptedException.causedByInterrupted(error))
        if (error == null && succeeded) {
            record.sink.finished(
                succeeded = true,
                cancelled = false,
                durationMillis = record.durationMillis,
                result = result?.takeUnless { it == org.mozilla.javascript.Undefined.instance }?.toString(),
            )
            return
        }
        val errorCode = if (cancelled) AutoJs4RuntimeContract.ERROR_CANCELLED else AutoJs4RuntimeContract.ERROR_SCRIPT_EXCEPTION
        record.sink.finished(
            succeeded = false,
            cancelled = cancelled,
            durationMillis = record.durationMillis,
            errorCode = errorCode,
            errorMessage = error?.let { it.message ?: it.toString() } ?: "Execution was cancelled",
            errorStackTrace = error?.takeUnless { cancelled }?.let(Log::getStackTraceString),
        )
    }

    private fun requestStop(record: ExecutionRecord) {
        record.cancelRequested.set(true)
        val engine = record.execution?.engine ?: return
        mainHandler.post {
            runCatching { engine.forceStop() }
                .onFailure { Log.w(TAG, "forceStop failed for ${record.executionId}", it) }
        }
    }

    private fun onHostDied(executionId: String) {
        val record = ExecutionRegistry[executionId] ?: return
        Log.w(TAG, "Host died, stopping AutoJs4 execution $executionId")
        requestStop(record)
    }

    private fun openPermissionGuide(permission: String) {
        runCatching {
            startActivity(
                Intent(this, SettingsActivity::class.java)
                    .putExtra(SettingsActivity.EXTRA_REQUESTED_PERMISSION, permission)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            )
        }.onFailure { Log.w(TAG, "Unable to open the permission guide", it) }
    }

    private fun <T> runOnMainThread(timeoutMillis: Long, block: () -> T): T {
        if (Looper.myLooper() == Looper.getMainLooper()) return block()
        val latch = CountDownLatch(1)
        var outcome: Result<T>? = null
        mainHandler.post {
            outcome = runCatching(block)
            latch.countDown()
        }
        check(latch.await(timeoutMillis, TimeUnit.MILLISECONDS)) { "AutoJs4 main thread did not respond within ${timeoutMillis} ms" }
        return checkNotNull(outcome).getOrThrow()
    }

    private fun enforcePluginCaller() {
        enforceCallingOrSelfPermission(
            AutoJs4RuntimeContract.PLUGIN_PERMISSION,
            "AutoJs4 runtime requires the AutoJs6 plugin permission",
        )
    }

    private fun runtimeInfo(): Bundle {
        val identity = AutoJs4PluginMetadata.identity()
        val packagedAbis = applicationContext.packagedRuntimeAbis()
        return Bundle().apply {
            putInt(AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION, AutoJs4RuntimeContract.PROTOCOL_VERSION)
            putBoolean(
                AutoJs4RuntimeContract.KEY_RUNTIME_READY,
                AutoJs4Runtime.isInitialized && ScriptEngineService.getInstance() != null,
            )
            putString(AutoJs4RuntimeContract.KEY_PROCESS_NAME, currentProcessName())
            putString(AutoJs4RuntimeContract.KEY_PROCESS_ABI, Build.SUPPORTED_ABIS.firstOrNull { it in packagedAbis })
            putStringArray(AutoJs4RuntimeContract.KEY_SUPPORTED_ABIS, packagedAbis)
            putString(AutoJs4RuntimeContract.KEY_AUTOJS4_VERSION_NAME, identity.autoJs4VersionName)
            putInt(AutoJs4RuntimeContract.KEY_AUTOJS4_VERSION_CODE, identity.autoJs4VersionCode)
            putString(AutoJs4RuntimeContract.KEY_AUTOJS4_COMMIT, identity.autoJs4Commit)
            putString(AutoJs4RuntimeContract.KEY_RHINO_VERSION, identity.rhinoVersion)
            putBoolean(AutoJs4RuntimeContract.KEY_STORAGE_PERMISSION_GRANTED, PluginPermissions.hasStoragePermission(this@AutoJs4RuntimeService))
            putBoolean(AutoJs4RuntimeContract.KEY_OVERLAY_PERMISSION_GRANTED, PluginPermissions.canDrawOverlays(this@AutoJs4RuntimeService))
            putBoolean(AutoJs4RuntimeContract.KEY_ACCESSIBILITY_SERVICE_ENABLED, PluginPermissions.isAccessibilityServiceEnabled(this@AutoJs4RuntimeService))
            putInt(AutoJs4RuntimeContract.KEY_ACTIVE_EXECUTIONS, ExecutionRegistry.activeCount)
            putInt(AutoJs4RuntimeContract.KEY_MAX_CONCURRENT_EXECUTIONS, AutoJs4RuntimeContract.MAX_CONCURRENT_EXECUTIONS)
        }
    }

    private fun currentProcessName(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) return Application.getProcessName()
        return runCatching {
            File("/proc/self/cmdline").readText(Charsets.UTF_8).substringBefore('\u0000')
        }.getOrDefault("$packageName:autojs4_runtime")
    }

    private fun acceptance(executionId: String, uiMode: Boolean): Bundle = Bundle().apply {
        putInt(AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION, AutoJs4RuntimeContract.PROTOCOL_VERSION)
        putString(AutoJs4RuntimeContract.KEY_EXECUTION_ID, executionId)
        putBoolean(AutoJs4RuntimeContract.KEY_ACCEPTED, true)
        putBoolean(AutoJs4RuntimeContract.KEY_UI_MODE, uiMode)
    }

    private fun rejection(executionId: String, code: String, message: String): Bundle = Bundle().apply {
        putInt(AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION, AutoJs4RuntimeContract.PROTOCOL_VERSION)
        putString(AutoJs4RuntimeContract.KEY_EXECUTION_ID, executionId)
        putBoolean(AutoJs4RuntimeContract.KEY_ACCEPTED, false)
        putBoolean(AutoJs4RuntimeContract.KEY_UI_MODE, false)
        putString(AutoJs4RuntimeContract.KEY_ERROR_CODE, code)
        putString(AutoJs4RuntimeContract.KEY_ERROR_MESSAGE, BoundedText.truncate(message, AutoJs4RuntimeContract.MAX_TERMINAL_MESSAGE_BYTES))
    }

    private companion object {
        const val TAG = "AutoJs4RuntimeService"
        const val ADMISSION_TIMEOUT_MILLIS = 10_000L
    }
}
