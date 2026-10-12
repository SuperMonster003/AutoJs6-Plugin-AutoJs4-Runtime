package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimeCallback
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

/**
 * Delivers protocol V1 events of one execution to the host callback. Sequence numbers are
 * monotonic, the terminal event is emitted at most once, and a dead host Binder is reported
 * through [onHostDied] so the execution can be stopped.
 *
 * zh-CN: 把一次执行的协议 V1 事件投递给宿主回调. 序号单调递增, 终止事件最多发送一次, 宿主 Binder
 * 死亡时经 [onHostDied] 通知以便停止执行.
 */
internal class HostEventSink(
    private val callback: IAutoJs4RuntimeCallback,
    private val executionId: String,
    private val onHostDied: () -> Unit,
) {
    private val sequence = AtomicLong()
    private val closed = AtomicBoolean(false)
    private val dead = AtomicBoolean(false)
    private val deathRecipient = IBinder.DeathRecipient {
        if (dead.compareAndSet(false, true)) onHostDied()
    }

    init {
        runCatching { callback.asBinder().linkToDeath(deathRecipient, 0) }
            .onFailure { if (dead.compareAndSet(false, true)) onHostDied() }
    }

    val isClosed: Boolean
        get() = closed.get()

    fun started(uiMode: Boolean) = emit(AutoJs4RuntimeContract.EVENT_STARTED) {
        putBoolean(AutoJs4RuntimeContract.KEY_UI_MODE, uiMode)
    }

    fun log(level: Int, text: String) {
        for (chunk in BoundedText.chunk(text, AutoJs4RuntimeContract.MAX_LOG_CHUNK_BYTES)) {
            emit(AutoJs4RuntimeContract.EVENT_LOG) {
                putInt(AutoJs4RuntimeContract.KEY_LEVEL, level)
                putString(AutoJs4RuntimeContract.KEY_TEXT, chunk)
            }
        }
    }

    fun finished(
        succeeded: Boolean,
        cancelled: Boolean,
        durationMillis: Long,
        errorCode: String? = null,
        errorMessage: String? = null,
        errorStackTrace: String? = null,
        result: String? = null,
    ) {
        if (!closed.compareAndSet(false, true)) return
        emit(AutoJs4RuntimeContract.EVENT_FINISHED, allowClosed = true) {
            putBoolean(AutoJs4RuntimeContract.KEY_SUCCEEDED, succeeded)
            putBoolean(AutoJs4RuntimeContract.KEY_CANCELLED, cancelled)
            putLong(AutoJs4RuntimeContract.KEY_DURATION_MILLIS, durationMillis)
            errorCode?.let { putString(AutoJs4RuntimeContract.KEY_ERROR_CODE, it) }
            errorMessage?.let {
                putString(
                    AutoJs4RuntimeContract.KEY_ERROR_MESSAGE,
                    BoundedText.truncate(it, AutoJs4RuntimeContract.MAX_TERMINAL_MESSAGE_BYTES),
                )
            }
            errorStackTrace?.let {
                putString(
                    AutoJs4RuntimeContract.KEY_ERROR_STACK_TRACE,
                    BoundedText.truncate(it, AutoJs4RuntimeContract.MAX_STACK_TRACE_BYTES),
                )
            }
            result?.let {
                putString(
                    AutoJs4RuntimeContract.KEY_RESULT,
                    BoundedText.truncate(it, AutoJs4RuntimeContract.MAX_TERMINAL_MESSAGE_BYTES),
                )
            }
        }
        release()
    }

    /** Stops delivering events without a terminal event (host died or execution never started). */
    fun release() {
        closed.set(true)
        runCatching { callback.asBinder().unlinkToDeath(deathRecipient, 0) }
    }

    private fun emit(type: String, allowClosed: Boolean = false, fill: Bundle.() -> Unit) {
        if (dead.get()) return
        if (closed.get() && !allowClosed) return
        val event = Bundle().apply {
            putInt(AutoJs4RuntimeContract.KEY_PROTOCOL_VERSION, AutoJs4RuntimeContract.PROTOCOL_VERSION)
            putString(AutoJs4RuntimeContract.KEY_EXECUTION_ID, executionId)
            putString(AutoJs4RuntimeContract.KEY_EVENT_TYPE, type)
            putLong(AutoJs4RuntimeContract.KEY_SEQUENCE, sequence.getAndIncrement())
            putLong(AutoJs4RuntimeContract.KEY_TIMESTAMP, System.currentTimeMillis())
            fill()
        }
        try {
            callback.onEvent(event)
        } catch (error: RemoteException) {
            Log.w(TAG, "Host callback is unreachable for $executionId", error)
            if (dead.compareAndSet(false, true)) onHostDied()
        }
    }

    private companion object {
        const val TAG = "HostEventSink"
    }
}
