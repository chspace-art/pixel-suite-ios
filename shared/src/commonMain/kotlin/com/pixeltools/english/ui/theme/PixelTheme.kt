package com.pixeltools.english.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * 第三级文字色。Material 3 只有 onSurface / onSurfaceVariant 两级，
 * 而说明文字、时间戳这类还需要再弱一级，所以在主题里补一个。
 */
val LocalTextTertiary = staticCompositionLocalOf { Text.Tertiary }

/** 页面留白。全局统一，组件之间靠它对齐，不再各写各的 padding。 */
val LocalPageInset = staticCompositionLocalOf { Inset.page }

/**
 * App 主题。包一层就换掉整套配色。
 *
 * 切换栏目时只有会被眼睛看到的槽位做插值。原来 30 个槽位全量动画，
 * 一帧要算 30 次颜色插值，切栏目能看出中间态卡一下。
 *
 * [dark] = 夜林外壳（2026-09-12 用户选型）：深绿黑舞台 + 翠光强调。
 * 阅读页由调用处再包一层 `dark = false` 回到浅底护眼。
 */
@Composable
fun PixelTheme(tone: Tone, dark: Boolean = false, content: @Composable () -> Unit) {
    val scheme = if (dark) darkJadeScheme(tone.accent) else lightJadeScheme(tone.accent)
    val t = tween<Color>(Motion.BASE)
    val animated = scheme.copy(
        primary = animateColorAsState(scheme.primary, t).value,
        primaryContainer = animateColorAsState(scheme.primaryContainer, t).value,
        onPrimaryContainer = animateColorAsState(scheme.onPrimaryContainer, t).value,
        secondaryContainer = animateColorAsState(scheme.secondaryContainer, t).value,
        onSecondaryContainer = animateColorAsState(scheme.onSecondaryContainer, t).value,
        tertiaryContainer = animateColorAsState(scheme.tertiaryContainer, t).value,
        onTertiaryContainer = animateColorAsState(scheme.onTertiaryContainer, t).value,
        surfaceTint = animateColorAsState(scheme.surfaceTint, t).value,
    )
    MaterialTheme(colorScheme = animated, typography = PixelTypography, shapes = PixelShapes) {
        CompositionLocalProvider(
            LocalTextTertiary provides if (dark) TextDark.Tertiary else Text.Tertiary,
            LocalContentColor provides animated.onSurface,
            content = content,
        )
    }
}
