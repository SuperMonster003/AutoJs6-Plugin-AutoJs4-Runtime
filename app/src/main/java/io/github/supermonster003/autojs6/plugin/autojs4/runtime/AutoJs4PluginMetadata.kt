package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.content.Context
import android.os.Build
import android.os.Bundle
import org.autojs.plugin.autojs4.runtime.api.AutoJs4PluginCapabilityKeys
import org.autojs.plugin.autojs4.runtime.api.AutoJs4PluginIds
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.autojs.plugin.common.api.PluginCapabilityKeys
import org.autojs.plugin.common.api.PluginInfo
import java.util.zip.ZipFile

/** ABIs the release pipeline builds; must match `supportedAbis` in app/build.gradle.kts. */
internal val DISTRIBUTED_ABIS = arrayOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86")
internal const val INSTRUCTION_REFERENCE = "@raw/plugin_instruction"

internal data class AutoJs4Identity(
    val id: String,
    val engine: String,
    val variant: String,
    val requiredHostVersion: Long,
    val protocolVersion: Int,
    val autoJs4VersionName: String,
    val autoJs4VersionCode: Int,
    val autoJs4Commit: String,
    val rhinoVersion: String,
)

internal object AutoJs4PluginMetadata {
    fun identity() = AutoJs4Identity(
        id = AutoJs4PluginIds.ID,
        engine = AutoJs4PluginIds.ENGINE,
        variant = AutoJs4PluginIds.VARIANT_DEFAULT,
        requiredHostVersion = AutoJs4RuntimeContract.REQUIRES_HOST_VERSION,
        protocolVersion = AutoJs4RuntimeContract.PROTOCOL_VERSION,
        autoJs4VersionName = BuildConfig.AUTOJS4_VERSION_NAME,
        autoJs4VersionCode = BuildConfig.AUTOJS4_VERSION_CODE,
        autoJs4Commit = BuildConfig.AUTOJS4_COMMIT,
        rhinoVersion = AutoJs4RuntimeContract.RHINO_VERSION,
    )

    /** Pure capability assembly so JVM tests can cover the Bundle contents. */
    fun capabilities(identity: AutoJs4Identity = identity()): Bundle = Bundle().apply {
        putLong(PluginCapabilityKeys.REQUIRES_HOST_VERSION, identity.requiredHostVersion)
        putInt(AutoJs4PluginCapabilityKeys.CONTRACT_VERSION, identity.protocolVersion)
        putString(AutoJs4PluginCapabilityKeys.AUTOJS4_VERSION_NAME, identity.autoJs4VersionName)
        putInt(AutoJs4PluginCapabilityKeys.AUTOJS4_VERSION_CODE, identity.autoJs4VersionCode)
        putString(AutoJs4PluginCapabilityKeys.AUTOJS4_COMMIT, identity.autoJs4Commit)
        putString(AutoJs4PluginCapabilityKeys.RHINO_VERSION, identity.rhinoVersion)
        putString(AutoJs4PluginCapabilityKeys.SOURCE_TRANSPORT, AutoJs4RuntimeContract.SOURCE_TRANSPORT_FILE_PATH)
        putString(
            AutoJs4PluginCapabilityKeys.EXECUTION_MODEL,
            AutoJs4RuntimeContract.EXECUTION_MODEL_ISOLATED_PROCESS_CONCURRENT,
        )
        putBoolean(AutoJs4PluginCapabilityKeys.SUPPORTS_UI_MODE, true)
        putBoolean(AutoJs4PluginCapabilityKeys.SUPPORTS_AUTO_FILES, true)
        putBoolean(AutoJs4PluginCapabilityKeys.SUPPORTS_PROJECTS, true)
        putBoolean(AutoJs4PluginCapabilityKeys.SUPPORTS_CANCELLATION, true)
        putBoolean(AutoJs4PluginCapabilityKeys.SUPPORTS_STREAMING_LOG, true)
    }
}

/** ABIs for which the installed package carries at least one native library. */
internal fun Context.packagedRuntimeAbis(): Array<String> {
    val archivePaths = buildList {
        add(applicationInfo.sourceDir)
        addAll(applicationInfo.splitSourceDirs.orEmpty())
    }
    val packaged = archivePaths.flatMapTo(linkedSetOf()) { archivePath ->
        runCatching {
            ZipFile(archivePath).use { archive ->
                archive.entries().asSequence()
                    .map { it.name }
                    .mapNotNull { name ->
                        DISTRIBUTED_ABIS.firstOrNull { abi ->
                            name.startsWith("lib/$abi/") && name.endsWith(".so")
                        }
                    }
                    .toList()
            }
        }.getOrDefault(emptyList())
    }
    return DISTRIBUTED_ABIS.filter(packaged::contains).toTypedArray()
}

internal fun Context.autoJs4PluginInfo(): PluginInfo {
    val appContext = applicationContext
    val packageInfo = appContext.packageManager.getPackageInfo(appContext.packageName, 0)
    val installedVersionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        @Suppress("DEPRECATION")
        packageInfo.versionCode.toLong()
    }
    val identity = AutoJs4PluginMetadata.identity()
    return PluginInfo().apply {
        name = getString(R.string.app_name)
        description = getString(R.string.plugin_description)
        instruction = INSTRUCTION_REFERENCE
        author = getString(R.string.plugin_author)
        collaborators = arrayOf("hyb1996")
        versionName = packageInfo.versionName.orEmpty()
        versionCode = installedVersionCode
        versionDate = getString(R.string.plugin_version_date)
        id = identity.id
        engine = identity.engine
        variant = identity.variant
        supportedAbis = appContext.packagedRuntimeAbis()
        capabilities = AutoJs4PluginMetadata.capabilities(identity)
    }
}
