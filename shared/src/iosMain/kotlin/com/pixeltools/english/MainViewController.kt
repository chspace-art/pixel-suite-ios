package com.pixeltools.english

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

/**
 * iOS 外壳的入口。Swift 侧通过 `MainViewControllerKt.MainViewController()` 调用
 * （文件名 + `Kt` 后缀是 Kotlin/Native 对顶层函数的命名规则）。
 *
 * 整个 iOS 端只有这一个 Kotlin→Swift 的接缝：UI 全部由 Compose 画。
 */
fun MainViewController(): UIViewController = ComposeUIViewController { App() }
