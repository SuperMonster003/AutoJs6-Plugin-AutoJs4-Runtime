// AutoJs4 `:autojs` module (`com.stardust.autojs`), the script engine itself, compiled from the
// Git submodule without modification. Dependencies mirror `autojs4/autojs/build.gradle`.
// zh-CN: AutoJs4 的 `:autojs` 模块 (`com.stardust.autojs`), 即脚本引擎本体, 直接编译 Git submodule 中的
// 源码, 不作修改; 依赖与 `autojs4/autojs/build.gradle` 一致.
plugins {
    id("org.autojs.build.versions")
    id("org.autojs.build.jvm-convention")
    id("com.android.library")
}

val AUTOJS4_COMPILE_SDK = 30
val autoJs4Module = rootProject.layout.projectDirectory.dir("autojs4/autojs")

android {
    namespace = "com.stardust.autojs"
    // AutoJs4 compiles against API 30; newer android.jar stubs drop Thread.stop(Throwable),
    // suspend(), resume(), destroy() and countStackFrames() used by MainThreadProxy.
    // zh-CN: AutoJs4 按 API 30 编译; 更新的 android.jar 存根移除了 MainThreadProxy 使用的
    // Thread.stop(Throwable), suspend(), resume(), destroy() 与 countStackFrames().
    compileSdk = AUTOJS4_COMPILE_SDK

    defaultConfig {
        minSdk = versions.sdkVersionMin
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile(autoJs4Module.file("src/main/AndroidManifest.xml"))
            java.setSrcDirs(listOf(autoJs4Module.dir("src/main/java")))
            kotlin.setSrcDirs(listOf(autoJs4Module.dir("src/main/java")))
            res.setSrcDirs(listOf(autoJs4Module.dir("src/main/res")))
            assets.setSrcDirs(listOf(autoJs4Module.dir("src/main/assets")))
        }
    }

    lint {
        targetSdk = versions.sdkVersionTarget
        abortOnError = false
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(files(autoJs4Module.file("libs/rhino-1.7.7.2.jar"), autoJs4Module.file("libs/dx.jar")))
    api(libs.autojs4.eventbus)
    api(libs.autojs4.zip4j)
    api(libs.autojs4.material.dialogs) {
        exclude(group = "com.android.support")
    }
    api(libs.autojs4.material)
    api(libs.autojs4.enhanced.floaty)
    api(libs.autojs4.roundedimageview)
    api(project(":engine:libs:opencv"))
    api(libs.autojs4.okhttp)
    api(libs.autojs4.jdeferred)
    api(libs.autojs4.gson)
    api(libs.autojs4.android.logging.log4j)
    api(libs.autojs4.log4j)
    api(project(":engine:libs:termexec"))
    api(project(":engine:libs:emulatorview"))
    api(project(":engine:libs:term"))
    api(project(":engine:common"))
    api(project(":engine:automator"))
}
