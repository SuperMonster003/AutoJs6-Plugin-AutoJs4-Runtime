package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.util.Log
import com.stardust.autojs.engine.LoopBasedJavaScriptEngine

/**
 * AutoJs4's loop-based Rhino engine plus the plugin's console window policy (roadmap D17):
 * logs always reach the host, while `console.show()` opens AutoJs4's floating console in this
 * process only when the user enabled it in the plugin settings.
 *
 * zh-CN: AutoJs4 的循环 Rhino 引擎加上插件的控制台窗口策略 (roadmap D17): 日志始终回传宿主,
 * `console.show()` 只在用户于插件设置中开启后才在本进程显示 AutoJs4 的浮动控制台.
 */
class PluginJavaScriptEngine(context: android.content.Context) : LoopBasedJavaScriptEngine(context) {

    override fun init() {
        super.init()
        if (PluginPreferences.showConsoleWindow) {
            return
        }
        runCatching {
            context.evaluateString(scriptable, CONSOLE_WINDOW_POLICY, CONSOLE_WINDOW_POLICY_NAME, 1, null)
        }.onFailure { error ->
            Log.w(TAG, "Unable to apply the console window policy", error)
        }
    }

    private companion object {
        const val TAG = "PluginJavaScriptEngine"
        const val CONSOLE_WINDOW_POLICY_NAME = "autojs4-runtime-console-policy.js"
        val CONSOLE_WINDOW_POLICY = """
            (function () {
                var noop = function () {};
                if (typeof console !== 'undefined' && console !== null) {
                    console.show = noop;
                    console.hide = noop;
                }
                if (typeof openConsole !== 'undefined') {
                    openConsole = noop;
                }
            })();
        """.trimIndent()
    }
}
