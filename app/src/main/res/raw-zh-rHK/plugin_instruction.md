AutoJs4 Runtime 內嵌了 [AutoJs4](https://github.com/SuperMonster003/AutoJs4) 的引擎, 即作為 AutoJs6 行為基準的 Auto.js 4.1.1 Alpha2 克隆專案. 安裝後, AutoJs6 檔案管理器中的 "使用 AutoJs4 執行" 會在本插件自己的進程裏用原版 Rhino 1.7.7.2 引擎執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾, 控制台輸出回傳到 AutoJs6. 這樣無需另外安裝 AutoJs4, 就能在同一部裝置上檢查腳本在 AutoJs4 與 AutoJs6 上的表現是否不同.

此版本內嵌 AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`), 使用 Rhino 1.7.7.2 與 OpenCV 3.4.3, 並在隔離的 `:autojs4_runtime` 進程中執行每個腳本. 開始, 控制台行與結束狀態透過宿主契約返回 AutoJs6, 任務可在 AutoJs6 任務列表中停止.

### 快速開始

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 控制台會列印帶 `[AutoJs4]` 前綴的日誌, 第一行顯示執行腳本的插件套件名稱, 以此確認腳本由 AutoJs4 引擎執行.

### 兼容性和範圍

- 引擎: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 克隆), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 原始碼以 Git submodule 引入並原樣編譯; arm64-v8a 與 x86_64 的 OpenCV 庫以 NDK 28.2.13676358 從同一版本重新編譯, 並校驗匯出了原庫的全部 JNI 符號.
- 平台: Android 7.0 (API 24) 或更高版本. 單 ABI APK 覆蓋 `arm64-v8a, armeabi-v7a, x86_64, x86`, `universal` APK 包含全部四種. 與 AutoJs4 一樣, `x86` 建置不含 OpenCV.
- 腳本: `.js` 檔案, `.auto` Root 錄製檔案以及含 `project.json` 的專案資料夾 (遵循 `main` 入口與 `features`). `"ui";` 腳本在插件進程中打開 AutoJs4 自己的腳本 Activity.
- 權限: 儲存權限 (傳統外部儲存, `targetSdk 28`), 懸浮視窗與 AutoJs4 無障礙服務都單獨授予插件, 與 AutoJs6 無關. Root 功能與 AutoJs4 一樣使用裝置上的 `su`.
- 並發: 最多同時執行 16 個 AutoJs4 腳本; 在 AutoJs6 中停止任務會停止 AutoJs4 引擎, 插件進程崩潰會被報告為失敗的任務.

ABI 選擇, 權限, 精確契約與本版本範圍請參閱 [專案 README](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
