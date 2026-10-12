// Registers the jackpal libtermexec AAR rebuilt by the AutoJs6 host from upstream sources with
// NDK 28.2 for 16 KB page alignment (`libs/jackpal-androidterm-libtermexec-1_0/`). It carries the
// same Java classes as AutoJs4's `autojs/libs/libtermexec-release.aar` but ships arm64-v8a,
// armeabi-v7a, x86 and x86_64 native libraries; provenance sits next to the AAR.
// zh-CN: 注册宿主 AutoJs6 以 NDK 28.2 从上游源码重编并满足 16 KB 页对齐的 jackpal libtermexec AAR
// (`libs/jackpal-androidterm-libtermexec-1_0/`). 其 Java 类与 AutoJs4 的 `autojs/libs/libtermexec-release.aar`
// 相同, 但原生库覆盖 arm64-v8a, armeabi-v7a, x86 与 x86_64; 来源信息与 AAR 放在一起.
plugins {
    id("org.autojs.build.local-arr-register-convention")
}

localAars {
    files += "${rootDir}/libs/jackpal-androidterm-libtermexec-1_0/libtermexec-release.aar"
}
