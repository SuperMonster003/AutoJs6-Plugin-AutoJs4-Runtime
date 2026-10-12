package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.stardust.autojs.AutoJs
import com.stardust.autojs.core.console.GlobalConsole
import com.stardust.autojs.runtime.ScriptRuntime
import com.stardust.autojs.runtime.api.AppUtils
import com.stardust.autojs.runtime.exception.ScriptException
import com.stardust.autojs.runtime.exception.ScriptInterruptedException
import com.stardust.autojs.script.JavaScriptSource
import com.stardust.view.accessibility.AccessibilityService
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui.LogActivity
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui.SettingsActivity

/**
 * The AutoJs4 engine shell. It mirrors `inrt/.../autojs/AutoJs.kt` of the AutoJs4 repository:
 * the engine, runtime and accessibility bridge are AutoJs4's own classes, this subclass only
 * supplies the file provider authority, the host log forwarding console, the console window
 * policy and the plugin's own permission guidance.
 *
 * zh-CN: AutoJs4 引擎外壳, 对应 AutoJs4 仓库的 `inrt/.../autojs/AutoJs.kt`: 引擎, 运行时与无障碍桥接都是
 * AutoJs4 自己的类, 这个子类只提供 FileProvider authority, 转发宿主日志的控制台, 控制台窗口策略与插件
 * 自身的权限引导.
 */
class AutoJs4Runtime private constructor(application: Application) : AutoJs(application) {

    override fun createAppUtils(context: Context): AppUtils {
        return AppUtils(context, context.packageName + FILE_PROVIDER_SUFFIX)
    }

    override fun createGlobalConsole(): GlobalConsole {
        return ForwardingGlobalConsole(uiHandler)
    }

    override fun initScriptEngineManager() {
        super.initScriptEngineManager()
        scriptEngineManager.registerEngine(JavaScriptSource.ENGINE) {
            PluginJavaScriptEngine(application).also { engine ->
                engine.runtime = createRuntime()
            }
        }
    }

    override fun createRuntime(): ScriptRuntime {
        return super.createRuntime().apply {
            putProperty("class.settings", SettingsActivity::class.java)
            putProperty("class.console", LogActivity::class.java)
        }
    }

    override fun ensureAccessibilityServiceEnabled() {
        if (AccessibilityService.instance != null) {
            return
        }
        val message = accessibilityErrorMessage()
        PluginPermissions.openAccessibilitySettings(application)
        throw ScriptException(message)
    }

    override fun waitForAccessibilityServiceEnabled() {
        if (AccessibilityService.instance != null) {
            return
        }
        accessibilityErrorMessage()
        PluginPermissions.openAccessibilitySettings(application)
        if (!AccessibilityService.waitForEnabled(-1)) {
            throw ScriptInterruptedException()
        }
    }

    private fun accessibilityErrorMessage(): String {
        return if (PluginPermissions.isAccessibilityServiceEnabled(application)) {
            application.getString(R.string.text_auto_operate_service_enabled_but_not_running)
        } else {
            application.getString(R.string.text_no_accessibility_permission)
        }
    }

    companion object {
        const val FILE_PROVIDER_SUFFIX = ".fileprovider"

        @SuppressLint("StaticFieldLeak")
        lateinit var instance: AutoJs4Runtime
            private set

        val isInitialized: Boolean
            get() = ::instance.isInitialized

        fun initInstance(application: Application) {
            instance = AutoJs4Runtime(application)
        }
    }
}
