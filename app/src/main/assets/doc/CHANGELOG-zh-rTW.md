******

### 發行記錄

******

# v1.0.0

###### 2026/10/12

* `提示` 首個版本完全鏡像 AutoJs4, 包括其僅有 32 位元的原生程式庫, 因此外掛無法安裝到 64 位元專用裝置
* `功能` 透過 AutoJs6 檔案管理器的 `使用 AutoJs4 執行` 選單項目, 以內嵌的 AutoJs4 4.4.0 引擎 (Rhino 1.7.7.2, OpenCV 3.4.3) 執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾
* `功能` 受保護的執行階段服務與版本化的 `autojs4-runtime-api` AIDL 契約: 檔案路徑請求, 串流主控台行, 開始與結束事件, 取消及並行執行
* `功能` 外掛設定頁提供無障礙服務, 懸浮視窗, 儲存空間權限, 電池最佳化與 Root 狀態, 以及 `顯示 AutoJs4 主控台視窗` 開關
* `功能` 發行 `armeabi-v7a`, `x86` 與 `universal` APK, 提供 Wake 啟用, 完整 PluginInfo 中繼資料, 以及 10 種語言的外掛中心說明, README 與 changelog
* `相依項目` 以 Git submodule 內嵌 AutoJs4 4.4.0, 對應 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
