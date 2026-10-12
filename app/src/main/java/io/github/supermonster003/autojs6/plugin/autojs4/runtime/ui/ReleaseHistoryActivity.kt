package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.R
import java.util.Locale

/** Shows the bundled localized changelog (`assets/doc/CHANGELOG-<tag>.md`), falling back to English. */
class ReleaseHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_history)
        setTitle(R.string.release_history_title)
        findViewById<TextView>(R.id.release_history_text).text =
            loadChangelog(this) ?: getString(R.string.release_history_load_failed)
    }

    companion object {
        fun changelogCandidates(locale: Locale): List<String> {
            val language = locale.language.lowercase(Locale.ROOT)
            val region = locale.country.uppercase(Locale.ROOT)
            val script = locale.script
            val primary = when (language) {
                "zh" -> when {
                    script == "Hant" || region == "TW" -> "zh-Hant-TW"
                    region == "HK" || region == "MO" -> "zh-Hant-HK"
                    else -> "zh-Hans"
                }
                "ar", "es", "fr", "ja", "ko", "ru", "en" -> language
                else -> null
            }
            return listOfNotNull(primary, "en").distinct().map { "doc/CHANGELOG-$it.md" } + "doc/CHANGELOG.md"
        }

        fun loadChangelog(context: Context, locale: Locale = Locale.getDefault()): String? {
            for (candidate in changelogCandidates(locale)) {
                val text = runCatching {
                    context.assets.open(candidate).bufferedReader(Charsets.UTF_8).use { it.readText() }
                }.getOrNull()
                if (!text.isNullOrBlank()) return text
            }
            return null
        }
    }
}
