// 版本组合（2026-09-20 查证）：CMP 1.12.0 stable + Kotlin 2.4.20 + Compose 编译器插件同版本。
// CMP 插件源码里只校验编译器插件版本 == Kotlin 版本、且 Kotlin ≥ 2.2.0，不设上限。
// Gradle 9.7.0 是 Kotlin 2.4.20 官方兼容表内的最高版本。
// 不引 AGP：这份工程没有 Android 目标。
plugins {
    kotlin("multiplatform") version "2.4.20" apply false
    id("org.jetbrains.compose") version "1.12.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.20" apply false
}
