AutoJs4 Runtime embeds the engine of [AutoJs4](https://github.com/SuperMonster003/AutoJs4), the clone of Auto.js 4.1.1 Alpha2 that serves as the behavioral baseline of AutoJs6. After installing it, the "Run with AutoJs4" entry in the AutoJs6 file manager runs `.js` files, `.auto` recordings and project folders with the original Rhino 1.7.7.2 engine inside this plugin's own process, while the console output flows back into AutoJs6. This makes it possible to check on one device whether a script behaves differently on AutoJs4 and on AutoJs6 without installing AutoJs4 itself.

This release embeds AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`) with Rhino 1.7.7.2 and OpenCV 3.4.3 and runs every script in the isolated `:autojs4_runtime` process. Start, console lines and completion return to AutoJs6 through the host contract; the task can be stopped from the AutoJs6 task list.

### Quick Start

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

The AutoJs6 console prints the lines prefixed with `[AutoJs4]`; the first line shows the plugin package name that executed the script, which proves the AutoJs4 engine ran it.

### Compatibility and Scope

- Engine: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 clone), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. The AutoJs4 sources are included as a Git submodule and compiled without modification; the arm64-v8a and x86_64 OpenCV libraries are rebuilt from the same release with NDK 28.2.13676358 and verified to export every JNI symbol of the original library.
- Platform: Android 7.0 (API 24) or later. Single-ABI APKs cover `arm64-v8a, armeabi-v7a, x86_64, x86`; the `universal` APK contains all four. Like AutoJs4, the `x86` build has no OpenCV.
- Scripts: `.js` files, `.auto` root recordings and project folders with `project.json` (the `main` script and `features` are honoured). `"ui";` scripts open AutoJs4's own script activity inside the plugin process.
- Permissions: storage access (legacy external storage, `targetSdk 28`), display over other apps and the AutoJs4 accessibility service are granted to the plugin separately from AutoJs6. Root features use the `su` of the device like AutoJs4.
- Concurrency: up to 16 AutoJs4 scripts run at the same time; stopping a task in AutoJs6 stops the AutoJs4 engine, and a crashed plugin process is reported as a failed task.

See the [project README](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime) for ABI selection, permissions, the exact contract and the scope of this version.
