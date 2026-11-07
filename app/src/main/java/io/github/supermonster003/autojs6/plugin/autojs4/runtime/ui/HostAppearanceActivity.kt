package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.ColorUtils
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.R

/** Refreshes the official host snapshot on resume without changing process-wide engine resources. */
abstract class HostAppearanceActivity : AppCompatActivity() {
    private lateinit var systemContext: Context
    internal lateinit var appearance: HostAppearance
        private set
    private var appearanceGeneration = 0
    private var interacted = false

    override fun attachBaseContext(newBase: Context) {
        systemContext = newBase
        appearance = HostAppearance.resolve(newBase)
        super.attachBaseContext(appearance.wrap(newBase))
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(if (appearance.dark) R.style.Theme_AutoJs4Runtime_Dark else R.style.Theme_AutoJs4Runtime_Light)
        // AutoJs4's AppCompat 1.0.2 needs the Activity window, which attachBaseContext does not have yet.
        delegate.setLocalNightMode(if (appearance.dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(appearance.primary))
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = appearance.primaryDark
        val navigationColor = if (appearance.dark) 0xff121212.toInt() else 0xfffafafa.toInt()
        window.navigationBarColor = if (Build.VERSION.SDK_INT >= 26) navigationColor else Color.BLACK
        val mask = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
            if (Build.VERSION.SDK_INT >= 26) View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR else 0
        var flags = window.decorView.systemUiVisibility and mask.inv()
        if (foregroundOn(appearance.primaryDark) == Color.BLACK) flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= 26 && !appearance.dark) flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.decorView.systemUiVisibility = flags
    }

    override fun onTitleChanged(title: CharSequence?, color: Int) {
        super.onTitleChanged(title, color)
        if (::appearance.isInitialized) {
            supportActionBar?.title = SpannableString(title ?: "").apply {
                setSpan(ForegroundColorSpan(foregroundOn(appearance.primary)), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        interacted = false
        val generation = ++appearanceGeneration
        val app = applicationContext
        HostAppearance.worker.execute {
            val host = HostAppearance.read(app)
            runOnUiThread {
                if (generation != appearanceGeneration || isFinishing || isDestroyed) return@runOnUiThread
                // A missing/disabled host clears the previous snapshot instead of retaining it forever.
                HostAppearance.cached = host
                if (!interacted && appearance != HostAppearance.resolve(systemContext, host)) recreate()
            }
        }
    }

    override fun onUserInteraction() {
        interacted = true
        super.onUserInteraction()
    }

    override fun onPause() {
        appearanceGeneration++
        super.onPause()
    }

    private fun foregroundOn(background: Int): Int =
        if (ColorUtils.calculateContrast(Color.BLACK, background) >= ColorUtils.calculateContrast(Color.WHITE, background)) {
            Color.BLACK
        } else {
            Color.WHITE
        }
}
