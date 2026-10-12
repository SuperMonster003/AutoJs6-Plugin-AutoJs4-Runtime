<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>使用内嵌的 Auto.js 4.1.1 Alpha2 引擎运行脚本, 用于对照 AutoJs6 的运行结果</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### 语言

******

当前 README.md 支持以下语言:

- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-en.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ar.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-es.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-fr.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ru.md)
- 简体中文 [zh-Hans] # 当前
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### 简介

******

AutoJs4 Runtime 内嵌了 [AutoJs4](https://github.com/SuperMonster003/AutoJs4) 的引擎, 即作为 AutoJs6 行为基准的 Auto.js 4.1.1 Alpha2 克隆项目. 安装后, AutoJs6 文件管理器中的 "使用 AutoJs4 运行" 会在本插件自己的进程里用原版 Rhino 1.7.7.2 引擎运行 `.js` 文件, `.auto` 录制文件与项目文件夹, 控制台输出回传到 AutoJs6. 这样无需另外安装 AutoJs4, 就能在同一台设备上检查脚本在 AutoJs4 与 AutoJs6 上的表现是否不同.

******

### 安装和使用

******

1. 在 Android 7.0 (API 24) 或更高版本上使用 AutoJs6 build 5334 (6.8.0) 或更高版本. 设备需要能运行 32 位应用, 与 AutoJs4 的要求相同.
2. 安装与设备 ABI 对应的发布 APK (ARM 设备为 `armeabi-v7a`, 模拟器为 `x86`), 或安装 `universal` APK.
3. 打开 AutoJs6 插件中心并启用 AutoJs4 Runtime. 如果 Android 仍将新安装的包保持在停止状态, 请使用宿主显示的 `激活` 操作.
4. 从插件中心打开插件设置, 按脚本需要授予存储权限, 悬浮窗与无障碍服务; 这些权限属于插件自身, 与 AutoJs6 的权限相互独立.
5. 在 AutoJs6 文件管理器中打开 `.js` 文件, `.auto` 文件或项目文件夹的菜单, 选择 `使用 AutoJs4 运行`. 任务会出现在 AutoJs6 的任务列表中并可在那里停止.

> 插件不修改 AutoJs4: 相同的 Rhino 1.7.7.2, 相同的 OpenCV 3.4.3 原生库, `targetSdk 28` 与相同的 32 位 ABI 覆盖范围. 因此两个引擎之间观察到的差异就是 AutoJs4 与 AutoJs6 的差异, 可反馈到 AutoJs6 的 issue 跟踪器.

******

### 快速开始

******

把这个文件保存到 AutoJs6 工作目录, 然后通过 `使用 AutoJs4 运行` 执行:

```javascript
console.log(context.getPackageName() + " / Rhino " + org.mozilla.javascript.Context.getCurrentContext().getImplementationVersion());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 控制台会打印带 `[AutoJs4]` 前缀的日志, 第一行显示执行脚本的 AutoJs4 包名与 Rhino 版本.

******

### 兼容性和范围

******

- 引擎: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 克隆), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 源码以 Git submodule 引入并原样编译.
- 平台: Android 7.0 (API 24) 或更高版本, 且支持 32 位应用. 单 ABI APK 覆盖 `armeabi-v7a, x86`, `universal` APK 包含两者. 64 位专用设备无法安装本插件, 与无法安装 AutoJs4 的情形一致.
- 脚本: `.js` 文件, `.auto` Root 录制文件以及含 `project.json` 的项目文件夹 (遵循 `main` 入口与 `features`). `"ui";` 脚本在插件进程中打开 AutoJs4 自己的脚本 Activity.
- 权限: 存储权限 (传统外部存储, `targetSdk 28`), 悬浮窗与 AutoJs4 无障碍服务都单独授予插件, 与 AutoJs6 无关. Root 功能与 AutoJs4 一样使用设备上的 `su`.
- 并发: 最多同时运行 16 个 AutoJs4 脚本; 在 AutoJs6 中停止任务会停止 AutoJs4 引擎, 插件进程崩溃会被报告为失败的任务.

******

### 权限和完整性

******

- Wake Activity 与两个导出服务均要求 `org.autojs.permission.PLUGIN`; 脚本以插件 UID 运行在 `:autojs4_runtime` 进程中, 从不进入 AutoJs6 进程.
- 脚本以绝对路径寻址. 插件只凭用户授予它的存储权限读写文件, 并拒绝路径不是绝对路径或文件不可读的请求.
- 控制台输出通过有界的 oneway 回调分块回传 AutoJs6, 每块不超过 64 KiB; 终止事件只携带状态, 错误信息与堆栈摘要.
- AutoJs4 原有的外部入口 (运行意图, 快捷方式, Tasker) 均不导出, 只有 AutoJs6 宿主可以通过受保护的 Binder 服务启动脚本.

******

### 插件接口

******

供 AutoJs6 宿主和插件开发者使用的稳定标识和限制:

```text
application id: io.github.supermonster003.autojs6.plugin.autojs4.runtime
plugin id: autojs4-runtime
engine: autojs4
variant: default
service action: org.autojs.plugin.autojs4.RUNTIME
service category: autojs4-runtime
aidl interface: org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimePlugin
contract version: 1
aidl methods: getInfo(), getRuntimeInfo(), runScript(request, callback), cancelScript(executionId)
minimum host build: 5334 (6.8.0)
embedded AutoJs4: 4.4.0 (3fb08269198ebd49a880ab3ca2621383eaa10443)
rhino: 1.7.7.2
supported abis: armeabi-v7a, x86
max concurrent executions: 16
max log chunk: 64 KiB
```

`AutoJs4RuntimeService` 通过 action `org.autojs.plugin.autojs4.RUNTIME` 和 category `autojs4-runtime` 被发现. `runScript` 校验请求 Bundle (脚本路径, 工作目录, 可选的项目文件夹, 参数, 延迟, 间隔与循环次数), 启动 AutoJs4 执行并立即返回受理 Bundle, 随后 `started`, `log` 与 `finished` 事件经回调送达. `cancelScript` 停止运行中的执行, 或为尚未调度的执行记录一次取消. 完整契约见 AutoJs6 仓库 ([协议 V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)).

16 KB 页大小: 插件只携带 AutoJs4 自带的 32 位原生库, 没有 64 位原生代码, 因此对齐门禁无需检查任何库, APK 也无法安装到 64 位专用设备. 是否以重新编译的 OpenCV 3.4.x 增加 `arm64-v8a` 构建, 作为待决问题记录在 [roadmap](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md) 中.

******

### 发布历史

******

#### v1.0.0

_2026/10/12_

- `提示` 首个版本完全镜像 AutoJs4, 包括其仅有 32 位的原生库, 因此插件无法安装到 64 位专用设备
- `功能` 通过 AutoJs6 文件管理器的 `使用 AutoJs4 运行` 菜单项, 以内嵌的 AutoJs4 4.4.0 引擎 (Rhino 1.7.7.2, OpenCV 3.4.3) 运行 `.js` 文件, `.auto` 录制文件与项目文件夹
- `功能` 受保护的运行时服务与版本化的 `autojs4-runtime-api` AIDL 契约: 文件路径请求, 流式控制台行, 开始与结束事件, 取消及并发执行
- `功能` 插件设置页提供无障碍服务, 悬浮窗, 存储权限, 电池优化与 Root 状态, 以及 `显示 AutoJs4 控制台窗口` 开关
- `功能` 发布 `armeabi-v7a`, `x86` 与 `universal` APK, 提供 Wake 激活, 完整 PluginInfo 元数据, 以及 10 种语言的插件中心说明, README 与 changelog
- `依赖` 以 Git submodule 内嵌 AutoJs4 4.4.0, 对应 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)

##### 更多发布历史

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hans.md)

******

### 构建和验证

******

构建需要 JDK 17 或更高版本, Python 3 与 Android SDK 37 (AutoJs4 引擎模块按 API 30 编译, 由 Android SDK 按需下载). 克隆时使用 `--recurse-submodules` 以填充 `autojs4/`. 标准检查会验证生成文档, JVM 测试, debug APK, instrumentation 包与 Android Lint.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### 本地化和文档生成

******

编辑 JSON 和 Markdown 模板后运行 `py .python/generate_markdown.py`. 不要手动编辑生成的 README, changelog 或插件说明文件. `--check` 模式会在不写入文件的情况下验证语言结构, 版本一致性, 本地化资源, 孤立文件和生成文件漂移.

```text
.readme/common.json
.readme/lang_*.json
.readme/template_readme.md
.readme/template_plugin_instruction.md
.changelog/lang_*.json
.changelog/template_changelog.md
.python/generate_markdown.py
app/src/main/assets/doc/CHANGELOG-*.md
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
```

启动器图标是 `.python/generate_provisional_icon.py` 渲染的临时图案, 维护者图稿到位前暂用; 可用 `py .python/generate_provisional_icon.py --check` 复核.

******

### 许可证

******

插件代码采用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE). 内嵌的 AutoJs4 源码采用 MPL 2.0 并附带 AutoJs4 仓库的非商业性使用条款; Rhino, OpenCV, jackpal 终端库与其他第三方组件保留各自的许可证. 参阅 [第三方声明](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md).

******

### 链接

******

- AutoJs6 项目: https://github.com/SuperMonster003/AutoJs6
- AutoJs4 项目: https://github.com/SuperMonster003/AutoJs4
- AutoJs4 文档: https://github.com/SuperMonster003/AutoJs4-Documentation
- AutoJs4 Runtime 协议 V1: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- AutoJs4 Runtime roadmap: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- 第三方声明: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
