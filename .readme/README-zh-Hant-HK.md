<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>使用內嵌的 Auto.js 4.1.1 Alpha2 引擎執行腳本, 用於對照 AutoJs6 的執行結果</p>

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
- 繁體中文 香港 [zh-Hant-HK] # 目前
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### 簡介

******

AutoJs4 Runtime 內嵌了 [AutoJs4](https://github.com/SuperMonster003/AutoJs4) 的引擎, 即作為 AutoJs6 行為基準的 Auto.js 4.1.1 Alpha2 克隆專案. 安裝後, AutoJs6 檔案管理器中的 "使用 AutoJs4 執行" 會在本插件自己的進程裏用原版 Rhino 1.7.7.2 引擎執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾, 控制台輸出回傳到 AutoJs6. 這樣無需另外安裝 AutoJs4, 就能在同一部裝置上檢查腳本在 AutoJs4 與 AutoJs6 上的表現是否不同.

******

### 安裝和使用

******

1. 在 Android 7.0 (API 24) 或更高版本上使用 AutoJs6 build 5334 (6.8.0) 或更高版本. 裝置需要能執行 32 位應用, 與 AutoJs4 的要求相同.
2. 安裝與裝置 ABI 對應的發佈 APK (ARM 裝置為 `armeabi-v7a`, 模擬器為 `x86`), 或安裝 `universal` APK.
3. 打開 AutoJs6 插件中心並啟用 AutoJs4 Runtime. 如果 Android 仍將新安裝的套件保持在停止狀態, 請使用宿主顯示的 `啟動` 操作.
4. 從插件中心打開插件設定, 按腳本需要授予儲存權限, 懸浮視窗與無障礙服務; 這些權限屬於插件自身, 與 AutoJs6 的權限相互獨立.
5. 在 AutoJs6 檔案管理器中打開 `.js` 檔案, `.auto` 檔案或專案資料夾的選單, 選擇 `使用 AutoJs4 執行`. 任務會出現在 AutoJs6 的任務列表中並可在那裏停止.

> 插件不修改 AutoJs4: 相同的 Rhino 1.7.7.2, 相同的 OpenCV 3.4.3 原生庫, `targetSdk 28` 與相同的 32 位 ABI 覆蓋範圍. 因此兩個引擎之間觀察到的差異就是 AutoJs4 與 AutoJs6 的差異, 可回饋到 AutoJs6 的 issue 追蹤器.

******

### 快速開始

******

把這個檔案儲存到 AutoJs6 工作目錄, 然後透過 `使用 AutoJs4 執行` 執行:

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 控制台會列印帶 `[AutoJs4]` 前綴的日誌, 第一行顯示執行腳本的插件套件名稱, 以此確認腳本由 AutoJs4 引擎執行.

******

### 兼容性和範圍

******

- 引擎: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 克隆), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 原始碼以 Git submodule 引入並原樣編譯.
- 平台: Android 7.0 (API 24) 或更高版本, 且支援 32 位應用. 單 ABI APK 覆蓋 `armeabi-v7a, x86`, `universal` APK 包含兩者. 64 位專用裝置無法安裝本插件, 與無法安裝 AutoJs4 的情形一致.
- 腳本: `.js` 檔案, `.auto` Root 錄製檔案以及含 `project.json` 的專案資料夾 (遵循 `main` 入口與 `features`). `"ui";` 腳本在插件進程中打開 AutoJs4 自己的腳本 Activity.
- 權限: 儲存權限 (傳統外部儲存, `targetSdk 28`), 懸浮視窗與 AutoJs4 無障礙服務都單獨授予插件, 與 AutoJs6 無關. Root 功能與 AutoJs4 一樣使用裝置上的 `su`.
- 並發: 最多同時執行 16 個 AutoJs4 腳本; 在 AutoJs6 中停止任務會停止 AutoJs4 引擎, 插件進程崩潰會被報告為失敗的任務.

******

### 權限和完整性

******

- Wake Activity 與兩個匯出服務均要求 `org.autojs.permission.PLUGIN`; 腳本以插件 UID 執行在 `:autojs4_runtime` 進程中, 從不進入 AutoJs6 進程.
- 腳本以絕對路徑定址. 插件只憑使用者授予它的儲存權限讀寫檔案, 並拒絕路徑不是絕對路徑或檔案不可讀的請求.
- 控制台輸出透過有界的 oneway 回呼分塊回傳 AutoJs6, 每塊不超過 64 KiB; 終止事件只攜帶狀態, 錯誤資訊與堆疊摘要.
- AutoJs4 原有的外部入口 (執行意圖, 捷徑, Tasker) 均不匯出, 只有 AutoJs6 宿主可以透過受保護的 Binder 服務啟動腳本.

******

### 插件介面

******

供 AutoJs6 宿主和插件開發者使用的穩定標識和限制:

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
supported abis: armeabi-v7a, x86
max concurrent executions: 16
max log chunk: 64 KiB
```

`AutoJs4RuntimeService` 透過 action `org.autojs.plugin.autojs4.RUNTIME` 和 category `autojs4-runtime` 被發現. `runScript` 校驗請求 Bundle (腳本路徑, 工作目錄, 可選的專案資料夾, 參數, 延遲, 間隔與循環次數), 啟動 AutoJs4 執行並立即返回受理 Bundle, 隨後 `started`, `log` 與 `finished` 事件經回呼送達. `cancelScript` 停止執行中的執行, 或為尚未排程的執行記錄一次取消. 完整契約見 AutoJs6 倉庫 ([協定 V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)).

16 KB 頁大小: 插件只攜帶 AutoJs4 自帶的 32 位原生庫, 沒有 64 位原生程式碼, 因此對齊門禁無需檢查任何庫, APK 也無法安裝到 64 位專用裝置. 是否以重新編譯的 OpenCV 3.4.x 增加 `arm64-v8a` 建置, 作為待決問題記錄在 [roadmap](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md) 中.

******

### 發佈記錄

******

#### v1.0.0

_2026/10/12_

- `提示` 首個版本完全鏡像 AutoJs4, 包括其僅有 32 位的原生庫, 因此插件無法安裝到 64 位專用裝置
- `功能` 透過 AutoJs6 檔案管理器的 `使用 AutoJs4 執行` 選單項, 以內嵌的 AutoJs4 4.4.0 引擎 (Rhino 1.7.7.2, OpenCV 3.4.3) 執行 `.js` 檔案, `.auto` 錄製檔案與專案資料夾
- `功能` 受保護的執行時服務與版本化的 `autojs4-runtime-api` AIDL 契約: 檔案路徑請求, 串流控制台行, 開始與結束事件, 取消及並發執行
- `功能` 插件設定頁提供無障礙服務, 懸浮視窗, 儲存權限, 電池優化與 Root 狀態, 以及 `顯示 AutoJs4 控制台視窗` 開關
- `功能` 發佈 `armeabi-v7a`, `x86` 與 `universal` APK, 提供 Wake 啟動, 完整 PluginInfo 元資料, 以及 10 種語言的插件中心說明, README 與 changelog
- `依賴` 以 Git submodule 內嵌 AutoJs4 4.4.0, 對應 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)

##### 更多發佈記錄

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-zh-Hant-HK.md)

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

編輯 JSON 和 Markdown 範本後執行 `py .python/generate_markdown.py`. 不要手動編輯產生的 README, changelog 或插件說明檔案. `--check` 模式會在不寫入檔案的情況下驗證語言結構, 版本一致性, 本地化資源, 孤立檔案和產生檔案漂移.

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

啟動器圖標是 `.python/generate_provisional_icon.py` 渲染的臨時圖案, 維護者圖稿到位前暫用; 可用 `py .python/generate_provisional_icon.py --check` 複核.

******

### 授權條款

******

插件程式碼採用 [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE). 內嵌的 AutoJs4 原始碼採用 MPL 2.0 並附帶 AutoJs4 倉庫的非商業性使用條款; Rhino, OpenCV, jackpal 終端庫與其他第三方元件保留各自的授權條款. 參閱 [第三方聲明](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md).

******

### 連結

******

- AutoJs6 專案: https://github.com/SuperMonster003/AutoJs6
- AutoJs4 專案: https://github.com/SuperMonster003/AutoJs4
- AutoJs4 文件: https://github.com/SuperMonster003/AutoJs4-Documentation
- AutoJs4 Runtime 協定 V1: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- AutoJs4 Runtime roadmap: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- 第三方聲明: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
