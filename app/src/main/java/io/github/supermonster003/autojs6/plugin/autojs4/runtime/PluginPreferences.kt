package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/** Plugin-owned settings; nothing here is written back to the host. */
object PluginPreferences {

    const val KEY_SHOW_CONSOLE_WINDOW = "show_console_window"

    @SuppressLint("StaticFieldLeak")
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    /** Roadmap D17: `console.show()` opens AutoJs4's floating console only when this is enabled. */
    var showConsoleWindow: Boolean
        get() = preferences.getBoolean(KEY_SHOW_CONSOLE_WINDOW, false)
        set(value) = preferences.edit().putBoolean(KEY_SHOW_CONSOLE_WINDOW, value).apply()

    private const val FILE_NAME = "autojs4_runtime"
}
