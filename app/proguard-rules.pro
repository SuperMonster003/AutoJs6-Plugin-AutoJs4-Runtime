# Minification is disabled for every build type (see app/build.gradle.kts). These rules only
# document the classes that must survive if shrinking is ever enabled.
# zh-CN: 全部构建类型均未开启混淆 (见 app/build.gradle.kts), 以下规则仅记录开启收缩时必须保留的类.
-keep class org.autojs.plugin.autojs4.runtime.api.** { *; }
-keep class org.autojs.plugin.common.api.** { *; }
-keep class io.github.supermonster003.autojs6.plugin.autojs4.runtime.** extends android.app.Service { *; }
-keep class io.github.supermonster003.autojs6.plugin.autojs4.runtime.** extends android.app.Activity { *; }
-keep class io.github.supermonster003.autojs6.plugin.autojs4.runtime.** extends android.app.Application { *; }
-keep class com.stardust.** { *; }
-keep class org.mozilla.javascript.** { *; }
-keep class org.opencv.** { *; }
-dontwarn kotlinx.parcelize.Parcelize
