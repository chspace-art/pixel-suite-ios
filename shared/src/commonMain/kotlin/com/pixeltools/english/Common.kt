package com.pixeltools.english

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// 对应安卓侧 Common.kt 里的 AppLang / L。那两个在 Common.kt 里，
// 而 Common.kt 混着播报、文件选择这些平台件，所以这里单独放一份最小实现。
// 全量移植时这份会被安卓侧的 Common.kt 顶掉。

enum class Lang { ZH, EN }

object AppLang {
    var current by mutableStateOf(Lang.ZH)
}

/** 界面文案双语取值。所有 UI 字面量都必须经这里，禁止再裸写英文句子。 */
@Composable
internal fun L(zh: String, en: String): String = when (AppLang.current) {
    Lang.ZH -> zh
    Lang.EN -> en
}
