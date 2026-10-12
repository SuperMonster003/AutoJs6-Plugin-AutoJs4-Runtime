******

### 發佈記錄

******

# v1.0.0

###### 2026/10/12

* `提示` armeabi-v7a 使用 AutoJs4 原版 OpenCV 3.4.3 原生庫, 64 位裝置使用以 NDK 28.2 重編的同一版本; x86 建置與 AutoJs4 一樣不含 OpenCV
* `功能` 透過 AutoJs6 檔案管理器的 `使用 AutoJs4 執行` 選單項, 以內嵌的 AutoJs4 4.4.0 引擎 (Rhino 1.7.7.2, OpenCV 3.4.3) 執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾
* `功能` 受保護的執行時服務與版本化的 `autojs4-runtime-api` AIDL 契約: 檔案路徑請求, 串流控制台行, 開始與結束事件, 取消及並發執行
* `功能` 插件設定頁提供無障礙服務, 懸浮視窗, 儲存權限, 電池優化與 Root 狀態, 以及 `顯示 AutoJs4 控制台視窗` 開關
* `功能` 發佈 `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` 與 `universal` APK, 提供 Wake 啟動, 完整 PluginInfo 元資料, 以及 10 種語言的插件中心說明, README 與 changelog
* `改善` arm64-v8a 與 x86_64 的 OpenCV 3.4.3 及 jackpal 終端庫以 16 KB 頁對齊建置, 並在建置時校驗 ELF 對齊與原庫 JNI 符號的完整覆蓋
* `依賴` 以 Git submodule 內嵌 AutoJs4 4.4.0, 對應 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
