import groovy.json.JsonSlurper
import java.util.Properties

plugins {
    id("io.github.supermonster003.autojs6-native-alignment")
    id("org.autojs.build.utils")
    id("org.autojs.build.versions")
    id("org.autojs.build.signs")
    id("org.autojs.build.jvm-convention")
    id("com.android.application")
}

val globalApplicationId = "io.github.supermonster003.autojs6.plugin.autojs4.runtime"
val pluginPermission = "org.autojs.permission.PLUGIN"
val androidTestPermissionName = providers.gradleProperty("androidTestPermissionName")
    .orElse(pluginPermission)
    .map(String::trim)
val requiredApiLevel = providers.gradleProperty("requiredApiLevel")
    .map(String::trim)

// AutoJs4 ships OpenCV 3.4.3 only for armeabi-v7a and the terminal libraries for armeabi-v7a and
// x86. The plugin mirrors that inventory instead of adding 64-bit builds AutoJs4 never had.
// zh-CN: AutoJs4 只为 armeabi-v7a 提供 OpenCV 3.4.3, 终端库覆盖 armeabi-v7a 与 x86. 插件镜像这一
// 清单, 不额外提供 AutoJs4 从未有过的 64 位构建.
val supportedAbis = listOf("armeabi-v7a", "x86")
var isSignsValid = false

val autoJs4Dir = rootProject.layout.projectDirectory.dir("autojs4")
val autoJs4Versions = (JsonSlurper().parse(autoJs4Dir.file("project-versions.json").asFile) as Map<*, *>)
val autoJs4VersionName = autoJs4Versions["appVersionName"].toString()
val autoJs4VersionCode = autoJs4Versions["appVersionCode"].toString().toInt()
val autoJs4Commit: String = runCatching {
    providers.exec {
        commandLine("git", "-C", autoJs4Dir.asFile.path, "rev-parse", "HEAD")
    }.standardOutput.asText.get().trim()
}.getOrNull()?.takeIf { it.matches(Regex("[0-9a-f]{40}")) } ?: "unknown"

android {
    namespace = globalApplicationId
    compileSdk = versions.sdkVersionCompile

    defaultConfig {
        applicationId = globalApplicationId
        minSdk = versions.sdkVersionMin
        targetSdk = versions.sdkVersionTarget
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["androidTestPermissionName"] = androidTestPermissionName.get().also { permissionName ->
            require(permissionName.matches(Regex("[A-Za-z][A-Za-z0-9_]*(\\.[A-Za-z][A-Za-z0-9_]*)+"))) {
                "androidTestPermissionName must be a fully-qualified Android permission name, but was '$permissionName'"
            }
        }
        requiredApiLevel.orNull?.let { apiLevel ->
            require(apiLevel.toIntOrNull() != null) {
                "requiredApiLevel must be an integer, but was '$apiLevel'"
            }
            testInstrumentationRunnerArguments["requiredApiLevel"] = apiLevel
        }
        versionCode = versions.appVersionCode
        versionName = versions.appVersionName
        multiDexEnabled = true

        resValue("string", "app_name", "AutoJs4 Runtime")
        resValue("string", "plugin_author", "SuperMonster003")
        resValue("string", "plugin_version_date", utils.getDateString("MMM d, yyyy", "GMT+08:00"))

        buildConfigField("String", "AUTOJS4_VERSION_NAME", "\"$autoJs4VersionName\"")
        buildConfigField("int", "AUTOJS4_VERSION_CODE", "$autoJs4VersionCode")
        buildConfigField("String", "AUTOJS4_COMMIT", "\"$autoJs4Commit\"")

        ndk {
            abiFilters += supportedAbis
        }
    }

    sourceSets {
        getByName("main") {
            // libopencv_java3.so and the jackpal terminal libraries exactly as AutoJs4 packages them.
            // zh-CN: 与 AutoJs4 打包方式完全相同的 libopencv_java3.so 与 jackpal 终端库.
            jniLibs.srcDirs(autoJs4Dir.dir("app/src/main/jniLibs"))
        }
    }

    signingConfigs {
        val props = Properties().also { properties ->
            File("${project.rootDir}/sign.properties").takeIf(File::exists)?.let { file ->
                file.inputStream().use(properties::load)
                isSignsValid = properties.isNotEmpty()
            }
        }
        if (isSignsValid) {
            create("release") {
                storeFile = props["storeFile"]?.let { file(it as String) }
                keyPassword = props["keyPassword"] as String
                keyAlias = props["keyAlias"] as String
                storePassword = props["storePassword"] as String
            }
        }
    }

    buildTypes {
        val releaseSigning = takeIf { isSignsValid }?.let { signingConfigs.getByName("release") }
        // AutoJs4 never minifies its engine (Rhino, reflection and JS bridges); the plugin keeps
        // the same packaging so behaviour stays comparable.
        // zh-CN: AutoJs4 从不混淆其引擎 (Rhino, 反射与 JS 桥), 插件保持相同打包方式以便行为可对照.
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            releaseSigning?.let { signingConfig = it }
        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            releaseSigning?.let { signingConfig = it }
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include(*supportedAbis.toTypedArray())
            isUniversalApk = true
        }
    }

    lint {
        abortOnError = true
        checkDependencies = false
    }

    buildFeatures {
        aidl = true
        resValues = true
        buildConfig = true
    }
}

dependencies {
    implementation(files("$rootDir/libs/common-plugin-api.aar"))
    implementation(projects.pluginApi.autojs4RuntimeApi)
    implementation(projects.engine.autojs)

    // Same image loader the AutoJs4 shells install for UI scripts.
    // zh-CN: 与 AutoJs4 壳应用为 UI 脚本安装的图片加载器相同.
    implementation(libs.autojs4.glide) {
        exclude(group = "com.android.support")
    }

    testImplementation(libs.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.runner)
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.register<Sync>("appendDigestToReleasedFiles") {
    description = "Collects signed ABI and universal APKs with CRC32 in each name."
    val ext = utils.FILE_EXTENSION_APK
    val src = layout.buildDirectory.dir("outputs/apk/release")
    val dst = layout.projectDirectory.dir("releases")
    val expected = (supportedAbis + "universal").mapTo(mutableSetOf()) { abi ->
        "app-$abi-release.$ext"
    }
    dependsOn("assembleRelease")
    doFirst {
        check(isSignsValid) {
            "Release signing configuration is missing or incomplete; refusing to collect unsigned APKs"
        }
        val actual = src.get().asFile.listFiles { file -> file.isFile && file.extension == ext }
            .orEmpty().mapTo(mutableSetOf()) { it.name }
        check(actual == expected) { "Expected exactly ${expected.sorted()}, but found ${actual.sorted()}" }
    }
    from(src)
    into(dst)
    include("*.$ext")
    rename { name ->
        val abi = name.replace(Regex("^app-(.+?)-release(\\.$ext)$"), "$1")
        val prefix = "${rootProject.name}-v${versions.appVersionName}-$abi"
        "$prefix-${utils.digestCRC32(src.get().file(name).asFile)}.$ext"
    }
}
