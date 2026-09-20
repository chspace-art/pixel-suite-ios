package com.pixeltools.english.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * 圆角阶梯 —— 只有这五档。
 *
 * 之前散着 14 种不同圆角、55 处硬编码，13dp 和 14dp 并排出现在同一屏上，
 * 差别小到说不出、又大到看得见，是"廉价感"的主要来源之一。
 */
val PixelShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),    // 小标签、徽章
    small = RoundedCornerShape(12.dp),        // 芯片、次级按钮
    medium = RoundedCornerShape(16.dp),       // 卡片默认
    large = RoundedCornerShape(20.dp),        // 大卡片、底部浮层
    extraLarge = RoundedCornerShape(28.dp),   // 全宽主按钮
)

/** 胶囊：底部导航的选中态、分段控件用 */
val PillShape = RoundedCornerShape(percent = 50)

/**
 * 间距阶梯 —— 8dp 网格。
 *
 * 用 gap 排布，不靠给每个元素加 margin：margin 会互相折叠或叠加，
 * 最后没人说得清两个元素之间到底是 12 还是 16。
 */
object Gap {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val xxl = 24.dp
    val section = 32.dp
}

/** 页面水平留白。以前各页 16/12/4 混用，导致标题和正文不在同一条左轴上 */
object Inset {
    val page = 16.dp
    val card = 16.dp
    val bar = 12.dp
}

/**
 * 动效时长。
 *
 * 之前栏目切换用 550ms，慢到能看清中间态、像是卡了一下。
 * 220ms 是"感觉得到过渡、但注意力不会被它拽走"的位置。
 */
object Motion {
    const val QUICK = 140
    const val BASE = 220
    const val SLOW = 320
}

/** 最小可点区域 —— 手指不是鼠标，低于这个尺寸的控件点不准 */
val MinTouchTarget = 44.dp
