package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.stardust.autojs.core.console.ConsoleView
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.AutoJs4Runtime
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.R

/** AutoJs4's global console rendered with AutoJs4's own `ConsoleView`; the `class.console` target. */
class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        setTitle(R.string.log_title)
        val consoleView = findViewById<ConsoleView>(R.id.console)
        consoleView.setConsole(AutoJs4Runtime.instance.globalConsole)
        consoleView.findViewById<View>(R.id.input_container).visibility = View.GONE
    }
}
