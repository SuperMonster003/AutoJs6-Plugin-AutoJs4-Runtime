package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.ParcelFileDescriptor
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.autojs.plugin.common.api.PluginInfo
import org.autojs.plugin.explorer.api.IExplorerActionPlugin
import org.autojs.plugin.explorer.api.IExplorerArchiveSession

/**
 * Declares the file menu entries of [AutoJs4ExplorerActions] to the AutoJs6 file manager. The entries are host engine
 * actions: AutoJs6 runs them itself, so this service only answers the metadata and catalog calls; it opens no archive
 * and describes no file.
 *
 * zh-CN: 向 AutoJs6 文件管理器声明 [AutoJs4ExplorerActions] 的文件菜单项. 这些项是宿主引擎动作, 由 AutoJs6 自己运行, 因此本服务只回答
 * 元数据与 catalog 调用, 不打开压缩包, 也不描述文件.
 */
class AutoJs4ExplorerActionService : Service() {

    override fun onBind(intent: Intent?): IBinder = binder

    private val binder = object : IExplorerActionPlugin.Stub() {

        override fun getInfo(): PluginInfo {
            enforcePluginPermission()
            return applicationContext.autoJs4ExplorerPluginInfo()
        }

        override fun getActionCatalog(): Bundle {
            enforcePluginPermission()
            return AutoJs4ExplorerActions.catalog()
        }

        override fun openArchive(source: ParcelFileDescriptor?, request: Bundle?): IExplorerArchiveSession? {
            enforcePluginPermission()
            source?.close()
            return null
        }

        override fun openArchiveV11(source: ParcelFileDescriptor?, request: Bundle?): Bundle? {
            enforcePluginPermission()
            source?.close()
            return null
        }

        override fun getHostFileInfo(source: ParcelFileDescriptor?, request: Bundle?): Bundle? {
            enforcePluginPermission()
            source?.close()
            return null
        }
    }

    private fun enforcePluginPermission() = enforceCallingOrSelfPermission(
        AutoJs4RuntimeContract.PLUGIN_PERMISSION,
        "AutoJs4 Runtime file menu entries require the AutoJs6 plugin permission",
    )
}
