******

### Release History

******

# v1.0.0

###### 2026/10/12

* `Hint` armeabi-v7a uses AutoJs4's original OpenCV 3.4.3 native library, 64-bit devices use the same release rebuilt with NDK 28.2; the x86 build has no OpenCV, like AutoJs4
* `Feature` Run `.js` files, `.auto` recordings and project folders from the AutoJs6 file manager with the embedded AutoJs4 4.4.0 engine (Rhino 1.7.7.2, OpenCV 3.4.3) through the `Run with AutoJs4` menu entry
* `Feature` Protected runtime service with the versioned `autojs4-runtime-api` AIDL contract: file-path requests, streamed console lines, start and completion events, cancellation and concurrent executions
* `Feature` Plugin settings page with accessibility service, display over other apps, storage access, battery optimization and root status, plus the `Show AutoJs4 console window` switch
* `Feature` Ship `arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86` and `universal` APKs, Wake activation, complete PluginInfo metadata, plugin-center instructions, README and changelog in 10 languages
* `Improvement` Build the arm64-v8a and x86_64 OpenCV 3.4.3 and jackpal terminal libraries with 16 KB page alignment and verify ELF alignment plus full JNI symbol coverage of the original library at build time
* `Dependency` Embed AutoJs4 4.4.0 as a Git submodule at commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)
