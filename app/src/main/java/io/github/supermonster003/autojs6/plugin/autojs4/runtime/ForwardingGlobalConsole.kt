package io.github.supermonster003.autojs6.plugin.autojs4.runtime

import com.stardust.autojs.core.console.GlobalConsole
import com.stardust.util.UiHandler

/**
 * AutoJs4's global console with host forwarding (roadmap D6). AutoJs4 routes every per-script
 * `ConsoleImpl.println` to the global console with the raw text, so this is the single place
 * where all script output passes through. The line keeps AutoJs4's own formatting locally and
 * is attributed to its execution by [ExecutionRegistry].
 *
 * zh-CN: 带宿主转发的 AutoJs4 全局控制台 (roadmap D6). AutoJs4 把每个脚本的 `ConsoleImpl.println`
 * 以原始文本转到全局控制台, 因此这里是全部脚本输出的唯一汇点; 本地保留 AutoJs4 自身的格式,
 * 归属到具体执行的工作由 [ExecutionRegistry] 完成.
 */
class ForwardingGlobalConsole(uiHandler: UiHandler) : GlobalConsole(uiHandler) {

    override fun println(level: Int, charSequence: CharSequence): String {
        val formatted = super.println(level, charSequence)
        ExecutionRegistry.forwardLog(level, charSequence.toString())
        return formatted
    }
}
