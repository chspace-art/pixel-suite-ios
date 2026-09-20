package com.pixeltools.english

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

/**
 * 桌面靶机入口 —— 这条命令行是整个 iOS 移植里最快的迭代回路。
 *
 * Windows 上编不了 iOS，也拿不到任何真机热重载；但共享 UI 层是同一份 Kotlin，
 * 所以在本机跑这个窗口 ≈ 看 iOS 渲染结果。改动 → 窗口重开只要几秒，
 * 对比 CI 出 IPA 的 15–35 分钟，日常调 UI 全在这条路上做。
 *
 * 窗口开成手机比例（390×780 ≈ iPhone 逻辑分辨率），免得按桌面宽度调出手机上不成立的版式。
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "句透 · 桌面靶机 (390×780)",
        state = rememberWindowState(size = DpSize(390.dp, 780.dp)),
    ) {
        App()
    }
}
