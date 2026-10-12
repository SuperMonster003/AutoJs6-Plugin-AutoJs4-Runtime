package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.autojs.plugin.common.api.IPluginInfoProvider
import org.autojs.plugin.common.api.PluginInfo

class AutoJs4PluginInfoService : Service() {
    override fun onBind(intent: Intent?): IBinder = binder

    private val binder = object : IPluginInfoProvider.Stub() {
        override fun getInfo(): PluginInfo {
            enforceCallingOrSelfPermission(
                AutoJs4RuntimeContract.PLUGIN_PERMISSION,
                "AutoJs4 Runtime metadata requires the AutoJs6 plugin permission",
            )
            return applicationContext.autoJs4PluginInfo()
        }
    }
}
