package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import android.content.Context
import android.os.Bundle
import org.autojs.plugin.autojs4.runtime.api.AutoJs4RuntimeContract
import org.autojs.plugin.common.api.PluginCapabilityKeys
import org.autojs.plugin.common.api.PluginInfo
import org.autojs.plugin.explorer.api.ExplorerActionCapabilityKeys
import org.autojs.plugin.explorer.api.ExplorerActionCatalogKeys
import org.autojs.plugin.explorer.api.ExplorerActionHostEngineIds
import org.autojs.plugin.explorer.api.ExplorerActionPluginIds
import org.autojs.plugin.explorer.api.ExplorerActionValues

/** One file menu entry: the host command it names, its label resource and the text used when the host cannot read it. */
internal data class AutoJs4ExplorerAction(val id: String, val labelResourceName: String, val labelFallback: String)

/**
 * The file menu entries of AutoJs4 Runtime in the AutoJs6 file manager (AutoJs6 docs/dev/explorer-action-protocol-v28.md,
 * explorer roadmap Q52 (b)): "Run with AutoJs4" and "Run with AutoJs4 and compare" under the first-level menu
 * "AutoJs4", declared as host engine actions. AutoJs6 runs both itself (through the AutoJs4 Runtime service of this
 * package), so the catalog names no Activity. One `TARGET_MIXED` action per command covers `.js` / `.auto` files (the
 * extensions below) and project folders (AutoJs6 decides on directories itself).
 *
 * AutoJs6 6.8.1 reads the v28 catalog and stops showing its own copies of the two entries; AutoJs6 6.8.0 (Explorer
 * Action protocol up to v26) skips the catalog and keeps its own entries, so the minimum host version stays unchanged.
 *
 * zh-CN: AutoJs4 Runtime 在 AutoJs6 文件管理器中的文件菜单项 (AutoJs6 docs/dev/explorer-action-protocol-v28.md, explorer 路线图
 * Q52 (b)): 一级菜单 "AutoJs4" 下的 "使用 AutoJs4 运行" 与 "使用 AutoJs4 运行并对照", 以宿主引擎动作声明. 两项都由 AutoJs6 自己运行
 * (经本包的 AutoJs4 Runtime 服务), 因此 catalog 不指定 Activity. 每个命令一个 `TARGET_MIXED` 动作, 覆盖 `.js` / `.auto` 文件
 * (下列扩展名) 与项目文件夹 (目录由 AutoJs6 自行判断).
 *
 * AutoJs6 6.8.1 读取 v28 catalog, 不再显示自己的两项副本; AutoJs6 6.8.0 (Explorer Action 协议最高 v26) 跳过该 catalog 并保留自己的两项,
 * 因此最低宿主版本不变.
 */
internal object AutoJs4ExplorerActions {

    /** Explorer Action protocol v28 defines the host engine presentation. zh-CN: Explorer Action 协议 v28 定义宿主引擎呈现方式. */
    const val PROTOCOL_VERSION = 28

    /** The first-level menu, not translated (like the menu name the host used before). zh-CN: 一级菜单名, 不翻译 (与宿主此前使用的菜单名相同). */
    const val MENU_GROUP_LABEL = "AutoJs4"

    @JvmField
    val EXTENSIONS: List<String> = listOf("js", "auto")

    @JvmField
    val ACTIONS: List<AutoJs4ExplorerAction> = listOf(
        AutoJs4ExplorerAction(ExplorerActionHostEngineIds.AUTOJS4_RUN, "explorer_action_run_with_autojs4", "Run with AutoJs4"),
        AutoJs4ExplorerAction(
            ExplorerActionHostEngineIds.AUTOJS4_COMPARE,
            "explorer_action_compare_with_autojs4",
            "Run with AutoJs4 and compare",
        ),
    )

    fun catalog(): Bundle = Bundle().apply {
        putInt(ExplorerActionCatalogKeys.PROTOCOL_VERSION, PROTOCOL_VERSION)
        putString(ExplorerActionCatalogKeys.MENU_GROUP_LABEL_FALLBACK, MENU_GROUP_LABEL)
        putParcelableArrayList(ExplorerActionCatalogKeys.ACTIONS, ACTIONS.mapTo(ArrayList(), ::action))
    }

    /** Capabilities of the Explorer Action service. zh-CN: Explorer Action 服务的能力. */
    fun capabilities(): Bundle = Bundle().apply {
        putLong(PluginCapabilityKeys.REQUIRES_HOST_VERSION, AutoJs4RuntimeContract.REQUIRES_HOST_VERSION)
        putInt(ExplorerActionCapabilityKeys.PROTOCOL_VERSION, PROTOCOL_VERSION)
    }

    private fun action(action: AutoJs4ExplorerAction) = Bundle().apply {
        putString(ExplorerActionCatalogKeys.ID, action.id)
        putString(ExplorerActionCatalogKeys.LABEL_RESOURCE_NAME, action.labelResourceName)
        putString(ExplorerActionCatalogKeys.LABEL_FALLBACK, action.labelFallback)
        putInt(ExplorerActionCatalogKeys.PRESENTATION, ExplorerActionValues.PRESENTATION_HOST_ENGINE)
        putInt(ExplorerActionCatalogKeys.TARGET_KIND, ExplorerActionValues.TARGET_MIXED)
        putInt(ExplorerActionCatalogKeys.CARDINALITY, ExplorerActionValues.CARDINALITY_SINGLE)
        putInt(ExplorerActionCatalogKeys.ACCESS_MODE, ExplorerActionValues.ACCESS_READ_ONLY)
        putInt(ExplorerActionCatalogKeys.PLACEMENT, ExplorerActionValues.PLACEMENT_OVERFLOW)
        putStringArrayList(ExplorerActionCatalogKeys.EXTENSIONS, ArrayList(EXTENSIONS))
    }
}

/**
 * The [PluginInfo] of the Explorer Action service: the plugin's own metadata with the Explorer Action engine and
 * capabilities; no native library is involved, so no ABI is listed.
 * zh-CN: Explorer Action 服务的 [PluginInfo]: 插件自身的元数据, 换为 Explorer Action 引擎与能力; 不涉及原生库, 因此不列出 ABI.
 */
internal fun Context.autoJs4ExplorerPluginInfo(): PluginInfo = autoJs4PluginInfo().apply {
    engine = ExplorerActionPluginIds.ENGINE
    supportedAbis = emptyArray()
    capabilities = AutoJs4ExplorerActions.capabilities()
}
