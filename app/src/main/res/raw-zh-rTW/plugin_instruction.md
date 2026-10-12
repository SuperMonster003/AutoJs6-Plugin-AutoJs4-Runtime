AutoJs4 Runtime 內嵌了 [AutoJs4](https://github.com/SuperMonster003/AutoJs4) 的引擎, 即作為 AutoJs6 行為基準的 Auto.js 4.1.1 Alpha2 複製專案. 安裝後, AutoJs6 檔案管理器中的 "使用 AutoJs4 執行" 會在本外掛自己的處理程序裡用原版 Rhino 1.7.7.2 引擎執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾, 主控台輸出回傳到 AutoJs6. 這樣無需另外安裝 AutoJs4, 就能在同一部裝置上檢查指令碼在 AutoJs4 與 AutoJs6 上的表現是否不同.

此版本內嵌 AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`), 使用 Rhino 1.7.7.2 與 OpenCV 3.4.3, 並在隔離的 `:autojs4_runtime` 處理程序中執行每個指令碼. 開始, 主控台行與結束狀態透過宿主契約傳回 AutoJs6, 工作可在 AutoJs6 工作清單中停止.

### 快速開始

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 主控台會列印帶 `[AutoJs4]` 前綴的日誌, 第一行顯示執行指令碼的外掛套件名稱, 以此確認指令碼由 AutoJs4 引擎執行.

### 相容性和範圍

- 引擎: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 複製), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 原始碼以 Git submodule 引入並原樣編譯.
- 平台: Android 7.0 (API 24) 或更高版本, 且支援 32 位元應用程式. 單 ABI APK 涵蓋 `armeabi-v7a, x86`, `universal` APK 包含兩者. 64 位元專用裝置無法安裝本外掛, 與無法安裝 AutoJs4 的情形一致.
- 指令碼: `.js` 檔案, `.auto` Root 錄製檔案以及含 `project.json` 的專案資料夾 (遵循 `main` 入口與 `features`). `"ui";` 指令碼在外掛處理程序中開啟 AutoJs4 自己的指令碼 Activity.
- 權限: 儲存空間權限 (傳統外部儲存空間, `targetSdk 28`), 懸浮視窗與 AutoJs4 無障礙服務都單獨授予外掛, 與 AutoJs6 無關. Root 功能與 AutoJs4 一樣使用裝置上的 `su`.
- 並行: 最多同時執行 16 個 AutoJs4 指令碼; 在 AutoJs6 中停止工作會停止 AutoJs4 引擎, 外掛處理程序當機會被回報為失敗的工作.

ABI 選擇, 權限, 精確契約與本版本範圍請參閱 [專案 README](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
