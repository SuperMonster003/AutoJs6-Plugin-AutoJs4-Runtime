package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.app.Activity
import android.content.Context

/**
 * AutoJs6's appearance ([HostAppearance]) for one activity, whatever its base class: the language and night mode through
 * the base context ([attach]), and a fresh snapshot on every resume that recreates the activity when it differs and the
 * page was not touched. [HostAppearanceActivity] (AppCompat) and the Compose release history page (which must extend
 * `androidx.activity.ComponentActivity`) share it.
 * zh-CN: 一个 Activity 使用的 AutoJs6 外观 ([HostAppearance]), 与其基类无关: 语言与夜间模式经 base context 生效 ([attach]),
 * 每次恢复时重新读取宿主快照, 不同且页面未被操作时重建. [HostAppearanceActivity] (AppCompat) 与 Compose 版发行历史页
 * (必须继承 `androidx.activity.ComponentActivity`) 共用.
 */
internal class HostAppearanceSession(private val activity: Activity) {

    private lateinit var systemContext: Context
    lateinit var appearance: HostAppearance
        private set
    val isAttached: Boolean get() = ::appearance.isInitialized
    private var generation = 0
    private var interacted = false

    /** Call from `attachBaseContext`: the base context in the host's language and night mode. zh-CN: 在 `attachBaseContext` 中调用. */
    fun attach(newBase: Context): Context {
        systemContext = newBase
        appearance = HostAppearance.resolve(newBase)
        return appearance.wrap(newBase)
    }

    fun onResume() {
        interacted = false
        val current = ++generation
        val app = activity.applicationContext
        HostAppearance.worker.execute {
            val host = HostAppearance.read(app)
            activity.runOnUiThread {
                if (current != generation || activity.isFinishing || activity.isDestroyed) return@runOnUiThread
                // A missing/disabled host clears the previous snapshot instead of retaining it forever.
                HostAppearance.cached = host
                if (!interacted && appearance != HostAppearance.resolve(systemContext, host)) activity.recreate()
            }
        }
    }

    fun onUserInteraction() {
        interacted = true
    }

    fun onPause() {
        generation++
    }

}

/** A color attribute of this context's theme, [fallback] when the theme has none. zh-CN: 当前主题的颜色属性, 没有时为 [fallback]. */
internal fun Context.themeColor(attribute: Int, fallback: Int): Int {
    val attributes = theme.obtainStyledAttributes(intArrayOf(attribute))
    return try {
        attributes.getColor(0, fallback)
    } finally {
        attributes.recycle()
    }
}
