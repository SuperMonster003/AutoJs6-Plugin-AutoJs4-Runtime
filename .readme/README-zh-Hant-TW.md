<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>使用內嵌的 Auto.js 4.1.1 Alpha2 引擎執行指令碼, 用於對照 AutoJs6 的執行結果</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### 語言

******

目前 README.md 支援以下語言:

- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-en.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ar.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-es.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-fr.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ru.md)
- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- 繁體中文 台灣 [zh-Hant-TW] # 目前

******

### 簡介

******

AutoJs4 Runtime 內嵌了 [AutoJs4](https://github.com/SuperMonster003/AutoJs4) 的引擎, 即作為 AutoJs6 行為基準的 Auto.js 4.1.1 Alpha2 複製專案. 安裝後, AutoJs6 檔案管理器中的 "使用 AutoJs4 執行" 會在本外掛自己的處理程序裡用原版 Rhino 1.7.7.2 引擎執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾, 主控台輸出回傳到 AutoJs6. 這樣無需另外安裝 AutoJs4, 就能在同一部裝置上檢查指令碼在 AutoJs4 與 AutoJs6 上的表現是否不同.

******

### 安裝和使用

******

1. 在 Android 7.0 (API 24) 或更高版本上使用 AutoJs6 build 5334 (6.8.0) 或更高版本.
2. 安裝與裝置 ABI 對應的發行 APK (多數現代手機為 `arm64-v8a`, 32 位元裝置為 `armeabi-v7a`, 模擬器為 `x86_64` 或 `x86`), 或安裝體積更大的 `universal` APK.
3. 開啟 AutoJs6 外掛中心並啟用 AutoJs4 Runtime. 如果 Android 仍將新安裝的套件保持在停止狀態, 請使用宿主顯示的 `啟用` 操作.
4. 從外掛中心開啟外掛設定, 按指令碼需要授予儲存空間權限, 懸浮視窗與無障礙服務; 這些權限屬於外掛自身, 與 AutoJs6 的權限相互獨立.
5. 在 AutoJs6 檔案管理器中開啟 `.js` 檔案, `.auto` 檔案或專案資料夾的選單, 選擇 `使用 AutoJs4 執行`. 工作會出現在 AutoJs6 的工作清單中並可在那裡停止.

> 外掛不修改 AutoJs4: 相同的 Rhino 1.7.7.2, armeabi-v7a 使用 AutoJs4 原版 OpenCV 3.4.3 程式庫, 64 位元裝置使用同一 OpenCV 版本的重編程式庫, `targetSdk 28` 與相同的引擎模組. 因此兩個引擎之間觀察到的差異就是 AutoJs4 與 AutoJs6 的差異, 可回報到 AutoJs6 的 issue 追蹤器.

******

### 快速開始

******

把這個檔案儲存到 AutoJs6 工作目錄, 然後透過 `使用 AutoJs4 執行` 執行:

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 主控台會列印帶 `[AutoJs4]` 前綴的日誌, 第一行顯示執行指令碼的外掛套件名稱, 以此確認指令碼由 AutoJs4 引擎執行.

******

### 相容性和範圍

******

- 引擎: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 複製), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 原始碼以 Git submodule 引入並原樣編譯; arm64-v8a 與 x86_64 的 OpenCV 程式庫以 NDK 28.2.13676358 從同一版本重新編譯, 並校驗匯出了原程式庫的全部 JNI 符號.
- 平台: Android 7.0 (API 24) 或更高版本. 單 ABI APK 涵蓋 `arm64-v8a, armeabi-v7a, x86_64, x86`, `universal` APK 包含全部四種. 與 AutoJs4 一樣, `x86` 建置不含 OpenCV.
- 指令碼: `.js` 檔案, `.auto` Root 錄製檔案以及含 `project.json` 的專案資料夾 (遵循 `main` 入口與 `features`). `"ui";` 指令碼在外掛處理程序中開啟 AutoJs4 自己的指令碼 Activity.
- 權限: 儲存空間權限 (傳統外部儲存空間, `targetSdk 28`), 懸浮視窗與 AutoJs4 無障礙服務都單獨授予外掛, 與 AutoJs6 無關. Root 功能與 AutoJs4 一樣使用裝置上的 `su`.
- 並行: 最多同時執行 16 個 AutoJs4 指令碼; 在 AutoJs6 中停止工作會停止 AutoJs4 引擎, 外掛處理程序當機會被回報為失敗的工作.

******

### 權限和完整性

******

- Wake Activity 與兩個匯出服務均要求 `org.autojs.permission.PLUGIN`; 指令碼以外掛 UID 執行在 `:autojs4_runtime` 處理程序中, 從不進入 AutoJs6 處理程序.
- 指令碼以絕對路徑定址. 外掛只憑使用者授予它的儲存空間權限讀寫檔案, 並拒絕路徑不是絕對路徑或檔案不可讀的請求.
- 主控台輸出透過有界的 oneway 回呼分塊回傳 AutoJs6, 每塊不超過 64 KiB; 終止事件只攜帶狀態, 錯誤訊息與堆疊摘要.
- AutoJs4 原有的外部入口 (執行意圖, 捷徑, Tasker) 均不匯出, 只有 AutoJs6 宿主可以透過受保護的 Binder 服務啟動指令碼.

******

### 外掛介面

******

供 AutoJs6 宿主和外掛開發者使用的穩定識別碼和限制:

```text
application id: io.github.supermonster003.autojs6.plugin.autojs4.runtime
plugin id: autojs4-runtime
engine: autojs4
variant: default
service action: org.autojs.plugin.autojs4.RUNTIME
service category: autojs4-runtime
aidl interface: org.autojs.plugin.autojs4.runtime.api.IAutoJs4RuntimePlugin
contract version: 1
aidl methods: getInfo(), getRuntimeInfo(), runScript(request, callback), cancelScript(executionId)
minimum host build: 5334 (6.8.0)
embedded AutoJs4: 4.4.0 (3fb08269198ebd49a880ab3ca2621383eaa10443)
rhino: 1.7.7.2
supported abis: arm64-v8a, armeabi-v7a, x86_64, x86
max concurrent executions: 16
max log chunk: 64 KiB
```

`AutoJs4RuntimeService` 透過 action `org.autojs.plugin.autojs4.RUNTIME` 和 category `autojs4-runtime` 被發現. `runScript` 校驗請求 Bundle (指令碼路徑, 工作目錄, 可選的專案資料夾, 參數, 延遲, 間隔與循環次數), 啟動 AutoJs4 執行並立即傳回受理 Bundle, 隨後 `started`, `log` 與 `finished` 事件經回呼送達. `cancelScript` 停止執行中的執行, 或為尚未排程的執行記錄一次取消. 完整契約見 AutoJs6 儲存庫 ([協定 V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)).

16 KB 分頁大小: arm64-v8a 與 x86_64 的原生程式庫 (以 NDK 28.2.13676358 重編的 OpenCV 3.4.3 與宿主 AutoJs6 重編的 jackpal 終端程式庫) 均以 16 KB 最大分頁大小連結, 共享對齊門禁在建置時校驗每個 ELF PT_LOAD 區段. 尚未在 16 KB 分頁裝置上實際執行.

******

### 發行記錄

******

#### v1.0.1

_2026/10/12_

- `改善` 指令碼需要無障礙服務而外掛的無障礙服務未開啟時, 開啟系統無障礙設定頁的同時以 toast 說明原因

#### v1.0.0

_2026/10/12_

- `提示` armeabi-v7a 使用 AutoJs4 原版 OpenCV 3.4.3 原生程式庫, 64 位元裝置使用以 NDK 28.2 重編的同一版本; x86 建置與 AutoJs4 一樣不含 OpenCV
- `功能` 透過 AutoJs6 檔案管理器的 `使用 AutoJs4 執行` 選單項目, 以內嵌的 AutoJs4 4.4.0 引擎 (Rhino 1.7.7.2, OpenCV 3.4.3) 執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾
- `功能` 受保護的執行階段服務與版本化的 `autojs4-runtime-api` AIDL 契約: 檔案路徑請求, 串流主控台行, 開始與結束事件, 取消及並行執行
- `功能` 外掛設定頁提供無障礙服務, 懸浮視窗, 儲存空間權限, 電池最佳化與 Root 狀態, 以及 `顯示 AutoJs4 主控台視窗` 開關
- `功能` 發行 `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` 與 `universal` APK, 提供 Wake 啟用, 完整 PluginInfo 中繼資料, 以及 10 種語言的外掛中心說明, README 與 changelog
- `改善` arm64-v8a 與 x86_64 的 OpenCV 3.4.3 及 jackpal 終端程式庫以 16 KB 分頁對齊建置, 並在建置時校驗 ELF 對齊與原程式庫 JNI 符號的完整涵蓋
- `相依項目` 以 Git submodule 內嵌 AutoJs4 4.4.0, 對應 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)

##### 更多發行記錄

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hant-TW.md)

******

### 建置和驗證

******

建置需要 JDK 17 或更高版本, Python 3 與 Android SDK 37 (AutoJs4 引擎模組按 API 30 編譯, 由 Android SDK 按需下載). 複製時使用 `--recurse-submodules` 以填充 `autojs4/`. 標準檢查會驗證產生的文件, JVM 測試, debug APK, instrumentation 套件與 Android Lint.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### 本地化和文件產生

******

編輯 JSON 和 Markdown 範本後執行 `py .python/generate_markdown.py`. 不要手動編輯產生的 README, changelog 或外掛說明檔案. `--check` 模式會在不寫入檔案的情況下驗證語言結構, 版本一致性, 本地化資源, 孤立檔案和產生檔案漂移.

```text
.readme/common.json
.readme/lang_*.json
.readme/template_readme.md
.readme/template_plugin_instruction.md
.changelog/lang_*.json
.changelog/template_changelog.md
.python/generate_markdown.py
app/src/main/assets/doc/CHANGELOG-*.md
app/src/main/res/values*/strings.xml
app/src/main/res/raw*/plugin_instruction.md
```

圖示保留維護者提供的 [PNG 原稿](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/tree/master/.python/icons) (亮色與暗色主題各一張) 及原始構圖. 可透過 Icon Studio 調整, 使用 `python .python/generate_icon_studio.py --check` 核對配方與生成資源的一致性.

******

### 授權條款

******

外掛程式碼採用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE). 內嵌的 AutoJs4 原始碼採用 MPL 2.0 並附帶 AutoJs4 儲存庫的非商業性使用條款; Rhino, OpenCV, jackpal 終端程式庫與其他第三方元件保留各自的授權條款. 參閱 [第三方聲明](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md).

******

### 連結

******

- AutoJs6 專案: https://github.com/SuperMonster003/AutoJs6
- AutoJs4 專案: https://github.com/SuperMonster003/AutoJs4
- AutoJs4 文件: https://github.com/SuperMonster003/AutoJs4-Documentation
- AutoJs4 Runtime 協定 V1: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- AutoJs4 Runtime roadmap: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- 第三方聲明: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
