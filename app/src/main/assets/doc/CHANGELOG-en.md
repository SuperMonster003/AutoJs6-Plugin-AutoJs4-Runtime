******

### Release History

******

# v1.1.2

###### 2026/11/27

* `Improvement` The plugin's synopsis and instructions now say what it is for: comparing results with AutoJs6 when migrating old Auto.js 4.x scripts; most users, who only write new scripts in AutoJs6, do not need to install it

# v1.1.1

###### 2026/11/20

* `Improvement` The release history page now expands and collapses by version: a collapsed version shows its date and the number of entries per category, an expanded one names each category once; all versions can be expanded or collapsed at once, the newest one opens expanded and the subtitle shows the plugin name
* `Improvement` Rewrote the instructions shown in Plugin Center in five parts: getting started, main features, using it in scripts, requirements and FAQ
* `Dependency` Added Jetpack Compose BOM 2026.09.00 (release history page)
* `Dependency` Upgraded AndroidX Core to 1.16.0 and AndroidX Lifecycle to 2.9.4 (needed by Jetpack Compose; AppCompat stays at AutoJs4's 1.0.2)
* `Dependency` Upgraded shared build plugins version 1.9.0 -> 1.9.7

# v1.1.0

###### 2026/11/08

* `Fix` The application icon was difficult to distinguish on dark backgrounds in some launchers and system app lists
* `Improvement` In the file menu of AutoJs6 6.8.1 and later, "Run with AutoJs4" and "Run with AutoJs4 and compare" are now provided by the plugin, under the top-level menu "AutoJs4" (unchanged in older AutoJs6 versions)
* `Improvement` Settings and release history pages follow the language, dark mode and theme color of AutoJs6, falling back to the system appearance when host settings are unavailable

# v1.0.1

###### 2026/10/12

* `Improvement` When a script needs the accessibility service while the plugin's service is not enabled, a toast explains why the system accessibility settings open

# v1.0.0

###### 2026/10/12

* `Hint` armeabi-v7a uses AutoJs4's original OpenCV 3.4.3 native library, 64-bit devices use the same release rebuilt with NDK 28.2; the x86 build has no OpenCV, like AutoJs4
* `Feature` Run `.js` files, `.auto` recordings and project folders from the AutoJs6 file manager with the embedded AutoJs4 4.4.0 engine (Rhino 1.7.7.2, OpenCV 3.4.3) through the `Run with AutoJs4` menu entry
* `Feature` Protected runtime service with the versioned `autojs4-runtime-api` AIDL contract: file-path requests, streamed console lines, start and completion events, cancellation and concurrent executions
* `Feature` Plugin settings page with accessibility service, display over other apps, storage access, battery optimization and root status, plus the `Show AutoJs4 console window` switch
* `Feature` Ship `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` and `universal` APKs, Wake activation, complete PluginInfo metadata, plugin-center instructions, README and changelog in 10 languages
* `Improvement` Build the arm64-v8a and x86_64 OpenCV 3.4.3 and jackpal terminal libraries with 16 KB page alignment and verify ELF alignment plus full JNI symbol coverage of the original library at build time
* `Dependency` Embed AutoJs4 4.4.0 as a Git submodule at commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
