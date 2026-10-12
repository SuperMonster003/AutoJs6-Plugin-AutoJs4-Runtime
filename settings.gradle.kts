enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "autojs6-plugin-autojs4-runtime"

pluginManagement {
    providers.gradleProperty("autojs.buildPlugins.includeBuild").orNull?.let { includeBuild(it) }
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    plugins {
        id("io.github.supermonster003.autojs6-platform-versions") version "1.9.0"
        id("io.github.supermonster003.autojs6-native-alignment") version "1.9.0"
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    }
}

plugins {
    id("io.github.supermonster003.autojs6-platform-versions")
    id("org.gradle.toolchains.foojay-resolver-convention")
}

includeBuild("build-logic")

include(":app")
include(":plugin-api:autojs4-runtime-api")

// AutoJs4 engine modules. Their sources live in the Git submodule `autojs4/`; the build scripts
// live here so the submodule stays byte-identical to the upstream AutoJs4 repository.
// zh-CN: AutoJs4 引擎模块, 源码位于 Git submodule `autojs4/`, 构建脚本放在本仓库, 保持 submodule 与
// 上游 AutoJs4 仓库逐字节一致.
include(":engine:common")
include(":engine:automator")
include(":engine:autojs")
include(":engine:libs:apk-builder")
include(":engine:libs:root-shell")
include(":engine:libs:opencv")
include(":engine:libs:termexec")
include(":engine:libs:emulatorview")
include(":engine:libs:term")
