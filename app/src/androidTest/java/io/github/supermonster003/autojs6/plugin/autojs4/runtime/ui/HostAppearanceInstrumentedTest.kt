package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleCallback
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.R
import org.autojs.plugin.common.api.AutoJs6HostSettingsContract as Contract
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale
import java.util.concurrent.CopyOnWriteArrayList

@RunWith(AndroidJUnit4::class)
class HostAppearanceInstrumentedTest {
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val context = instrumentation.targetContext

    private fun snapshot() = Bundle().apply {
        putInt(Contract.KEY_PROTOCOL_VERSION, Contract.PROTOCOL_VERSION)
        putString(Contract.KEY_HOST_PACKAGE_NAME, Contract.HOST_PACKAGE_NAME)
        putString(Contract.KEY_RESOLVED_LANGUAGE_TAG, "zh-Hant-HK")
        putBoolean(Contract.KEY_DARK_MODE_ACTIVE, true)
        putInt(Contract.KEY_THEME_COLOR_PRIMARY, 0xff336699.toInt())
        putInt(Contract.KEY_THEME_COLOR_PRIMARY_DARK, 0xff224466.toInt())
        putInt(Contract.KEY_THEME_COLOR_ACCENT, 0xffcc5588.toInt())
    }

    @Test
    fun rejectsUnknownOrIncompleteHostSnapshots() {
        assertEquals("zh-Hant-HK", HostAppearance.decode(snapshot())?.languageTag)
        assertNull(HostAppearance.decode(snapshot().apply { putInt(Contract.KEY_PROTOCOL_VERSION, 99) }))
        assertNull(HostAppearance.decode(snapshot().apply { putString(Contract.KEY_HOST_PACKAGE_NAME, "other.host") }))
        for (key in listOf(Contract.KEY_RESOLVED_LANGUAGE_TAG, Contract.KEY_DARK_MODE_ACTIVE,
            Contract.KEY_THEME_COLOR_PRIMARY, Contract.KEY_THEME_COLOR_PRIMARY_DARK, Contract.KEY_THEME_COLOR_ACCENT)) {
            assertNull("Missing $key", HostAppearance.decode(snapshot().apply { remove(key) }))
            assertNull("Wrong type for $key", HostAppearance.decode(snapshot().apply {
                if (key == Contract.KEY_RESOLVED_LANGUAGE_TAG) putInt(key, 7) else putString(key, "invalid")
            }))
        }
        for (tag in listOf("", "zh_CN", "en--US", "en-a", "x-", "a".repeat(81))) {
            assertNull(tag, HostAppearance.decode(snapshot().apply { putString(Contract.KEY_RESOLVED_LANGUAGE_TAG, tag) }))
        }
    }

    @Test
    fun hostLocaleAndNightModeAreScopedToTheSettingsContext() {
        val before = Configuration(context.resources.configuration)
        val defaultLocale = Locale.getDefault()
        for (dark in listOf(false, true)) {
            val appearance = requireNotNull(HostAppearance.decode(snapshot())).copy(languageTag = "ar", dark = dark)
            val wrapped = appearance.wrap(context)
            assertEquals("ar", wrapped.resources.configuration.locales[0].language)
            assertEquals(android.view.View.LAYOUT_DIRECTION_RTL, wrapped.resources.configuration.layoutDirection)
            assertEquals(if (dark) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO,
                wrapped.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
            assertEquals(before, context.resources.configuration)
            assertEquals(defaultLocale, Locale.getDefault())
        }
        val fallback = HostAppearance.resolve(context, null)
        assertEquals(before.locales[0].toLanguageTag(), fallback.languageTag)
        assertEquals(before.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES, fallback.dark)
    }

    @Test
    fun settingsRefreshStaleAppearanceAndSurviveRecreation() {
        val host = HostAppearance.read(context)
        if (InstrumentationRegistry.getArguments().getString("requireHostAppearance") == "true") {
            assertNotNull("The installed host must grant its official settings snapshot", host)
        }
        val expected = HostAppearance.resolve(context, host)
        val before = Configuration(context.resources.configuration)
        val previous = HostAppearance.cached
        val stale = expected.copy(languageTag = "ar", dark = !expected.dark, primary = Color.MAGENTA)
        val created = CopyOnWriteArrayList<Pair<HostAppearance, Configuration>>()
        val callback = ActivityLifecycleCallback { activity, stage ->
            if (activity is SettingsActivity && stage == Stage.CREATED) {
                created.add(activity.appearance to Configuration(activity.resources.configuration))
            }
        }
        instrumentation.runOnMainSync { ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(callback) }
        try {
            HostAppearance.cached = stale
            ActivityScenario.launch<SettingsActivity>(Intent(context, SettingsActivity::class.java)).use { scenario ->
                awaitAppearance(scenario, expected)
                scenario.recreate()
                awaitAppearance(scenario, expected)
            }
            val (initialAppearance, initialConfiguration) = created.first()
            assertEquals(stale, initialAppearance)
            assertEquals("ar", initialConfiguration.locales[0].language)
            assertEquals("AppCompat must preserve the host night mode even when it differs from the system",
                if (stale.dark) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO,
                initialConfiguration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
            assertEquals("Settings must not change the engine's resources", before, context.resources.configuration)
            assertEquals("An unavailable host must clear a stale snapshot", host, HostAppearance.cached)
        } finally {
            instrumentation.runOnMainSync { ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(callback) }
            HostAppearance.cached = previous
        }
    }

    private fun awaitAppearance(scenario: ActivityScenario<SettingsActivity>, expected: HostAppearance) {
        val deadline = SystemClock.uptimeMillis() + 15000
        do {
            var matches = false
            scenario.onActivity { activity ->
                matches = activity.appearance == expected
                if (matches) {
                    assertEquals(expected.languageTag, activity.resources.configuration.locales[0].toLanguageTag())
                    assertEquals(if (expected.dark) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO,
                        activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
                    assertEquals(expected.primaryDark, activity.window.statusBarColor)
                    assertEquals(activity.getString(R.string.settings_title), activity.title.toString())
                }
            }
            if (matches) return
            SystemClock.sleep(100)
        } while (SystemClock.uptimeMillis() < deadline)
        fail("Settings did not adopt the current host/system appearance")
    }

    @Test
    fun releaseHistoryUsesItsContextLocaleIncludingHongKong() {
        val defaultLocale = Locale.getDefault()
        for ((tag, assetTag) in listOf("en" to "en", "zh-CN" to "zh-Hans", "zh-Hant-HK" to "zh-Hant-HK", "ar" to "ar")) {
            val wrapped = requireNotNull(HostAppearance.decode(snapshot())).copy(languageTag = tag).wrap(context)
            val expected = context.assets.open("doc/CHANGELOG-$assetTag.md").bufferedReader().use { it.readText() }
            assertEquals(expected, ReleaseHistoryActivity.loadChangelog(wrapped))
        }
        assertEquals(defaultLocale, Locale.getDefault())
        val previous = HostAppearance.cached
        try {
            val expected = HostAppearance.resolve(context, HostAppearance.read(context))
            HostAppearance.cached = expected
            ActivityScenario.launch<ReleaseHistoryActivity>(Intent(context, ReleaseHistoryActivity::class.java)).use { scenario ->
                scenario.onActivity { activity ->
                    assertEquals(expected, activity.appearance)
                    assertEquals(ReleaseHistoryActivity.loadChangelog(activity),
                        activity.findViewById<TextView>(R.id.release_history_text).text.toString())
                }
            }
        } finally {
            HostAppearance.cached = previous
        }
    }

    @Test
    fun systemIconsHaveContrastingBackgroundsInBothConfigurations() {
        val info = context.packageManager.getApplicationInfo(context.packageName, 0)
        assertEquals(R.mipmap.ic_icon_studio_application, info.icon)
        for ((dark, color) in listOf(false to 0xfffafafa.toInt(), true to 0xff212121.toInt())) {
            val configuration = Configuration(context.resources.configuration).apply {
                uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or
                    if (dark) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
            }
            val themed = context.createConfigurationContext(configuration)
            val icon = themed.getDrawable(info.icon)
            if (Build.VERSION.SDK_INT >= 26) {
                assertTrue("Modern systems must load an adaptive icon in both modes", icon is AdaptiveIconDrawable)
                assertEquals(color, ((icon as AdaptiveIconDrawable).background as ColorDrawable).color)
            } else {
                val bitmap = (icon as BitmapDrawable).bitmap
                assertEquals(color, bitmap.getPixel(bitmap.width / 2, bitmap.height / 50))
            }
            // Catalog and README artwork keeps its original transparent pixels.
            val artwork = (themed.getDrawable(R.mipmap.ic_plugin_center) as BitmapDrawable).bitmap
            assertEquals(0, Color.alpha(artwork.getPixel(0, 0)))
        }
        assertTrue(context.packageManager.queryIntentActivities(
            Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER).setPackage(context.packageName), 0).isEmpty())
    }
}
