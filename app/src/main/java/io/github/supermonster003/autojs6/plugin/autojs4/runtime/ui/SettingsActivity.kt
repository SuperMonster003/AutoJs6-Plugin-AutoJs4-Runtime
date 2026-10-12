package io.github.supermonster003.autojs6.plugin.autojs4.runtime.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.stardust.autojs.core.util.ProcessShell
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.AutoJs4PluginMetadata
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.PluginPermissions
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.PluginPreferences
import io.github.supermonster003.autojs6.plugin.autojs4.runtime.R
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import java.util.concurrent.Executors

/**
 * Plugin settings (roadmap D14 / D17): permission and service switches with live status, the
 * console window policy and release information. Opened by the host through the package launch
 * intent (MAIN + INFO, no launcher icon) and by the runtime service when a permission is missing.
 *
 * zh-CN: 插件设置页 (roadmap D14 / D17): 带实时状态的权限与服务开关, 控制台窗口策略与版本信息.
 * 由宿主通过包启动意图 (MAIN + INFO, 无启动器图标) 打开, 或在缺少权限时由运行时服务打开.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var rows: SettingsRows
    private lateinit var accessibilityRow: SettingsRows.Row
    private lateinit var overlayRow: SettingsRows.Row
    private lateinit var storageRow: SettingsRows.Row
    private lateinit var batteryRow: SettingsRows.Row
    private lateinit var rootRow: SettingsRows.Row
    private val rootCheckExecutor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setTitle(R.string.settings_title)
        rows = SettingsRows(this, findViewById(R.id.settings_container))
        buildPermissionGroup()
        buildRunningGroup()
        buildAboutGroup()
        intent.getStringExtra(EXTRA_REQUESTED_PERMISSION)?.let(::announceRequestedPermission)
    }

    override fun onResume() {
        super.onResume()
        refreshStatuses()
    }

    override fun onDestroy() {
        rootCheckExecutor.shutdownNow()
        super.onDestroy()
    }

    private fun buildPermissionGroup() {
        rows.addGroupTitle(R.string.settings_group_permissions)
        accessibilityRow = rows.addNavigationRow(R.string.settings_accessibility_title) {
            PluginPermissions.openAccessibilitySettings(this)
        }
        overlayRow = rows.addNavigationRow(R.string.settings_overlay_title) {
            PluginPermissions.openOverlaySettings(this)
        }
        storageRow = rows.addNavigationRow(R.string.settings_storage_title) {
            requestStoragePermission()
        }
        batteryRow = rows.addNavigationRow(R.string.settings_battery_title) {
            PluginPermissions.openBatteryOptimizationSettings(this)
        }
        rootRow = rows.addNavigationRow(R.string.settings_root_title) {
            checkRoot()
        }
        rootRow.setSummary(getString(R.string.settings_root_summary_unknown))
    }

    private fun buildRunningGroup() {
        rows.addGroupTitle(R.string.settings_group_running)
        rows.addSwitchRow(
            titleRes = R.string.settings_console_window_title,
            summary = getString(R.string.settings_console_window_summary),
            checked = PluginPreferences.showConsoleWindow,
        ) { checked -> PluginPreferences.showConsoleWindow = checked }
    }

    private fun buildAboutGroup() {
        rows.addGroupTitle(R.string.settings_group_about)
        val identity = AutoJs4PluginMetadata.identity()
        val versionName = runCatching {
            packageManager.getPackageInfo(packageName, 0).versionName.orEmpty()
        }.getOrDefault("")
        rows.addNavigationRow(R.string.settings_version_title, showChevron = false) { }
            .setSummary(
                getString(
                    R.string.settings_version_summary,
                    versionName,
                    identity.autoJs4VersionName,
                    identity.autoJs4Commit.take(10),
                ),
            )
        rows.addNavigationRow(R.string.settings_release_history_title) {
            startActivity(Intent(this, ReleaseHistoryActivity::class.java))
        }
        rows.addNavigationRow(R.string.settings_project_page_title) {
            runCatching {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PROJECT_URL)))
            }.onFailure {
                Toast.makeText(this, PROJECT_URL, Toast.LENGTH_LONG).show()
            }
        }.setSummary(PROJECT_URL)
    }

    private fun refreshStatuses() {
        accessibilityRow.setSummary(
            getString(
                if (PluginPermissions.isAccessibilityServiceEnabled(this)) {
                    R.string.settings_accessibility_summary_enabled
                } else {
                    R.string.settings_accessibility_summary_disabled
                },
            ),
        )
        overlayRow.setSummary(statusSummary(PluginPermissions.canDrawOverlays(this)))
        storageRow.setSummary(statusSummary(PluginPermissions.hasStoragePermission(this)))
        batteryRow.setSummary(statusSummary(PluginPermissions.isIgnoringBatteryOptimizations(this)))
    }

    private fun statusSummary(granted: Boolean): String = getString(
        if (granted) R.string.text_status_granted else R.string.text_status_not_granted,
    )

    private fun requestStoragePermission() {
        if (PluginPermissions.hasStoragePermission(this)) {
            startActivity(PluginPermissions.appDetailsIntent(this))
            return
        }
        ActivityCompat.requestPermissions(this, PluginPermissions.storagePermissions, REQUEST_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_STORAGE) return
        val granted = grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        if (!granted && permissions.any { !ActivityCompat.shouldShowRequestPermissionRationale(this, it) }) {
            // Permanently denied: only the system app details page can change it now.
            startActivity(PluginPermissions.appDetailsIntent(this))
        }
        refreshStatuses()
    }

    private fun checkRoot() {
        rootRow.setSummary(getString(R.string.settings_root_summary_checking))
        rootCheckExecutor.execute {
            val available = runCatching {
                val result = ProcessShell.execCommand("id", true)
                result.code == 0 && result.result.contains("uid=0")
            }.getOrDefault(false)
            runOnUiThread {
                if (isFinishing || isDestroyed) return@runOnUiThread
                rootRow.setSummary(
                    getString(
                        if (available) R.string.settings_root_summary_available else R.string.settings_root_summary_unavailable,
                    ),
                )
            }
        }
    }

    private fun announceRequestedPermission(permission: String) {
        val titleRes = when (permission) {
            AutoJs4RuntimeContract.PERMISSION_STORAGE -> R.string.settings_storage_title
            AutoJs4RuntimeContract.PERMISSION_OVERLAY -> R.string.settings_overlay_title
            AutoJs4RuntimeContract.PERMISSION_ACCESSIBILITY -> R.string.settings_accessibility_title
            else -> return
        }
        Toast.makeText(
            this,
            getString(R.string.text_permission_required_for_script, getString(titleRes)),
            Toast.LENGTH_LONG,
        ).show()
    }

    companion object {
        const val EXTRA_REQUESTED_PERMISSION = "io.github.supermonster003.autojs6.plugin.autojs4.runtime.extra.REQUESTED_PERMISSION"
        const val PROJECT_URL = "https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime"
        private const val REQUEST_STORAGE = 1001
    }
}
