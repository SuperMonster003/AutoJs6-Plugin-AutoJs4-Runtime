package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.os.SystemClock
import com.stardust.autojs.engine.RhinoJavaScriptEngine
import com.stardust.autojs.execution.ScriptExecution
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

internal class ExecutionRecord(
    val executionId: String,
    val sink: HostEventSink,
    val uiMode: Boolean,
) {
    val startedAt: Long = SystemClock.elapsedRealtime()
    val cancelRequested = AtomicBoolean(false)
    val finished = AtomicBoolean(false)

    @Volatile
    var execution: ScriptExecution? = null

    @Volatile
    var engineId: Int = ScriptExecution.NO_ID

    val durationMillis: Long
        get() = SystemClock.elapsedRealtime() - startedAt
}

/**
 * Tracks running executions and attributes AutoJs4 console lines to them. AutoJs4 has one global
 * console shared by all scripts, so a line is mapped back to its execution through the Rhino
 * context of the calling thread (`RhinoJavaScriptEngine.getEngineOfContext`), then through the
 * `ScriptThread-<id>` thread name AutoJs4 assigns to non-UI scripts, and finally to the only
 * active execution when exactly one is running.
 *
 * zh-CN: 跟踪运行中的执行并把 AutoJs4 控制台行归属到对应执行. AutoJs4 的全局控制台由全部脚本共享,
 * 因此依次通过当前线程的 Rhino Context (`RhinoJavaScriptEngine.getEngineOfContext`), AutoJs4 为非
 * UI 脚本设置的 `ScriptThread-<id>` 线程名, 以及 "仅有一个执行时归属给它" 三步定位.
 */
internal object ExecutionRegistry {
    private val records = ConcurrentHashMap<String, ExecutionRecord>()
    private val recordsByEngineId = ConcurrentHashMap<Int, ExecutionRecord>()
    private val pendingCancellations = ConcurrentHashMap<String, Long>()
    private val scriptThreadPattern = Regex("ScriptThread-(\\d+)\\b.*")

    val activeCount: Int
        get() = records.size

    fun register(record: ExecutionRecord): Boolean = records.putIfAbsent(record.executionId, record) == null

    operator fun get(executionId: String): ExecutionRecord? = records[executionId]

    fun bindEngine(record: ExecutionRecord, engineId: Int) {
        if (engineId == ScriptExecution.NO_ID) return
        record.engineId = engineId
        recordsByEngineId[engineId] = record
    }

    fun remove(record: ExecutionRecord) {
        records.remove(record.executionId, record)
        if (record.engineId != ScriptExecution.NO_ID) {
            recordsByEngineId.remove(record.engineId, record)
        }
    }

    fun rememberPendingCancellation(executionId: String) {
        prunePendingCancellations()
        if (pendingCancellations.size < MAX_PENDING_CANCELLATIONS) {
            pendingCancellations[executionId] = SystemClock.elapsedRealtime()
        }
    }

    fun consumePendingCancellation(executionId: String): Boolean {
        val recordedAt = pendingCancellations.remove(executionId) ?: return false
        return SystemClock.elapsedRealtime() - recordedAt <= AutoJs4RuntimeContract.PENDING_CANCELLATION_TTL_MILLIS
    }

    fun forwardLog(level: Int, text: String) {
        val record = resolveCurrent() ?: return
        if (record.finished.get()) return
        record.sink.log(level, text)
    }

    private fun resolveCurrent(): ExecutionRecord? {
        if (records.isEmpty()) return null
        runCatching {
            org.mozilla.javascript.Context.getCurrentContext()?.let { rhinoContext ->
                RhinoJavaScriptEngine.getEngineOfContext(rhinoContext)?.let { engine ->
                    recordsByEngineId[engine.id]?.let { return it }
                }
            }
        }
        scriptThreadPattern.matchEntire(Thread.currentThread().name)?.let { match ->
            match.groupValues[1].toIntOrNull()?.let { engineId ->
                recordsByEngineId[engineId]?.let { return it }
            }
        }
        return records.values.singleOrNull()
    }

    private fun prunePendingCancellations() {
        val cutoff = SystemClock.elapsedRealtime() - AutoJs4RuntimeContract.PENDING_CANCELLATION_TTL_MILLIS
        pendingCancellations.entries.removeIf { it.value < cutoff }
    }

    private const val MAX_PENDING_CANCELLATIONS = 64
}
