import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    // 桌面目标不是要给用户用的，是**开发靶机**：Windows 上唯一能秒级看到 UI 的通道。
    // 共享 UI 层改了先在这儿看，看对了再走 CI 出 iOS 包。
    jvm("desktop")

    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            // isStatic = true 是这次关签名 CI 方案的关键，不是随手写的：
            // KGP 里 embedAndSignAppleFrameworkForXcode 这个任务对静态 framework
            // 直接 task.isEnabled = false（registerEmbedTask 传的是 !isStatic），
            // 于是它既不拷贝也不调 codesign —— 关签名构建下就不会有 codesign 失败。
            // 用动态 framework 则会被塞进 Payload/App.app/Frameworks/，
            // 多一层要被 SideStore 重签的嵌套 Mach-O，是装机失败的高发点。
            isStatic = true
            binaryOption("bundleId", "com.pixeltools.english.shared")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.pixeltools.english.DesktopMainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Msi)
            packageName = "PixelEnglish"
            packageVersion = "0.1.0"
        }
    }
}
