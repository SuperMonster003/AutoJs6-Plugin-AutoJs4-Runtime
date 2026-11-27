AutoJs4 Runtime 內嵌了 AutoJs4 (Auto.js 4.1.1 Alpha2 的克隆專案) 未經修改的引擎, 讓 AutoJs6 無需另外安裝 AutoJs4 就能用它執行腳本. 控制台輸出回傳到 AutoJs6, 方便在同一部裝置上比較腳本在 AutoJs4 與 AutoJs6 上的表現. 本插件主要供從 Auto.js 4.x 遷移舊腳本的用戶對照執行結果, 只在 AutoJs6 中編寫新腳本的一般用戶不需要安裝.

### 開始使用

1. 確認 AutoJs6 插件中心裏本插件已啟用.
2. 從插件中心打開本插件的設置, 在 "權限與服務" 中按腳本需要授予 "儲存權限", "懸浮視窗" 或 "無障礙服務".
3. 在 AutoJs6 檔案管理器中打開 `.js` 檔案, `.auto` 檔案或專案資料夾的選單, 選擇 "使用 AutoJs4 執行" (AutoJs6 6.8.1 起位於 "AutoJs4" 子選單).
4. 執行記錄帶 `[AutoJs4]` 前綴顯示在 AutoJs6 控制台中; 任務出現在 AutoJs6 的任務列表中, 可以在那裏停止.

### 主要功能

- **使用 AutoJs4 執行**: 用 AutoJs4 引擎執行 `.js` 檔案, `.auto` 錄製檔案與含 `project.json` 的專案資料夾; `"ui";` 腳本的介面在本插件中顯示.
- **使用 AutoJs4 執行並對照**: 分別用 AutoJs6 與 AutoJs4 執行同一腳本, 在 "AutoJs4 對照結果" 中顯示兩次控制台輸出是否一致以及各自的用時.
- **顯示 AutoJs4 控制台視窗**: 設置中 "執行" 分組的開關, 開啟後 `console.show()` 會顯示 AutoJs4 的浮動控制台; 記錄始終回傳 AutoJs6.
- **權限與服務**: 設置中顯示儲存權限, 懸浮視窗, 無障礙服務, 忽略電池優化與 Root 權限的狀態, 輕按即可授予或檢測.

### 在腳本中使用

腳本使用 AutoJs4 的 API (與 Auto.js 4.1.1 Alpha2 相同). 把下面的代碼儲存為 `.js` 檔案後選擇 "使用 AutoJs4 執行", 控制台第一行會顯示本插件的套件名稱, 表示腳本由 AutoJs4 執行:

```js
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

- AutoJs6 才有的模組與寫法在 AutoJs4 中不可用; 兩個引擎的結果不同時, 可以用 "使用 AutoJs4 執行並對照" 比較.
- 腳本以本插件的身份執行: 讀寫檔案依靠本插件的儲存權限, Root 功能使用裝置上的 `su`.
- 最多同時執行 16 個 AutoJs4 腳本.

### 運行要求

- Android 7.0 或更高版本, AutoJs6 6.8.0 或更高版本.
- 儲存權限用於讀寫腳本使用的檔案.
- 懸浮視窗用於 AutoJs4 的控制台視窗與懸浮介面, 無障礙服務用於尋找控件與自動點按等操作; 都在腳本需要時再授予.
- x86 裝置上與 AutoJs4 原版一樣不含 OpenCV, 依賴它的圖像功能 (例如找圖) 不可用.

### 常見問題

**提示缺少權限時的處理**

本插件的權限與 AutoJs6 的權限相互獨立. 從插件中心打開本插件的設置, 在 "權限與服務" 中授予提示的權限, 然後重新執行腳本.

**兩個引擎結果不同的含義**

本插件使用未經修改的 AutoJs4 引擎, Rhino 與 OpenCV 的版本也與 AutoJs4 相同, 所以結果的差異就是 AutoJs4 與 AutoJs6 之間的差異. 如果差異影響腳本遷移, 可以回報到 [AutoJs6 的 issue 追蹤器](https://github.com/SuperMonster003/AutoJs6/issues).

**長時間執行的腳本被系統中斷時的處理**

在本插件設置的 "權限與服務" 中輕按 "忽略電池優化", 允許本插件不受電池優化限制.

AutoJs4 的腳本 API 見 [AutoJs4 文件](https://github.com/SuperMonster003/AutoJs4-Documentation), 原始碼與更多說明見 [項目主頁](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
