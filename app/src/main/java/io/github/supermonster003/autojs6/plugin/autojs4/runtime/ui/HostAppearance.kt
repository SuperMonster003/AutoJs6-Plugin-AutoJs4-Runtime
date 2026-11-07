package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.LocaleList
import org.autojs.plugin.common.api.AutoJs6HostSettingsContract as Contract
import java.util.Locale
import java.util.concurrent.Executors

/** Appearance of the plugin's settings UI, isolated from the embedded AutoJs4 engine. */
internal data class HostAppearance(
    val languageTag: String,
    val dark: Boolean,
    val primary: Int,
    val primaryDark: Int,
    val accent: Int,
) {
    fun wrap(context: Context): Context = context.createConfigurationContext(
        Configuration(context.resources.configuration).apply {
            val locale = Locale.forLanguageTag(languageTag)
            setLocales(LocaleList(locale))
            setLayoutDirection(locale)
            uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or
                if (dark) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
        },
    )

    companion object {
        private const val DEFAULT_COLOR = 0xffffdead.toInt()
        @Volatile var cached: HostAppearance? = null
        val worker = Executors.newSingleThreadExecutor()

        /** Call on a worker: acquiring the provider may start the host process. */
        fun read(context: Context): HostAppearance? = runCatching {
            context.contentResolver.acquireUnstableContentProviderClient(Uri.parse(Contract.CONTENT_URI))?.use {
                it.call(Contract.METHOD_GET_SETTINGS, null, null)?.let(::decode)
            }
        }.getOrNull()

        @Suppress("DEPRECATION")
        fun decode(value: Bundle): HostAppearance? = runCatching {
            require(!value.hasFileDescriptors())
            require(value.get(Contract.KEY_PROTOCOL_VERSION) == Contract.PROTOCOL_VERSION)
            require(value.get(Contract.KEY_HOST_PACKAGE_NAME) == Contract.HOST_PACKAGE_NAME)
            val tag = value.get(Contract.KEY_RESOLVED_LANGUAGE_TAG) as? String ?: return null
            require(tag.length in 2..80 && tag.matches(Regex("[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*")))
            require(Locale.Builder().setLanguageTag(tag).build().language.isNotBlank())
            HostAppearance(
                tag,
                value.get(Contract.KEY_DARK_MODE_ACTIVE) as? Boolean ?: return null,
                (value.get(Contract.KEY_THEME_COLOR_PRIMARY) as? Int ?: return null) or -0x1000000,
                (value.get(Contract.KEY_THEME_COLOR_PRIMARY_DARK) as? Int ?: return null) or -0x1000000,
                (value.get(Contract.KEY_THEME_COLOR_ACCENT) as? Int ?: return null) or -0x1000000,
            )
        }.getOrNull()

        /** The base context must still contain the system configuration, before wrap(). */
        fun resolve(systemContext: Context, host: HostAppearance? = cached): HostAppearance {
            if (host != null) return host
            val configuration = systemContext.resources.configuration
            return HostAppearance(
                configuration.locales[0].toLanguageTag(),
                configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES,
                DEFAULT_COLOR,
                DEFAULT_COLOR,
                DEFAULT_COLOR,
            )
        }
    }
}
