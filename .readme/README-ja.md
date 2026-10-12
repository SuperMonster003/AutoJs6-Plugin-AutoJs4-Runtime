<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>内蔵の Auto.js 4.1.1 Alpha2 エンジンでスクリプトを実行し, AutoJs6 の結果と比較する</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### 言語

******

現在の README.md は次の言語に対応しています:

- [English [en]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-en.md)
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ar.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-es.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-fr.md)
- 日本語 [ja] # 現在
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ru.md)
- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### 概要

******

AutoJs4 Runtime は, AutoJs6 の動作基準となる Auto.js 4.1.1 Alpha2 のクローンである [AutoJs4](https://github.com/SuperMonster003/AutoJs4) のエンジンを内蔵しています. インストール後, AutoJs6 のファイルマネージャーにある "AutoJs4 で実行" が `.js` ファイル, `.auto` 記録ファイル, プロジェクトフォルダーをこのプラグイン自身のプロセスでオリジナルの Rhino 1.7.7.2 エンジンにより実行し, コンソール出力を AutoJs6 に返します. AutoJs4 本体をインストールしなくても, 同じ端末でスクリプトの挙動が AutoJs4 と AutoJs6 で異なるかを確認できます.

******

### インストールと使用

******

1. Android 7.0 (API 24) 以降で AutoJs6 build 5334 (6.8.0) 以降を使用します.
2. 端末の ABI に合う release APK (多くの現行端末は `arm64-v8a`, 32 ビット端末は `armeabi-v7a`, エミュレーターは `x86_64` または `x86`) か, サイズの大きい `universal` APK をインストールします.
3. AutoJs6 のプラグインセンターを開いて AutoJs4 Runtime を有効にします. Android が新規インストールしたパッケージを停止状態のままにしている場合は host の `アクティベート` 操作を使用します.
4. プラグインセンターからプラグイン設定を開き, スクリプトに必要なストレージへのアクセス, 他のアプリの上に表示, ユーザー補助サービスを許可します. これらの権限はプラグイン自身のもので, AutoJs6 の権限とは独立しています.
5. AutoJs6 のファイルマネージャーで `.js` ファイル, `.auto` ファイル, またはプロジェクトフォルダーのメニューを開き, `AutoJs4 で実行` を選びます. タスクは AutoJs6 のタスクリストに表示され, そこで停止できます.

> プラグインは AutoJs4 を変更しません. 同じ Rhino 1.7.7.2, armeabi-v7a では AutoJs4 オリジナルの OpenCV 3.4.3 ライブラリ, 64 ビット端末では同じ OpenCV リリースを再ビルドしたライブラリ, `targetSdk 28`, 同じエンジンモジュールです. したがって両エンジンの間で観察される差は AutoJs4 と AutoJs6 の差であり, AutoJs6 の issue トラッカーに報告できます.

******

### クイックスタート

******

このファイルを AutoJs6 のワークスペースに保存し, `AutoJs4 で実行` から実行します:

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 のコンソールに `[AutoJs4]` 接頭辞付きの行が出力され, 最初の行にスクリプトを実行したプラグインのパッケージ名が表示されます. これで AutoJs4 エンジンが実行したことを確認できます.

******

### 互換性と範囲

******

- エンジン: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 クローン), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 のソースは Git submodule として取り込み変更せずにコンパイルします. arm64-v8a と x86_64 の OpenCV ライブラリは同じリリースから NDK 28.2.13676358 で再ビルドし, オリジナルライブラリの全 JNI シンボルを公開していることを検証しています.
- プラットフォーム: Android 7.0 (API 24) 以降. 単一 ABI の APK は `arm64-v8a, armeabi-v7a, x86_64, x86` を, `universal` APK は 4 つすべてを含みます. AutoJs4 と同様に `x86` ビルドには OpenCV がありません.
- スクリプト: `.js` ファイル, `.auto` root 記録ファイル, `project.json` を持つプロジェクトフォルダー (`main` と `features` を尊重). `"ui";` スクリプトはプラグインプロセス内で AutoJs4 自身のスクリプト Activity を開きます.
- 権限: ストレージへのアクセス (従来の外部ストレージ, `targetSdk 28`), 他のアプリの上に表示, AutoJs4 のユーザー補助サービスは AutoJs6 とは別にプラグインへ付与します. Root 機能は AutoJs4 と同様に端末の `su` を使用します.
- 同時実行: 最大 16 個の AutoJs4 スクリプトを同時に実行できます. AutoJs6 でタスクを停止すると AutoJs4 エンジンが停止し, プラグインプロセスのクラッシュは失敗したタスクとして報告されます.

******

### 権限と整合性

******

- Wake Activity と 2 つのエクスポート済みサービスは `org.autojs.permission.PLUGIN` を要求します. スクリプトはプラグイン UID で `:autojs4_runtime` プロセス内で実行され, AutoJs6 のプロセスには入りません.
- スクリプトは絶対パスで指定されます. プラグインはユーザーが付与したストレージ権限の範囲でのみファイルを読み書きし, 絶対パスでない要求や読み取れないファイルの要求を拒否します.
- コンソール出力は最大 64 KiB のチャンクで有界な oneway コールバックを通じて AutoJs6 にストリーミングされ, 終了イベントは状態, エラーメッセージ, スタックトレースの要約のみを運びます.
- AutoJs4 自身の外部エントリ (実行インテント, ショートカット, Tasker) はエクスポートされません. 保護された Binder サービスを通じてスクリプトを開始できるのは AutoJs6 host だけです.

******

### プラグインインターフェイス

******

AutoJs6 host と plugin developer 向けの安定 identifier と limit:

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

`AutoJs4RuntimeService` は action `org.autojs.plugin.autojs4.RUNTIME` と category `autojs4-runtime` で発見されます. `runScript` は要求 Bundle (スクリプトパス, 作業ディレクトリ, 任意のプロジェクトフォルダー, 引数, 遅延, 間隔, ループ回数) を検証し, AutoJs4 の実行を開始して受理 Bundle を直ちに返します. その後 `started`, `log`, `finished` イベントがコールバック経由で届きます. `cancelScript` は実行中の処理を停止するか, まだディスパッチされていない実行に対するキャンセルを記憶します. 完全な契約は AutoJs6 リポジトリ ([protocol V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)) に記載されています.

16 KB ページサイズ: arm64-v8a と x86_64 のネイティブライブラリ (NDK 28.2.13676358 で再ビルドした OpenCV 3.4.3 と AutoJs6 host が再ビルドした jackpal 端末ライブラリ) は 16 KB の最大ページサイズでリンクされ, 共有のアライメントゲートがビルド時にすべての ELF PT_LOAD セグメントを検証します. 16 KB ページ端末での実行はまだ行っていません.

******

### リリース履歴

******

#### v1.0.1

_2026/10/12_

- `改善` スクリプトがユーザー補助サービスを必要とし, プラグインのサービスが有効になっていない場合, システムのユーザー補助設定を開く理由をトーストで表示

#### v1.0.0

_2026/10/12_

- `ヒント` armeabi-v7a では AutoJs4 オリジナルの OpenCV 3.4.3 ネイティブライブラリを, 64 ビット端末では NDK 28.2 で再ビルドした同じリリースを使用します. x86 ビルドには AutoJs4 と同様に OpenCV がありません
- `機能` AutoJs6 のファイルマネージャーの `AutoJs4 で実行` メニューから, 内蔵の AutoJs4 4.4.0 エンジン (Rhino 1.7.7.2, OpenCV 3.4.3) で `.js` ファイル, `.auto` 記録ファイル, プロジェクトフォルダーを実行
- `機能` バージョン管理された `autojs4-runtime-api` AIDL 契約による保護されたランタイムサービス: ファイルパス要求, コンソール行のストリーミング, 開始と完了イベント, キャンセル, 同時実行
- `機能` ユーザー補助サービス, 他のアプリの上に表示, ストレージへのアクセス, 電池の最適化, Root 状態を示すプラグイン設定ページと `AutoJs4 コンソールウィンドウを表示` スイッチ
- `機能` `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86`, `universal` APK の提供, Wake アクティベーション, 完全な PluginInfo メタデータ, 10 言語のプラグインセンター説明, README, changelog
- `改善` arm64-v8a と x86_64 の OpenCV 3.4.3 および jackpal 端末ライブラリを 16 KB ページアライメントでビルドし, ビルド時に ELF アライメントとオリジナルライブラリの JNI シンボルの完全な網羅を検証
- `依存関係` AutoJs4 4.4.0 を Git submodule として commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443) で内蔵

##### その他のリリース履歴

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-ja.md)

******

### ビルドと検証

******

ビルドには JDK 17 以降, Python 3, Android SDK 37 が必要です (AutoJs4 エンジンモジュールは API 30 でコンパイルされ, Android SDK が必要に応じてダウンロードします). `autojs4/` を取得するため `--recurse-submodules` でクローンしてください. 標準チェックは生成文書, JVM テスト, debug APK, instrumentation パッケージ, Android Lint を検証します.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### ローカライズと文書生成

******

JSON と Markdown テンプレートを編集した後に `py .python/generate_markdown.py` を実行します. 生成された README, changelog, プラグイン説明ファイルは手動で編集しないでください. `--check` モードは書き込みなしで言語構造, バージョン整合, ローカライズリソース, 孤立ファイル, 生成ファイルのずれを検証します.

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

アイコンは管理者提供の [PNG 原稿](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/tree/master/.python/icons) (ライトテーマ用とダークテーマ用の 2 枚) と元の構図を保持しています. Icon Studio で調整し, `python .python/generate_icon_studio.py --check` でレシピと生成リソースの一致を確認してください.

******

### ライセンス

******

プラグインのコードは [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE) で提供されます. 内蔵する AutoJs4 のソースは MPL 2.0 に AutoJs4 リポジトリの非商用条項を加えたライセンスです. Rhino, OpenCV, jackpal 端末ライブラリ, その他のサードパーティコンポーネントはそれぞれのライセンスを保持します. [サードパーティ通知](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md) を参照してください.

******

### リンク

******

- AutoJs6 プロジェクト: https://github.com/SuperMonster003/AutoJs6
- AutoJs4 プロジェクト: https://github.com/SuperMonster003/AutoJs4
- AutoJs4 ドキュメント: https://github.com/SuperMonster003/AutoJs4-Documentation
- AutoJs4 Runtime protocol V1: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- AutoJs4 Runtime roadmap: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- サードパーティ通知: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
