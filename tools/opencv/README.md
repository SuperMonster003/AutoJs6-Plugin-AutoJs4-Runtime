# OpenCV 3.4.3 JNI library for 64-bit ABIs

AutoJs4 ships `libopencv_java3.so` only for `armeabi-v7a` (`autojs4/app/src/main/jniLibs/armeabi-v7a`,
SHA-256 `d05136b5f6a915da2343920ffcaecb13bd6b6e9d38f24ada78a6d11010a66638`). The plugin keeps that file
byte-for-byte for 32-bit devices and rebuilds the same OpenCV release for `arm64-v8a` and `x86_64`
so that 64-bit-only devices, including 16 KB page devices, can install the plugin (roadmap Q10 = B).

`native/opencv/<abi>/libopencv_java3.so` are the rebuilt libraries; `native/opencv/opencv.lock.json`
records the archive SHA-256, NDK, CMake options and, per ABI, the library SHA-256, the minimum
PT_LOAD alignment and the number of exported `Java_*` symbols.

## Guarantees checked by the script

- Source: the official `opencv-3.4.3.zip` from GitHub, SHA-256 verified before extraction.
- Compiler: NDK 28.2.13676358 (`android.toolchain.cmake`, `android-24`, `c++_static`), explicit
  `-Wl,-z,max-page-size=16384 -Wl,-z,common-page-size=16384`; every PT_LOAD segment of the 64-bit
  libraries is aligned to 16 KB or more.
- Compatibility: every `Java_*` symbol exported by AutoJs4's original 32-bit library (2039 symbols
  across core, imgproc, imgcodecs, features2d, calib3d, video, photo, ml, dnn, objdetect, videoio and
  android) must be exported by the rebuilt library. The unchanged `opencv-3.4.3.aar` Java classes
  therefore bind to the same JNI entry points.
- Configuration mirrors the official OpenCV Android SDK of the 3.4 line: static modules folded into
  one JNI library (`BUILD_FAT_JAVA_LIB`), no OpenCL / IPP / TBB / CUDA, no examples or tests. clang 16+
  diagnostics that became errors (`implicit-function-declaration` and friends in 2018 third-party C
  sources) are downgraded to warnings; no source file is patched.
- Provenance stamp: `-DOPENCV_VCSVERSION=3.4.3` so that `Core.getBuildInformation()` reports
  `Version control: 3.4.3` (the archive has no Git metadata; without the option CMake would describe
  the enclosing plugin checkout instead).

## Rebuild

Requirements: Python 3, Android SDK CMake 3.22.1 (with Ninja), NDK 28.2.13676358, Apache Ant (only
probed by OpenCV's CMake; the Java jar target is never built) and a JDK for `ant -version`.

```powershell
py tools/opencv/build_opencv_android.py `
  --ndk E:/.android/sdk/ndk/28.2.13676358 `
  --cmake-dir E:/.android/sdk/cmake/3.22.1/bin `
  --ant C:/tools/apache-ant-1.10.15/bin/ant.bat `
  --abis arm64-v8a,x86_64
py tools/opencv/build_opencv_android.py --check --ndk E:/.android/sdk/ndk/28.2.13676358
```

The build directory is `build/opencv/` (ignored). A clean build of one ABI takes about 6 to 10 minutes
with 10 to 12 parallel jobs; re-running after an option change relinks in about a minute per ABI.
`--check` verifies the committed libraries against the lock without building (SHA-256 only when
`--ndk` is omitted, as in CI; with `--ndk` it also re-reads the PT_LOAD alignment).

Device evidence (2026-10-12, XQ-DQ72, Android 13): the same `images.findColor` / `findImage` /
`matchTemplate` script run through "使用 AutoJs4 运行" returned identical results with the plugin
installed as `arm64-v8a` (rebuilt library, `os.arch` `aarch64`, clang 19.0.1 in the build information)
and as `armeabi-v7a` (AutoJs4's original library, GCC 4.8 / NDK r10e).

zh-CN: AutoJs4 只为 armeabi-v7a 提供 `libopencv_java3.so`; 插件保留该原版文件, 并用同一 OpenCV 3.4.3
源码以 NDK 28.2 为 arm64-v8a 与 x86_64 重编 (16 KB 页对齐), 构建脚本校验归档 SHA-256, ELF 对齐以及原库
2039 个 JNI 符号在新库中全部存在, 结果记录在 `native/opencv/opencv.lock.json`.
