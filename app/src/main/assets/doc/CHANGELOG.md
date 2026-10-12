******

### 发布历史

******

# v1.0.0

###### 2026/10/12

* `提示` 首个版本完全镜像 AutoJs4, 包括其仅有 32 位的原生库, 因此插件无法安装到 64 位专用设备
* `功能` 通过 AutoJs6 文件管理器的 `使用 AutoJs4 运行` 菜单项, 以内嵌的 AutoJs4 4.4.0 引擎 (Rhino 1.7.7.2, OpenCV 3.4.3) 运行 `.js` 文件, `.auto` 录制文件与项目文件夹
* `功能` 受保护的运行时服务与版本化的 `autojs4-runtime-api` AIDL 契约: 文件路径请求, 流式控制台行, 开始与结束事件, 取消及并发执行
* `功能` 插件设置页提供无障碍服务, 悬浮窗, 存储权限, 电池优化与 Root 状态, 以及 `显示 AutoJs4 控制台窗口` 开关
* `功能` 发布 `armeabi-v7a`, `x86` 与 `universal` APK, 提供 Wake 激活, 完整 PluginInfo 元数据, 以及 10 种语言的插件中心说明, README 与 changelog
* `依赖` 以 Git submodule 内嵌 AutoJs4 4.4.0, 对应 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
