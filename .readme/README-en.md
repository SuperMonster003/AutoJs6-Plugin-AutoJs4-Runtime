<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap-night/ic_launcher.png" />
      <img src="https://raw.githubusercontent.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/master/app/src/main/res/mipmap/ic_launcher.png" alt="AutoJs4 Runtime" border="0" width="128" />
    </picture>
  </p>

  <p>Runs scripts with the embedded Auto.js 4.1.1 Alpha2 engine to compare results with AutoJs6</p>

  <p>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/releases"><img alt="GitHub release" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?label=Release"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/issues"><img alt="GitHub issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=A24232&label=Issues"/></a>
    <a href="https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime?color=534BAE&label=License"/></a>
  </p>
</div>

******

### Languages

******

The current README.md supports the following languages:

- English [en] # current
- [العربية [ar]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ar.md)
- [Español [es]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-es.md)
- [Français [fr]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-fr.md)
- [日本語 [ja]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ja.md)
- [한국어 [ko]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ko.md)
- [Русский [ru]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-ru.md)
- [简体中文 [zh-Hans]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hans.md)
- [繁體中文 香港 [zh-Hant-HK]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-HK.md)
- [繁體中文 台灣 [zh-Hant-TW]](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/.readme/README-zh-Hant-TW.md)

******

### Introduction

******

AutoJs4 Runtime embeds the engine of [AutoJs4](https://github.com/SuperMonster003/AutoJs4), the clone of Auto.js 4.1.1 Alpha2 that serves as the behavioral baseline of AutoJs6. After installing it, the "Run with AutoJs4" entry in the AutoJs6 file manager runs `.js` files, `.auto` recordings and project folders with the original Rhino 1.7.7.2 engine inside this plugin's own process, while the console output flows back into AutoJs6. This makes it possible to check on one device whether a script behaves differently on AutoJs4 and on AutoJs6 without installing AutoJs4 itself.

******

### Install and Use

******

1. Use AutoJs6 build 5334 (6.8.0) or later on Android 7.0 (API 24) or later. The device must be able to run 32-bit apps, exactly like AutoJs4.
2. Install the release APK matching the device ABI (`armeabi-v7a` for ARM devices, `x86` for emulators), or the `universal` APK.
3. Open the AutoJs6 plugin center and enable AutoJs4 Runtime. If Android left the newly installed package stopped, use the host `Activate` action.
4. Open the plugin settings from the plugin center and grant storage access, display over other apps and the accessibility service as the scripts need them; these permissions belong to the plugin, not to AutoJs6.
5. In the AutoJs6 file manager, open the menu of a `.js` file, a `.auto` file or a project folder and choose `Run with AutoJs4`. The task appears in the AutoJs6 task list and can be stopped there.

> The plugin keeps AutoJs4 unchanged: the same Rhino 1.7.7.2, the same OpenCV 3.4.3 native library, `targetSdk 28` and the same 32-bit ABI coverage. Differences you observe between the two engines are therefore differences between AutoJs4 and AutoJs6, and can be reported to the AutoJs6 issue tracker.

******

### Quick Start

******

Save this file in the AutoJs6 workspace and run it through `Run with AutoJs4`:

```javascript
console.log(context.getPackageName() + " / Rhino " + org.mozilla.javascript.Context.getCurrentContext().getImplementationVersion());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

The AutoJs6 console prints the lines prefixed with `[AutoJs4]`; the first line shows the AutoJs4 package name and Rhino version that executed the script.

******

### Compatibility and Scope

******

- Engine: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 clone), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. The AutoJs4 sources are included as a Git submodule and compiled without modification.
- Platform: Android 7.0 (API 24) or later with 32-bit app support. Single-ABI APKs cover `armeabi-v7a, x86`; the `universal` APK contains both. 64-bit-only devices cannot install the plugin, just as they cannot install AutoJs4.
- Scripts: `.js` files, `.auto` root recordings and project folders with `project.json` (the `main` script and `features` are honoured). `"ui";` scripts open AutoJs4's own script activity inside the plugin process.
- Permissions: storage access (legacy external storage, `targetSdk 28`), display over other apps and the AutoJs4 accessibility service are granted to the plugin separately from AutoJs6. Root features use the `su` of the device like AutoJs4.
- Concurrency: up to 16 AutoJs4 scripts run at the same time; stopping a task in AutoJs6 stops the AutoJs4 engine, and a crashed plugin process is reported as a failed task.

******

### Permissions and Integrity

******

- The Wake activity and both exported services require `org.autojs.permission.PLUGIN`; scripts run under the plugin UID in the `:autojs4_runtime` process, never inside AutoJs6.
- Scripts are addressed by absolute path. The plugin reads and writes files only with the storage permission the user granted to it, and rejects requests whose paths are not absolute or whose files cannot be read.
- Console output is streamed to AutoJs6 through a bounded oneway callback in chunks of at most 64 KiB; the terminal event carries status, error message and stack trace summaries only.
- AutoJs4's own external entry points (run intents, shortcuts, Tasker) are not exported. Only the AutoJs6 host can start scripts through the protected Binder service.

******

### Plugin Interface

******

Stable identifiers and limits for AutoJs6 host and plugin developers:

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

`AutoJs4RuntimeService` is discovered by action `org.autojs.plugin.autojs4.RUNTIME` and category `autojs4-runtime`. `runScript` validates the request Bundle (script path, working directory, optional project folder, arguments, delay, interval and loop count), starts the AutoJs4 execution and returns an acceptance Bundle immediately; `started`, `log` and `finished` events then arrive through the callback. `cancelScript` stops a running execution or remembers a cancellation for an execution that has not been dispatched yet. The complete contract is documented in the AutoJs6 repository ([protocol V1](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md)).

16 KB page size: the plugin ships only the 32-bit native libraries that AutoJs4 ships. There is no 64-bit native code, so the alignment gate has nothing to check and the APK cannot be installed on 64-bit-only devices. Adding an `arm64-v8a` build with a rebuilt OpenCV 3.4.x is tracked as an open question in the [roadmap](https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md).

******

### Release History

******

#### v1.0.0

_2026/10/12_

- `Hint` The first release mirrors AutoJs4 exactly, including its 32-bit-only native libraries, so the plugin cannot be installed on 64-bit-only devices
- `Feature` Run `.js` files, `.auto` recordings and project folders from the AutoJs6 file manager with the embedded AutoJs4 4.4.0 engine (Rhino 1.7.7.2, OpenCV 3.4.3) through the `Run with AutoJs4` menu entry
- `Feature` Protected runtime service with the versioned `autojs4-runtime-api` AIDL contract: file-path requests, streamed console lines, start and completion events, cancellation and concurrent executions
- `Feature` Plugin settings page with accessibility service, display over other apps, storage access, battery optimization and root status, plus the `Show AutoJs4 console window` switch
- `Feature` Ship `armeabi-v7a`, `x86` and `universal` APKs, Wake activation, complete PluginInfo metadata, plugin-center instructions, README and changelog in 10 languages
- `Dependency` Embed AutoJs4 4.4.0 as a Git submodule at commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)

##### For more release history

- [CHANGELOG.md](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/app/src/main/assets/doc/CHANGELOG-en.md)

******

### Build and Verification

******

Building requires JDK 17 or later, Python 3 and Android SDK 37 (the AutoJs4 engine modules compile against API 30, which the Android SDK downloads on demand). Clone with `--recurse-submodules` so that `autojs4/` is populated. The standard checks validate generated documentation, JVM tests, debug APKs, the instrumentation package and Android Lint.

```powershell
git clone --recurse-submodules https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime.git
py .python\generate_markdown.py --check
py .python\generate_provisional_icon.py --check
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug
```

******

### Localization and Docs Generation

******

Edit the JSON and Markdown templates, then run `py .python/generate_markdown.py`. Generated README, changelog, and plugin-instruction files must not be edited by hand. The `--check` mode validates language shape, version alignment, localized resources, orphan files, and generated-file drift without writing.

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

The launcher icon is a provisional glyph rendered by `.python/generate_provisional_icon.py` until maintainer artwork is available; verify it with `py .python/generate_provisional_icon.py --check`.

******

### License

******

Plugin code is licensed under the [Mozilla Public License 2.0](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/LICENSE). The embedded AutoJs4 sources are licensed under MPL 2.0 with the non-commercial clause of the AutoJs4 repository; Rhino, OpenCV, the jackpal terminal libraries and the other third-party components keep their own licenses. See [Third-Party Notices](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md).

******

### Links

******

- AutoJs6 project: https://github.com/SuperMonster003/AutoJs6
- AutoJs4 project: https://github.com/SuperMonster003/AutoJs4
- AutoJs4 documentation: https://github.com/SuperMonster003/AutoJs4-Documentation
- AutoJs4 Runtime protocol V1: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-protocol-v1.md
- AutoJs4 Runtime roadmap: https://github.com/SuperMonster003/AutoJs6/blob/master/docs/dev/autojs4-runtime-plugin-roadmap.md
- Third-party notices: https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime/blob/master/THIRD_PARTY_NOTICES.md
