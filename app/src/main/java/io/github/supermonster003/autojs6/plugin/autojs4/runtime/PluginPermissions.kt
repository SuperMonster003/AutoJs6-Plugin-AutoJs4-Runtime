package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.stardust.autojs.core.accessibility.AccessibilityServiceUsher
import com.stardust.view.accessibility.AccessibilityServiceUtils
import java.io.File

/** Permission state the AutoJs4 engine depends on; every check is read-only. */
object PluginPermissions {

    val storagePermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    fun hasStoragePermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED
    }

    fun canDrawOverlays(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
        return Settings.canDrawOverlays(context)
    }

    fun isAccessibilityServiceEnabled(context: Context): Boolean {
        return AccessibilityServiceUtils.isAccessibilityServiceEnabled(context, AccessibilityServiceUsher::class.java)
    }

    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager ?: return false
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    /** Paths inside the plugin's own storage never need the storage permission. */
    fun isAppPrivatePath(context: Context, path: String): Boolean {
        val target = runCatching { File(path).canonicalFile }.getOrElse { File(path).absoluteFile }
        val roots = buildList {
            add(context.filesDir)
            add(context.cacheDir)
            context.getExternalFilesDir(null)?.let(::add)
            context.externalCacheDir?.let(::add)
            addAll(context.getExternalFilesDirs(null).filterNotNull())
        }
        return roots.any { root ->
            val canonicalRoot = runCatching { root.canonicalFile }.getOrElse { root.absoluteFile }
            generateSequence(target) { it.parentFile }.any { it == canonicalRoot }
        }
    }

    fun isExternalStoragePath(path: String): Boolean {
        val external = runCatching { Environment.getExternalStorageDirectory().canonicalFile }
            .getOrElse { Environment.getExternalStorageDirectory().absoluteFile }
        val target = runCatching { File(path).canonicalFile }.getOrElse { File(path).absoluteFile }
        return generateSequence(target) { it.parentFile }.any { it == external }
    }

    fun openAccessibilitySettings(context: Context) {
        context.startActivity(
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
        )
    }

    fun openOverlaySettings(context: Context) {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, packageUri(context))
        } else {
            appDetailsIntent(context)
        }
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun openBatteryOptimizationSettings(context: Context) {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, packageUri(context))
        } else {
            appDetailsIntent(context)
        }
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun appDetailsIntent(context: Context): Intent {
        return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri(context))
    }

    private fun packageUri(context: Context): Uri = Uri.parse("package:${context.packageName}")
}
