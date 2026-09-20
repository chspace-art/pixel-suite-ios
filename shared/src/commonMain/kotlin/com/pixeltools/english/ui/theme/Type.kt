package com.pixeltools.english.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 字号阶梯 —— 全部字号只从这里取。
 *
 * 之前散落着 25 个不同 fontSize、121 处硬编码，同一层级的标题在不同页面
 * 差 0.5–1sp，看久了就"说不清哪里乱"。这里收敛成 11 级。
 *
 * 中文行高比 Material 默认宽：汉字没有西文那种上下伸展的笔画，
 * 行高按西文给会挤成一坨。正文用 1.75 倍，标题 1.4 倍。
 */
private fun cjk(
    size: Float,
    lineHeight: Int,
    weight: FontWeight = FontWeight.Normal,
    letterSpacing: Double = 0.0,
) = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    letterSpacing = letterSpacing.sp,
)

val PixelTypography = Typography(
    // 背单词卡片上那个大写的单词。全 App 只有这一处用——
    // 一张卡一次只显示一个词，字大到能隔着半米看清，就是这一页唯一的任务
    displayLarge = cjk(38f, 48, FontWeight.Bold),

    // 页面主标题：「生词本」「我的」
    headlineLarge = cjk(26f, 36, FontWeight.Bold),

    // 次级标题：卡片主标题、阅读页文章名
    headlineMedium = cjk(21f, 30, FontWeight.Bold),

    // 分区标题
    headlineSmall = cjk(18f, 26, FontWeight.SemiBold),

    // 列表项标题、弹层单词
    titleLarge = cjk(16f, 24, FontWeight.SemiBold),

    // 设置项标题
    titleMedium = cjk(14f, 22, FontWeight.Medium),

    titleSmall = cjk(13f, 20, FontWeight.Medium),

    // 阅读正文 —— 行高最宽，长时间读英文靠它
    bodyLarge = cjk(16f, 28),

    bodyMedium = cjk(14f, 22),

    // 释义、例句、辅助说明
    bodySmall = cjk(12.5f, 20),

    // 按钮文字
    labelLarge = cjk(14f, 20, FontWeight.Medium),

    // 底部导航、分段控件
    labelMedium = cjk(12f, 16, FontWeight.Medium),

    // 页头小标签、时间戳 —— 全 App 最小的字，不再往下走
    labelSmall = cjk(11f, 15, FontWeight.Medium, letterSpacing = 0.02),
)

/**
 * 正文里"正在朗读的那个词"的字号。
 *
 * SpanStyle 不是 Composable，读不到 MaterialTheme，所以这里单独给一个常量。
 * 取值就是 [PixelTypography] 里 headlineSmall 的字号，别让它脱离阶梯。
 */
val WordSpotlightSize = 18.sp
