// Registers the prebuilt AutoJs4 AAR `autojs4/libs/apk-builder-1.0.3/apk-builder-1.0.3.aar` as a Gradle artifact.
// zh-CN: 把 AutoJs4 预编译的 AAR `autojs4/libs/apk-builder-1.0.3/apk-builder-1.0.3.aar` 注册为 Gradle 构件.
plugins {
    id("org.autojs.build.local-arr-register-convention")
}

localAars {
    files += "${rootDir}/autojs4/libs/apk-builder-1.0.3/apk-builder-1.0.3.aar"
}
