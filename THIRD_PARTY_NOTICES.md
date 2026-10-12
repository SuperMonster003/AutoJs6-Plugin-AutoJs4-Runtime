# Third-Party Notices

AutoJs6-Plugin-AutoJs4-Runtime is licensed under the Mozilla Public License 2.0 (see `LICENSE`).
It embeds and redistributes the following third-party components. Licenses are the ones declared
by each upstream project; the plugin does not modify any of them.

## AutoJs4 (Git submodule `autojs4/`)

- Source: https://github.com/SuperMonster003/AutoJs4, commit `3fb08269198ebd49a880ab3ca2621383eaa10443`
- Modules compiled without modification: `autojs`, `common`, `automator`; prebuilt artifacts taken as-is:
  `autojs/libs/opencv-3.4.3.aar` (Java bindings), `autojs/libs/rhino-1.7.7.2.jar`, `autojs/libs/dx.jar`,
  `autojs/libs/libtermexec-release.aar`, `autojs/libs/emulatorview-release.aar`, `autojs/libs/term-debug.aar`,
  `libs/apk-builder-1.0.3/apk-builder-1.0.3.aar`, `libs/root-shell-1.6/root-shell-1.6.aar`,
  `app/src/main/jniLibs/armeabi-v7a/libopencv_java3.so`, `app/src/main/jniLibs/armeabi-v7a/libjackpal-*.so`,
  `autojs/src/main/assets/binary/root_automator`
- License: Mozilla Public License 2.0 with the additional non-commercial clause of the AutoJs4 repository
  ("非商业性使用 - 不得将此项目及衍生项目的源代码和二进制产品用于任何商业和盈利用途"). AutoJs4 is itself a clone
  of Auto.js 4.1.1 Alpha2 by hyb1996.

## Components bundled through AutoJs4

| Component | Version | License |
| --- | --- | --- |
| Mozilla Rhino | 1.7.7.2 | Mozilla Public License 2.0 |
| OpenCV (Java bindings and `libopencv_java3.so`) | 3.4.3 | BSD 3-Clause (OpenCV license) |
| Android dx (`dx.jar`) | as shipped by AutoJs4 | Apache License 2.0 |
| jackpal Android-Terminal-Emulator (`libtermexec`, `emulatorview`, `term`) | 1.0 | Apache License 2.0 |
| Auto.js ApkBuilder (`apk-builder-1.0.3.aar`) | 1.0.3 | Mozilla Public License 2.0 (Auto.js) |
| Auto.js root-shell (`root-shell-1.6.aar`) | 1.6 | Mozilla Public License 2.0 (Auto.js) |
| EnhancedFloaty (hyb1996) | 0.31 | Apache License 2.0 |
| settingscompat (hyb1996) | 1.1.5 | Apache License 2.0 |
| Material Dialogs (afollestad) | 0.9.2.3 | MIT License |
| Material Components for Android | 1.1.0-alpha01 | Apache License 2.0 |
| AndroidX (appcompat, annotation, core and transitive artifacts) | as resolved | Apache License 2.0 |
| greenrobot EventBus | 3.0.0 | Apache License 2.0 |
| zip4j | 1.3.2 | Apache License 2.0 |
| RoundedImageView (makeramen) | 2.3.0 | Apache License 2.0 |
| OkHttp | 3.10.0 | Apache License 2.0 |
| JDeferred (android-aar) | 1.2.6 | Apache License 2.0 |
| Gson | 2.8.2 | Apache License 2.0 |
| android-logging-log4j | 1.0.3 | Apache License 2.0 |
| Apache log4j | 1.2.17 | Apache License 2.0 |
| Glide | 4.2.0 | BSD, part MIT and Apache License 2.0 |

## AutoJs6 plugin API

- `libs/common-plugin-api.aar`: built from the AutoJs6 repository (`:plugin-api:common-plugin-api`),
  Mozilla Public License 2.0. Provenance is recorded in `libs/api-artifacts.lock.json`.
- `plugin-api/autojs4-runtime-api`: source copy of the AutoJs6 module of the same name, Mozilla Public License 2.0.

The Android Support Library references inside Material Dialogs 0.9.2.3 and EnhancedFloaty 0.31 are
rewritten to AndroidX by Jetifier at build time, exactly as in the AutoJs4 build.
