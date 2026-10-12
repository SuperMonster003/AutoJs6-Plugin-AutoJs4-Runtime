******

### 发布历史

******

# v1.0.1

###### 2026/10/12

* `改进` 脚本需要无障碍服务而插件的无障碍服务未开启时, 打开系统无障碍设置页的同时以 toast 说明原因

# v1.0.0

###### 2026/10/12

* `提示` armeabi-v7a 使用 AutoJs4 原版 OpenCV 3.4.3 原生库, 64 位设备使用以 NDK 28.2 重编的同一版本; x86 构建与 AutoJs4 一样不含 OpenCV
* `功能` 通过 AutoJs6 文件管理器的 `使用 AutoJs4 运行` 菜单项, 以内嵌的 AutoJs4 4.4.0 引擎 (Rhino 1.7.7.2, OpenCV 3.4.3) 运行 `.js` 文件, `.auto` 录制文件与项目文件夹
* `功能` 受保护的运行时服务与版本化的 `autojs4-runtime-api` AIDL 契约: 文件路径请求, 流式控制台行, 开始与结束事件, 取消及并发执行
* `功能` 插件设置页提供无障碍服务, 悬浮窗, 存储权限, 电池优化与 Root 状态, 以及 `显示 AutoJs4 控制台窗口` 开关
* `功能` 发布 `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` 与 `universal` APK, 提供 Wake 激活, 完整 PluginInfo 元数据, 以及 10 种语言的插件中心说明, README 与 changelog
* `改进` arm64-v8a 与 x86_64 的 OpenCV 3.4.3 及 jackpal 终端库以 16 KB 页对齐构建, 并在构建时校验 ELF 对齐与原库 JNI 符号的完整覆盖
* `依赖` 以 Git submodule 内嵌 AutoJs4 4.4.0, 对应 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
