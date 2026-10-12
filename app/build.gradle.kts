import groovy.json.JsonSlurper
import java.util.Properties
import javax.inject.Inject

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

// Native inventory (roadmap Q10 = B): AutoJs4 ships OpenCV 3.4.3 only for armeabi-v7a, so the
// 32-bit library is taken byte-for-byte from the submodule, while arm64-v8a and x86_64 use the same
// OpenCV 3.4.3 rebuilt with NDK 28.2 for 16 KB pages (native/opencv, see tools/opencv). The jackpal
// terminal libraries for all four ABIs come from the AAR rebuilt by the AutoJs6 host. x86 has no
// OpenCV, exactly like AutoJs4.
// zh-CN: 原生库清单 (roadmap Q10 = B): AutoJs4 只为 armeabi-v7a 提供 OpenCV 3.4.3, 32 位库逐字节取自
// submodule; arm64-v8a 与 x86_64 使用以 NDK 28.2 重编并满足 16 KB 页对齐的同一 OpenCV 3.4.3
// (native/opencv, 见 tools/opencv). 四个 ABI 的 jackpal 终端库来自宿主 AutoJs6 重编的 AAR. x86 与
// AutoJs4 一样没有 OpenCV.
val supportedAbis = listOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86")
val openCvLibraryName = "libopencv_java3.so"
val openCvSources = mapOf(
    "armeabi-v7a" to rootProject.layout.projectDirectory.file("autojs4/app/src/main/jniLibs/armeabi-v7a/$openCvLibraryName"),
    "arm64-v8a" to rootProject.layout.projectDirectory.file("native/opencv/arm64-v8a/$openCvLibraryName"),
    "x86_64" to rootProject.layout.projectDirectory.file("native/opencv/x86_64/$openCvLibraryName"),
)
var isSignsValid = false

/** Stages the per-ABI OpenCV JNI library so that only the intended copy of each ABI reaches the APK. */
abstract class StageOpenCvJniLibsTask @Inject constructor(private val fileSystem: FileSystemOperations) : DefaultTask() {

    /** ABI names, index-aligned with [libraries]. */
    @get:Input
    abstract val abis: ListProperty<String>

    /** One `libopencv_java3.so` per ABI, index-aligned with [abis]. */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val libraries: ListProperty<RegularFile>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun stage() {
        val abiNames = abis.get()
        val sources = libraries.get()
        require(abiNames.size == sources.size) { "abis and libraries must be index-aligned" }
        val destination = outputDirectory.get().asFile
        fileSystem.delete { delete(destination) }
        abiNames.forEachIndexed { index, abi ->
            val source = sources[index].asFile
            check(source.isFile) { "OpenCV library for $abi is missing: $source (run tools/opencv/build_opencv_android.py)" }
            fileSystem.copy {
                from(source)
                into(destination.resolve(abi))
            }
        }
    }
}

val stageOpenCvJniLibs by tasks.registering(StageOpenCvJniLibsTask::class) {
    description = "Collects libopencv_java3.so per ABI (AutoJs4 original for armeabi-v7a, 16 KB rebuilds for 64-bit ABIs)."
    abis.set(openCvSources.keys.toList())
    libraries.set(openCvSources.values.toList())
    outputDirectory.set(layout.buildDirectory.dir("generated/opencv-jnilibs"))
}

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

androidComponents {
    onVariants { variant ->
        // AGP 9 refuses Provider-backed source directories in the legacy sourceSets API; the generated
        // jniLibs directory is wired through the Variant API so the staging task dependency is carried.
        variant.sources.jniLibs?.addGeneratedSourceDirectory(stageOpenCvJniLibs, StageOpenCvJniLibsTask::outputDirectory)
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
