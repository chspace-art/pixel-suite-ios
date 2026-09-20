// pixel-english iOS 移植工程（独立 Gradle 构建）
//
// 刻意与 Pixel/ 下的安卓套件分开：那份是 com.android.application + Kotlin 2.0.20，
// 这份是 KMP + Kotlin 2.4.20，两者的 Kotlin 与 Gradle 版本要求不同，
// 合在一起会逼着安卓侧跟着升级——没必要为一个尚在验证的 iOS 工程动在售的代码。
//
// 仓库顺序：阿里云镜像在前（本机在国内，日常迭代走它），Central 兜底。
// CI 在美国跑，如嫌慢把 mavenCentral() 提到最前面即可，产物完全相同。

rootProject.name = "pixel-english-ios"

pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/public")
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        // google() 是必须的，别被"没有 Android 目标"骗了：JetBrains 的 compose 桌面产物
        // 依赖真正的 AndroidX 坐标（androidx.compose.runtime:runtime、androidx.lifecycle、
        // androidx.savedstate），这些只发布在 Google Maven 上。少这一行第一次解析就会
        // "Could not find androidx.compose.runtime:runtime" 一片红（2026-09-20 实测）。
        google()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        mavenCentral()
    }
}

include(":shared")
