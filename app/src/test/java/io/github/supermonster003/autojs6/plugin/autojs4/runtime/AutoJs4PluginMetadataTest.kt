package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui.ReleaseHistoryActivity
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Locale

class AutoJs4PluginMetadataTest {
    @Test
    fun identityMatchesRepositoryContract() {
        val identity = AutoJs4PluginMetadata.identity()

        assertEquals("autojs4-runtime", identity.id)
        assertEquals("autojs4", identity.engine)
        assertEquals("default", identity.variant)
        assertEquals(AutoJs4RuntimeContract.REQUIRES_HOST_VERSION, identity.requiredHostVersion)
        assertEquals(1, identity.protocolVersion)
        assertEquals("1.7.7.2", identity.rhinoVersion)
        assertTrue(identity.autoJs4VersionName.matches(Regex("\\d+\\.\\d+\\.\\d+")))
        assertTrue(identity.autoJs4VersionCode > 0)
        assertTrue(identity.autoJs4Commit == "unknown" || identity.autoJs4Commit.matches(Regex("[0-9a-f]{40}")))
    }

    @Test
    fun distributedAbiInventoryMirrorsAutoJs4() {
        assertEquals(listOf("armeabi-v7a", "x86"), DISTRIBUTED_ABIS.toList())
        assertTrue(INSTRUCTION_REFERENCE.startsWith("@raw/"))
    }

    @Test
    fun boundedTextRespectsUtf8Limits() {
        assertEquals("abc", BoundedText.truncate("abc", 3))
        assertEquals("ab", BoundedText.truncate("abc", 2))
        assertEquals("", BoundedText.truncate("中", 2))
        assertEquals("中", BoundedText.truncate("中文", 4))
        assertEquals(listOf("中", "文"), BoundedText.chunk("中文", 4))
        assertEquals("🧪", BoundedText.truncate("🧪x", 4))
        assertEquals(listOf("🧪", "x"), BoundedText.chunk("🧪x", 4))
    }

    @Test
    fun changelogCandidatesFollowTheReadmeLanguages() {
        assertEquals(
            listOf("doc/CHANGELOG-zh-Hans.md", "doc/CHANGELOG-en.md", "doc/CHANGELOG.md"),
            ReleaseHistoryActivity.changelogCandidates(Locale.SIMPLIFIED_CHINESE),
        )
        assertEquals(
            listOf("doc/CHANGELOG-zh-Hant-TW.md", "doc/CHANGELOG-en.md", "doc/CHANGELOG.md"),
            ReleaseHistoryActivity.changelogCandidates(Locale.TRADITIONAL_CHINESE),
        )
        assertEquals(
            listOf("doc/CHANGELOG-zh-Hant-HK.md", "doc/CHANGELOG-en.md", "doc/CHANGELOG.md"),
            ReleaseHistoryActivity.changelogCandidates(Locale("zh", "HK")),
        )
        assertEquals(
            listOf("doc/CHANGELOG-en.md", "doc/CHANGELOG.md"),
            ReleaseHistoryActivity.changelogCandidates(Locale.GERMANY),
        )
        assertEquals(
            listOf("doc/CHANGELOG-ru.md", "doc/CHANGELOG-en.md", "doc/CHANGELOG.md"),
            ReleaseHistoryActivity.changelogCandidates(Locale("ru", "RU")),
        )
    }
}
