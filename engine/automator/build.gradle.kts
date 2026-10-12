// AutoJs4 `:automator` module (`com.stardust.automator`), compiled from the Git submodule without modification.
// zh-CN: AutoJs4 的 `:automator` 模块 (`com.stardust.automator`), 直接编译 Git submodule 中的源码, 不作修改.
plugins {
    id("org.autojs.build.versions")
    id("org.autojs.build.jvm-convention")
    id("com.android.library")
}

val AUTOJS4_COMPILE_SDK = 30
val autoJs4Module = rootProject.layout.projectDirectory.dir("autojs4/automator")

android {
    namespace = "com.stardust.automator"
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
    api(libs.autojs4.appcompat)
    api(project(":engine:common"))
}
