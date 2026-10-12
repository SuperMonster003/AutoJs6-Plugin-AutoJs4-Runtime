AutoJs4 Runtime は, AutoJs6 の動作基準となる Auto.js 4.1.1 Alpha2 のクローンである [AutoJs4](https://github.com/SuperMonster003/AutoJs4) のエンジンを内蔵しています. インストール後, AutoJs6 のファイルマネージャーにある "AutoJs4 で実行" が `.js` ファイル, `.auto` 記録ファイル, プロジェクトフォルダーをこのプラグイン自身のプロセスでオリジナルの Rhino 1.7.7.2 エンジンにより実行し, コンソール出力を AutoJs6 に返します. AutoJs4 本体をインストールしなくても, 同じ端末でスクリプトの挙動が AutoJs4 と AutoJs6 で異なるかを確認できます.

このリリースは AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`) を Rhino 1.7.7.2 と OpenCV 3.4.3 とともに内蔵し, すべてのスクリプトを隔離された `:autojs4_runtime` プロセスで実行します. 開始, コンソール行, 完了は host 契約を通じて AutoJs6 に返され, タスクは AutoJs6 のタスクリストから停止できます.

### クイックスタート

```javascript
console.log(context.getPackageName() + " / Rhino " + org.mozilla.javascript.Context.getCurrentContext().getImplementationVersion());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 のコンソールに `[AutoJs4]` 接頭辞付きの行が出力され, 最初の行にスクリプトを実行した AutoJs4 のパッケージ名と Rhino バージョンが表示されます.

### 互換性と範囲

- エンジン: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 クローン), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 のソースは Git submodule として取り込み, 変更せずにコンパイルします.
- プラットフォーム: Android 7.0 (API 24) 以降で 32 ビットアプリに対応した端末. 単一 ABI の APK は `armeabi-v7a, x86` を, `universal` APK は両方を含みます. 64 ビット専用端末には AutoJs4 と同様にインストールできません.
- スクリプト: `.js` ファイル, `.auto` root 記録ファイル, `project.json` を持つプロジェクトフォルダー (`main` と `features` を尊重). `"ui";` スクリプトはプラグインプロセス内で AutoJs4 自身のスクリプト Activity を開きます.
- 権限: ストレージへのアクセス (従来の外部ストレージ, `targetSdk 28`), 他のアプリの上に表示, AutoJs4 のユーザー補助サービスは AutoJs6 とは別にプラグインへ付与します. Root 機能は AutoJs4 と同様に端末の `su` を使用します.
- 同時実行: 最大 16 個の AutoJs4 スクリプトを同時に実行できます. AutoJs6 でタスクを停止すると AutoJs4 エンジンが停止し, プラグインプロセスのクラッシュは失敗したタスクとして報告されます.

ABI の選択, 権限, 正確な契約, このバージョンの範囲は [プロジェクト README](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime) を参照してください.
