package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.R

/**
 * The release history page (AUTOJS6_PLUGIN_RELEASE_HISTORY_AGENTS.md) in Compose, in the colors of the settings page. A
 * Compose page must extend `androidx.activity.ComponentActivity`, which AutoJs4's AppCompat 1.0.2 `AppCompatActivity` is
 * not, so instead of [HostAppearanceActivity] it uses the same [HostAppearanceSession]: AutoJs6's language and night mode,
 * and its theme colors on the top bar.
 * zh-CN: Compose 版发行历史页 (规范 AUTOJS6_PLUGIN_RELEASE_HISTORY_AGENTS.md), 与设置页同色. Compose 页面必须继承
 * `androidx.activity.ComponentActivity`, AutoJs4 的 AppCompat 1.0.2 `AppCompatActivity` 不是它的子类, 所以不继承
 * [HostAppearanceActivity], 而是使用同一个 [HostAppearanceSession]: AutoJs6 的语言与夜间模式, 顶栏用其主题色.
 */
class ReleaseHistoryActivity : ComponentActivity() {

    private val session = HostAppearanceSession(this)
    internal val appearance: HostAppearance get() = session.appearance

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(session.attach(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Explicit light / dark parents, as on the settings page: AppCompat would otherwise follow the system.
        setTheme(if (appearance.dark) R.style.Theme_AutoJs4Runtime_Page_Dark else R.style.Theme_AutoJs4Runtime_Page_Light)
        super.onCreate(savedInstanceState)
        val colors = colors()
        val barStyle = if (AppearanceColors.foregroundOn(colors.surface) == AppearanceColors.WHITE) {
            SystemBarStyle.dark(Color.TRANSPARENT)
        } else {
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        }
        // Below API 26 the navigation bar icons cannot turn dark: keep a dark scrim there (settings spec section 12).
        val navigationStyle = when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.O -> SystemBarStyle.dark(NAVIGATION_SCRIM)
            colors.dark -> SystemBarStyle.dark(Color.TRANSPARENT)
            else -> SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        }
        enableEdgeToEdge(statusBarStyle = barStyle, navigationBarStyle = navigationStyle)
        val candidates = ReleaseHistoryAssetPolicy.candidatesFor(resources.configuration.locales[0])
        val entries = loadReleaseHistory(candidates) { path ->
            assets.open(path).use { stream -> stream.readBytes().toString(Charsets.UTF_8) }
        }
        setContent { ReleaseHistoryTheme(colors) { ReleaseHistoryScreen(entries = entries, onBack = ::finish) } }
    }

    /**
     * The settings page's colors: the action bar in the host's primary color, the AppCompat page colors, and the host's
     * accent made readable for the category labels.
     * zh-CN: 设置页的颜色: 操作栏用宿主主色, 页面用 AppCompat 的颜色, 类别标签用调整为可读的宿主强调色.
     */
    private fun colors(): ReleaseHistoryColors {
        val dark = appearance.dark
        val background = themeColor(android.R.attr.colorBackground, if (dark) 0xFF303030.toInt() else 0xFFFAFAFA.toInt())
        val onSurface = themeColor(android.R.attr.textColorPrimary, if (dark) AppearanceColors.WHITE else 0xDE000000.toInt())
        val onSurfaceVariant = themeColor(android.R.attr.textColorSecondary, if (dark) 0xB3FFFFFF.toInt() else 0x8A000000.toInt())
        val appBar = appearance.primary
        val accent = AppearanceColors.readable(appearance.accent, background)
        // The category labels: a pale tint of the accent, the accent darkened or lightened until it reads on it.
        val container = AppearanceColors.blend(background, accent, if (dark) 0.24f else 0.14f)
        return ReleaseHistoryColors(
            dark = dark,
            primary = AppearanceColors.readable(accent, container, 4.5),
            primaryContainer = container,
            background = background,
            surface = appBar,
            onAppBar = AppearanceColors.foregroundOn(appBar),
            onSurface = opaque(onSurface, background),
            onSurfaceVariant = opaque(onSurfaceVariant, background),
            outlineVariant = AppearanceColors.blend(background, opaque(onSurface, background), 0.12f),
        )
    }

    /** [color] (possibly translucent, like the theme text colors) laid over [background]. zh-CN: 把 (可能半透明的) [color] 叠在 [background] 上. */
    private fun opaque(color: Int, background: Int): Int =
        AppearanceColors.blend(background, color or OPAQUE, (color ushr 24) / 255f)

    override fun onResume() {
        super.onResume()
        session.onResume()
    }

    override fun onUserInteraction() {
        session.onUserInteraction()
        super.onUserInteraction()
    }

    override fun onPause() {
        session.onPause()
        super.onPause()
    }

    private companion object {
        const val OPAQUE = -0x1000000
        const val NAVIGATION_SCRIM = 0xFF1B1B1B.toInt()
    }

}
