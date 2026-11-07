package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.autojs.plugin.common.api.PluginCapabilityKeys
import org.autojs.plugin.explorer.api.ExplorerActionCapabilityKeys
import org.autojs.plugin.explorer.api.ExplorerActionCatalogKeys
import org.autojs.plugin.explorer.api.ExplorerActionHostEngineIds
import org.autojs.plugin.explorer.api.ExplorerActionPluginActions
import org.autojs.plugin.explorer.api.ExplorerActionPluginIds
import org.autojs.plugin.explorer.api.ExplorerActionValues
import org.autojs.plugin.explorer.api.IExplorerActionPlugin
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

/**
 * The Explorer Action service as AutoJs6 sees it (Explorer Action protocol v28): discoverable under the plugin
 * permission, and answering a v28 catalog with the two host engine actions under the first-level menu "AutoJs4".
 *
 * zh-CN: AutoJs6 所见的 Explorer Action 服务 (Explorer Action 协议 v28): 在插件权限下可被发现, 返回 v28 catalog, 含一级菜单 "AutoJs4"
 * 下的两个宿主引擎动作.
 */
@RunWith(AndroidJUnit4::class)
class AutoJs4ExplorerActionInstrumentedTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun theServiceIsDiscoverableUnderThePluginPermission() {
        val services = context.packageManager.queryIntentServices(
            Intent(ExplorerActionPluginActions.EXPLORER_ACTION).setPackage(context.packageName),
            PackageManager.GET_META_DATA,
        )
        assertEquals(1, services.size)
        val service = services.single().serviceInfo
        assertEquals(AutoJs4ExplorerActionService::class.java.name, service.name)
        assertTrue(service.exported)
        assertEquals(AutoJs4RuntimeContract.PLUGIN_PERMISSION, service.permission)
    }

    @Test
    fun theCatalogDeclaresTheTwoHostEngineActions() = withService { plugin ->
        val info = plugin.info
        assertEquals(ExplorerActionPluginIds.ENGINE, info.engine)
        assertEquals("autojs4-runtime", info.id)
        assertEquals(0, info.supportedAbis?.size ?: 0)
        val capabilities = requireNotNull(info.capabilities)
        assertEquals(28, capabilities.getInt(ExplorerActionCapabilityKeys.PROTOCOL_VERSION))
        assertEquals(
            AutoJs4RuntimeContract.REQUIRES_HOST_VERSION,
            capabilities.getLong(PluginCapabilityKeys.REQUIRES_HOST_VERSION),
        )

        val catalog = plugin.actionCatalog
        assertEquals(28, catalog.getInt(ExplorerActionCatalogKeys.PROTOCOL_VERSION))
        assertEquals("AutoJs4", catalog.getString(ExplorerActionCatalogKeys.MENU_GROUP_LABEL_FALLBACK))
        assertFalse(catalog.containsKey(ExplorerActionCatalogKeys.MENU_GROUP_LABEL_RESOURCE_NAME))
        @Suppress("DEPRECATION")
        val actions = catalog.getParcelableArrayList<Bundle>(ExplorerActionCatalogKeys.ACTIONS).orEmpty()
        assertEquals(
            listOf(ExplorerActionHostEngineIds.AUTOJS4_RUN, ExplorerActionHostEngineIds.AUTOJS4_COMPARE),
            actions.map { it.getString(ExplorerActionCatalogKeys.ID) },
        )
        actions.forEach { action ->
            assertEquals(ExplorerActionValues.PRESENTATION_HOST_ENGINE, action.getInt(ExplorerActionCatalogKeys.PRESENTATION))
            assertEquals(ExplorerActionValues.TARGET_MIXED, action.getInt(ExplorerActionCatalogKeys.TARGET_KIND))
            assertEquals(ExplorerActionValues.CARDINALITY_SINGLE, action.getInt(ExplorerActionCatalogKeys.CARDINALITY))
            assertEquals(ExplorerActionValues.ACCESS_READ_ONLY, action.getInt(ExplorerActionCatalogKeys.ACCESS_MODE))
            assertEquals(ExplorerActionValues.PLACEMENT_OVERFLOW, action.getInt(ExplorerActionCatalogKeys.PLACEMENT))
            assertEquals(listOf("js", "auto"), action.getStringArrayList(ExplorerActionCatalogKeys.EXTENSIONS))
            assertNull(action.getString(ExplorerActionCatalogKeys.ACTIVITY_CLASS_NAME))
            // The host resolves the label resource by name in the plugin package.
            // zh-CN: 宿主按名称在插件包中解析标签资源.
            val name = requireNotNull(action.getString(ExplorerActionCatalogKeys.LABEL_RESOURCE_NAME))
            val id = context.resources.getIdentifier(name, "string", context.packageName)
            assertTrue("missing string resource $name", id != 0)
            assertTrue(context.getString(id).contains("AutoJs4"))
        }
    }

    private fun withService(block: (IExplorerActionPlugin) -> Unit) {
        val bound = CountDownLatch(1)
        val binder = AtomicReference<IBinder?>()
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                binder.set(service)
                bound.countDown()
            }

            override fun onServiceDisconnected(name: ComponentName?) = Unit
        }
        val intent = Intent(ExplorerActionPluginActions.EXPLORER_ACTION)
            .setClassName(context.packageName, AutoJs4ExplorerActionService::class.java.name)
        assertTrue(context.bindService(intent, connection, Context.BIND_AUTO_CREATE))
        try {
            assertTrue("Explorer Action service did not bind", bound.await(30, TimeUnit.SECONDS))
            block(IExplorerActionPlugin.Stub.asInterface(requireNotNull(binder.get())))
        } finally {
            context.unbindService(connection)
        }
    }
}
