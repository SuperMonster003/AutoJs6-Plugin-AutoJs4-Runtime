package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import org.autojs.plugin.explorer.api.ExplorerActionHostEngineIds
import org.autojs.plugin.explorer.api.ExplorerActionProtocol
import org.autojs.plugin.explorer.api.ExplorerActionValues
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * The file menu entries declared to AutoJs6 (Explorer Action protocol v28, host engine actions): the commands, the
 * first-level menu and the label resources in every language of the plugin.
 *
 * zh-CN: 向 AutoJs6 声明的文件菜单项 (Explorer Action 协议 v28, 宿主引擎动作): 命令, 一级菜单与插件全部语言中的标签资源.
 */
class AutoJs4ExplorerActionsTest {

    private val languages = listOf("", "-en", "-zh", "-zh-rHK", "-zh-rTW", "-ja", "-ko", "-fr", "-es", "-ru", "-ar")

    private fun strings(qualifier: String): Map<String, String> {
        val file = File("src/main/res/values$qualifier/strings.xml")
        assertTrue("missing $file", file.isFile)
        return Regex("""<string name="([a-z0-9_]+)"[^>]*>([^<]*)</string>""").findAll(file.readText())
            .associate { it.groupValues[1] to it.groupValues[2] }
    }

    @Test
    fun theCatalogDeclaresTheTwoHostCommandsOfProtocolV28() {
        assertEquals(28, AutoJs4ExplorerActions.PROTOCOL_VERSION)
        assertEquals(ExplorerActionProtocol.VERSION, AutoJs4ExplorerActions.PROTOCOL_VERSION)
        assertEquals(3, ExplorerActionValues.PRESENTATION_HOST_ENGINE)
        assertEquals(
            listOf(ExplorerActionHostEngineIds.AUTOJS4_RUN, ExplorerActionHostEngineIds.AUTOJS4_COMPARE),
            AutoJs4ExplorerActions.ACTIONS.map { it.id },
        )
        assertEquals(listOf("autojs4-run", "autojs4-compare"), AutoJs4ExplorerActions.ACTIONS.map { it.id })
        assertEquals("AutoJs4", AutoJs4ExplorerActions.MENU_GROUP_LABEL)
        assertEquals(listOf("js", "auto"), AutoJs4ExplorerActions.EXTENSIONS)
    }

    @Test
    fun everyLanguageNamesBothEntries() {
        val defaults = strings("")
        AutoJs4ExplorerActions.ACTIONS.forEach { action ->
            // The fallback is the default text, which equals the English one.
            // zh-CN: 备用文本即默认文本, 与英文相同.
            assertEquals(action.labelFallback, defaults[action.labelResourceName])
            assertEquals(action.labelFallback, strings("-en")[action.labelResourceName])
            languages.forEach { qualifier ->
                val label = strings(qualifier)[action.labelResourceName]
                assertTrue("values$qualifier lacks ${action.labelResourceName}", !label.isNullOrBlank())
                assertTrue("values$qualifier: $label", label!!.contains("AutoJs4") && label.none { it.code in 0xFF01..0xFF5E })
            }
        }
    }

    @Test
    fun theServiceIsDeclaredForTheHost() {
        val manifest = File("src/main/AndroidManifest.xml").readText()
        val service = Regex("""<service\s+android:name="\.AutoJs4ExplorerActionService"[\s\S]*?</service>""").find(manifest)?.value
        assertTrue("AutoJs4ExplorerActionService is not declared", service != null)
        assertTrue(service!!.contains("""android:permission="org.autojs.permission.PLUGIN""""))
        assertTrue(service.contains("""android:exported="true""""))
        assertTrue(service.contains("""<action android:name="org.autojs.plugin.EXPLORER_ACTION" />"""))
    }
}
