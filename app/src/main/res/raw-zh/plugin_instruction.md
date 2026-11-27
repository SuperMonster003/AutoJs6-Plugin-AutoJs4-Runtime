AutoJs4 Runtime 内嵌了 AutoJs4 (Auto.js 4.1.1 Alpha2 的克隆项目) 未经修改的引擎, AutoJs6 无需另外安装 AutoJs4 就能用它运行脚本. 控制台输出回传 AutoJs6, 便于在同一台设备上对照脚本在两者上的表现. 本插件主要给从 Auto.js 4.x 迁移旧脚本的用户对照运行结果用, 只在 AutoJs6 中编写新脚本的一般用户不需要安装.

### 开始使用

1. 确认 AutoJs6 插件中心里本插件已启用.
2. 从插件中心打开本插件的设置, 在 "权限与服务" 中按脚本需要授予 "存储权限", "悬浮窗" 或 "无障碍服务".
3. 在 AutoJs6 文件管理器中打开 `.js`, `.auto` 文件或项目文件夹的菜单, 选择 "使用 AutoJs4 运行" (6.8.1 起在 "AutoJs4" 子菜单中).
4. 运行日志带 `[AutoJs4]` 前缀显示在 AutoJs6 控制台中; 任务出现在 AutoJs6 的任务列表中, 可以在那里停止.

### 主要功能

- **使用 AutoJs4 运行**: 用 AutoJs4 运行 `.js` 文件, `.auto` 录制文件与含 `project.json` 的项目文件夹; `"ui";` 脚本的界面在本插件中显示.
- **使用 AutoJs4 运行并对照**: 分别用 AutoJs6 与 AutoJs4 运行同一脚本, 在 "AutoJs4 对照结果" 中显示两次控制台输出是否一致以及各自的用时.
- **显示 AutoJs4 控制台窗口**: 设置中 "运行" 分组的开关, 开启后 `console.show()` 会显示 AutoJs4 的浮动控制台; 日志始终回传 AutoJs6.
- **权限与服务**: 设置中显示存储权限, 悬浮窗, 无障碍服务, 忽略电池优化与 Root 权限的状态, 点按即可授予或检测.

### 在脚本中使用

脚本使用 AutoJs4 的 API (与 Auto.js 4.1.1 Alpha2 相同). 把下面的代码保存为 `.js` 文件后选择 "使用 AutoJs4 运行", 控制台第一行会显示本插件的包名, 表示脚本由 AutoJs4 运行:

```js
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

- AutoJs6 才有的模块与写法在 AutoJs4 中不可用; 两个引擎的结果不同时, 可以用 "使用 AutoJs4 运行并对照" 比较.
- 脚本以本插件的身份运行: 读写文件依靠本插件的存储权限, Root 功能使用设备上的 `su`.
- 最多同时运行 16 个 AutoJs4 脚本.

### 运行要求

- Android 7.0 或更高版本, AutoJs6 6.8.0 或更高版本.
- 存储权限用于读写脚本使用的文件.
- 悬浮窗用于 AutoJs4 的控制台窗口与悬浮界面, 无障碍服务用于查找控件与自动点按等操作; 都在脚本需要时再授予.
- x86 设备上与 AutoJs4 原版一样不含 OpenCV, 依赖它的图像功能 (例如找图) 不可用.

### 常见问题

**提示缺少权限时的处理**

本插件的权限与 AutoJs6 的权限相互独立. 从插件中心打开本插件的设置, 在 "权限与服务" 中授予提示的权限, 然后重新运行脚本.

**两个引擎结果不同的含义**

本插件使用未经修改的 AutoJs4 引擎, Rhino 与 OpenCV 的版本也与 AutoJs4 相同, 所以结果的差异就是 AutoJs4 与 AutoJs6 之间的差异. 如果差异影响脚本迁移, 可以反馈到 [AutoJs6 的 issue 跟踪器](https://github.com/SuperMonster003/AutoJs6/issues).

**长时间运行的脚本被系统中断时的处理**

在本插件设置的 "权限与服务" 中点按 "忽略电池优化", 允许本插件不受电池优化限制.

AutoJs4 的脚本 API 见 [AutoJs4 文档](https://github.com/SuperMonster003/AutoJs4-Documentation), 源码与更多说明见 [项目主页](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
