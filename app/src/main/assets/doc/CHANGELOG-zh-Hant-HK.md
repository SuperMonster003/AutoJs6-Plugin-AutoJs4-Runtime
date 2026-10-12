******

### 發佈記錄

******

# v1.0.0

###### 2026/10/12

* `提示` 首個版本完全鏡像 AutoJs4, 包括其僅有 32 位的原生庫, 因此插件無法安裝到 64 位專用裝置
* `功能` 透過 AutoJs6 檔案管理器的 `使用 AutoJs4 執行` 選單項, 以內嵌的 AutoJs4 4.4.0 引擎 (Rhino 1.7.7.2, OpenCV 3.4.3) 執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾
* `功能` 受保護的執行時服務與版本化的 `autojs4-runtime-api` AIDL 契約: 檔案路徑請求, 串流控制台行, 開始與結束事件, 取消及並發執行
* `功能` 插件設定頁提供無障礙服務, 懸浮視窗, 儲存權限, 電池優化與 Root 狀態, 以及 `顯示 AutoJs4 控制台視窗` 開關
* `功能` 發佈 `armeabi-v7a`, `x86` 與 `universal` APK, 提供 Wake 啟動, 完整 PluginInfo 元資料, 以及 10 種語言的插件中心說明, README 與 changelog
* `依賴` 以 Git submodule 內嵌 AutoJs4 4.4.0, 對應 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
