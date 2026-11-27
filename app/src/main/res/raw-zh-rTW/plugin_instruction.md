AutoJs4 Runtime 內嵌了 AutoJs4 (Auto.js 4.1.1 Alpha2 的複製專案) 未經修改的引擎, 讓 AutoJs6 不必另外安裝 AutoJs4 就能用它執行指令碼. 主控台輸出會傳回 AutoJs6, 方便在同一台裝置上比較指令碼在 AutoJs4 與 AutoJs6 上的表現. 本外掛主要供從 Auto.js 4.x 移轉舊指令碼的使用者對照執行結果, 只在 AutoJs6 中撰寫新指令碼的一般使用者不需要安裝.

### 開始使用

1. 確認 AutoJs6 外掛中心裡本外掛已啟用.
2. 從外掛中心開啟本外掛的設定, 在 "權限與服務" 中依指令碼需要授予 "儲存空間權限", "懸浮視窗" 或 "無障礙服務".
3. 在 AutoJs6 檔案管理器中開啟 `.js` 檔案, `.auto` 檔案或專案資料夾的選單, 選擇 "使用 AutoJs4 執行" (AutoJs6 6.8.1 起位於 "AutoJs4" 子選單).
4. 執行記錄帶 `[AutoJs4]` 前綴顯示在 AutoJs6 主控台中; 工作出現在 AutoJs6 的工作清單中, 可以在那裡停止.

### 主要功能

- **使用 AutoJs4 執行**: 用 AutoJs4 引擎執行 `.js` 檔案, `.auto` 錄製檔案與含 `project.json` 的專案資料夾; `"ui";` 指令碼的介面在本外掛中顯示.
- **使用 AutoJs4 執行並對照**: 分別用 AutoJs6 與 AutoJs4 執行同一指令碼, 在 "AutoJs4 對照結果" 中顯示兩次主控台輸出是否一致以及各自的耗時.
- **顯示 AutoJs4 主控台視窗**: 設定中 "執行" 群組的開關, 開啟後 `console.show()` 會顯示 AutoJs4 的浮動主控台; 記錄一律傳回 AutoJs6.
- **權限與服務**: 設定中顯示儲存空間權限, 懸浮視窗, 無障礙服務, 忽略電池最佳化與 Root 權限的狀態, 輕觸即可授予或偵測.

### 在指令碼中使用

指令碼使用 AutoJs4 的 API (與 Auto.js 4.1.1 Alpha2 相同). 把下面的程式碼儲存為 `.js` 檔案後選擇 "使用 AutoJs4 執行", 主控台第一行會顯示本外掛的套件名稱, 表示指令碼由 AutoJs4 執行:

```js
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

- AutoJs6 才有的模組與寫法在 AutoJs4 中無法使用; 兩個引擎的結果不同時, 可以用 "使用 AutoJs4 執行並對照" 比較.
- 指令碼以本外掛的身分執行: 讀寫檔案依靠本外掛的儲存空間權限, Root 功能使用裝置上的 `su`.
- 最多同時執行 16 個 AutoJs4 指令碼.

### 執行需求

- Android 7.0 或更新版本, AutoJs6 6.8.0 或更新版本.
- 儲存空間權限用於讀寫指令碼使用的檔案.
- 懸浮視窗用於 AutoJs4 的主控台視窗與懸浮介面, 無障礙服務用於尋找控制項與自動點按等操作; 都在指令碼需要時再授予.
- x86 裝置上與 AutoJs4 原版一樣不含 OpenCV, 依賴它的影像功能 (例如找圖) 無法使用.

### 常見問題

**提示缺少權限時的處理**

本外掛的權限與 AutoJs6 的權限彼此獨立. 從外掛中心開啟本外掛的設定, 在 "權限與服務" 中授予提示的權限, 然後重新執行指令碼.

**兩個引擎結果不同的含義**

本外掛使用未經修改的 AutoJs4 引擎, Rhino 與 OpenCV 的版本也與 AutoJs4 相同, 所以結果的差異就是 AutoJs4 與 AutoJs6 之間的差異. 如果差異影響指令碼移轉, 可以回報到 [AutoJs6 的 issue 追蹤器](https://github.com/SuperMonster003/AutoJs6/issues).

**長時間執行的指令碼被系統中斷時的處理**

在本外掛設定的 "權限與服務" 中輕觸 "忽略電池最佳化", 允許本外掛不受電池最佳化限制.

AutoJs4 的指令碼 API 請見 [AutoJs4 文件](https://github.com/SuperMonster003/AutoJs4-Documentation), 原始碼與更多說明請見 [專案首頁](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime).
