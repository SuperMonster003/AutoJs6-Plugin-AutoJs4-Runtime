AutoJs4 Runtime 内嵌了 [AutoJs4](https://github.com/SuperMonster003/AutoJs4) 的引擎, 即作为 AutoJs6 行为基准的 Auto.js 4.1.1 Alpha2 克隆项目. 安装后, AutoJs6 文件管理器中的 "使用 AutoJs4 运行" 会在本插件自己的进程里用原版 Rhino 1.7.7.2 引擎运行 `.js` 文件, `.auto` 录制文件与项目文件夹, 控制台输出回传到 AutoJs6. 这样无需另外安装 AutoJs4, 就能在同一台设备上检查脚本在 AutoJs4 与 AutoJs6 上的表现是否不同.

此版本内嵌 AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`), 使用 Rhino 1.7.7.2 与 OpenCV 3.4.3, 并在隔离的 `:autojs4_runtime` 进程中运行每个脚本. 开始, 控制台行与结束状态通过宿主契约返回 AutoJs6, 任务可在 AutoJs6 任务列表中停止.

### 快速开始

```javascript
console.log(context.getPackageName() + " / Rhino " + org.mozilla.javascript.Context.getCurrentContext().getImplementationVersion());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 控制台会打印带 `[AutoJs4]` 前缀的日志, 第一行显示执行脚本的 AutoJs4 包名与 Rhino 版本.

### 兼容性和范围

- 引擎: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 克隆), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 源码以 Git submodule 引入并原样编译.
- 平台: Android 7.0 (API 24) 或更高版本, 且支持 32 位应用. 单 ABI APK 覆盖 `armeabi-v7a, x86`, `universal` APK 包含两者. 64 位专用设备无法安装本插件, 与无法安装 AutoJs4 的情形一致.
- 脚本: `.js` 文件, `.auto` Root 录制文件以及含 `project.json` 的项目文件夹 (遵循 `main` 入口与 `features`). `"ui";` 脚本在插件进程中打开 AutoJs4 自己的脚本 Activity.
- 权限: 存储权限 (传统外部存储, `targetSdk 28`), 悬浮窗与 AutoJs4 无障碍服务都单独授予插件, 与 AutoJs6 无关. Root 功能与 AutoJs4 一样使用设备上的 `su`.
- 并发: 最多同时运行 16 个 AutoJs4 脚本; 在 AutoJs6 中停止任务会停止 AutoJs4 引擎, 插件进程崩溃会被报告为失败的任务.

ABI 选择, 权限, 精确契约与本版本范围请参阅 [项目 README](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
