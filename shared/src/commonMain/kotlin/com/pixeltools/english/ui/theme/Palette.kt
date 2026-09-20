package com.pixeltools.english.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

/**
 * 墨玉 · 色板。
 *
 * 一套染翠的中性阶，配一个墨绿强调色。所有数值都在这里，别的地方不许再出现字面量颜色。
 *
 * 设计意图（2026-09-12 用户定调）：整个 App 要有"墨玉"的体感——翡翠/墨绿/森林/树叶同族绿，
 * **不引入其他重颜色**；近白/纯白的底是败笔，中性阶全部带绿，一眼是玉不是纸。
 * 5 套栏目 Tone 是同族绿（翡翠/深孔雀绿/深墨绿/冷苔绿/竹青），钢蓝已退场。
 *
 * 2026-09-12 第八轮验收（用户选「夜林」方案）：深底这份换成夜林取值——墨绿黑舞台、
 * 面板带绿、翠光强调；深底用于外壳（首页/影视/词本/我的），阅读页仍用浅底护眼。
 *
 * 两种底色各有一份取值，按同一个键名排列，改的时候成对改：
 *
 *           浅底(默认)             深底(夜林)
 *   页面      #E3EEDB              #10201A
 *   卡片      #EDF5E4              #173026
 *   正文      #14231A              #EAF2E2
 *   强调      #2F6B52              #4EBE8B（翠光）
 */

// ---------------------------------------------------------------- 中性色阶

/** 浅底中性阶。名字按"离页面底多远"排，不按明暗排——深底那份是反过来的。 */
internal object Paper {
    val Base = Color(0xFFE3EEDB)   // 淡翠玉底：页面背景，绿意必须一眼可辨（用户两次点名去白）
    val Card = Color(0xFFEDF5E4)   // 卡片/弹层：比页面底亮半档，但仍是翠纸不是白纸
    val Surface2 = Color(0xFFE9F2E0)
    val Surface3 = Color(0xFFE3EEDB)
    val Surface4 = Color(0xFFD8E6CB)
    val Dim = Color(0xFFD1E1C4)
    val Line = Color(0xFFBCD3AE)
    val LineSoft = Color(0xFFD8E6CB)
}

/** 深底中性阶（夜林）。与 [Paper] 逐项对应。 */
internal object Ink {
    val Base = Color(0xFF10201A)   // 夜林底：页面背景
    val Card = Color(0xFF173026)   // 面板：剧场幕片的底
    val Surface2 = Color(0xFF1B3529)
    val Surface3 = Color(0xFF1F3B2E)
    val Surface4 = Color(0xFF24463A)
    val Dim = Color(0xFF0B1812)
    val Line = Color(0xFF2E5947)
    val LineSoft = Color(0xFF24463A)
}

// ---------------------------------------------------------------- 文字

/**
 * 文字色阶。括号里是对页面底的实测对比度。
 *
 * 三级就够：正文、说明、弱化。再多一级就会出现两个几乎看不出区别的灰，
 * 用的人只能瞎猜该用哪个。
 */
internal object Text {
    val Primary = Color(0xFF14231A)    // 墨绿黑，约 14.8:1 on 淡翠底
    val Secondary = Color(0xFF4C6153)  //  5.1:1
    val Tertiary = Color(0xFF5B7161)   //  4.6:1
    val OnAccent = Color(0xFFFFFFFF)   //  6.3:1 on 翡翠（各 Tone 上为 6.1~9.0:1）
}

internal object TextDark {
    val Primary = Color(0xFFEAF2E2)   // 苔雾白 on 夜林底 ~13:1
    val Secondary = Color(0xFF9DB8A6) //  ~6.4:1
    val Tertiary = Color(0xFF7FA08C)  //  ~4.6:1
    val OnAccent = Color(0xFF0C1310)
}

// ---------------------------------------------------------------- 语义色

internal object Semantic {
    val Accent = Color(0xFF2F6B52)
    val Error = Color(0xFFB3261E)
    val ErrorContainer = Color(0xFFFBE9E7)
    val OnErrorContainer = Color(0xFF601410)
}

/**
 * 栏目的强调色。
 *
 * 浅底下要压深——原来那套是为深底调的，直接搬到近白底上全部低于 4.5:1，小字会糊。
 * 行尾是对 [Paper.Base] 的实测对比度，5 套全部 ≥4.5:1。
 */
@Immutable
data class Tone(val accent: Color) {
    /** 选中态、标签底的淡色。 */
    val soft: Color get() = accent.copy(alpha = 0.10f).compositeOver(Paper.Card)

    /** 分隔线，比 [soft] 略实。 */
    val hairline: Color get() = accent.copy(alpha = 0.22f).compositeOver(Paper.Base)

    /**
     * 深底（夜林）下的对应取值：色相不动、提到翠光一档——深底的深 accent 直接放
     * 上夜林底会发闷。ToneMovies 用提案定稿的翠光 #4EBE8B，其余同族绿等亮。
     */
    val accentDark: Color
        get() = when (accent) {
            ToneHome.accent -> Color(0xFF5FC493)
            ToneMovies.accent -> Color(0xFF4EBE8B)
            ToneStudy.accent -> Color(0xFF57BE8F)
            ToneEssay.accent -> Color(0xFF83C79E)
            else -> Color(0xFFA3CE85) // ToneDiary 竹青的夜林对应值
        }
}

val ToneHome = Tone(Color(0xFF2F6B52))    // 翡翠    5.5:1 —— 首页的摘抄与随笔
val ToneMovies = Tone(Color(0xFF245E5A))  // 深孔雀绿 6.4:1 —— 影视台词
val ToneStudy = Tone(Color(0xFF1E5240))   // 深墨绿   7.9:1 —— 背单词、复习
val ToneEssay = Tone(Color(0xFF406B4F))   // 冷苔绿   5.3:1 —— 随笔、文章
val ToneDiary = Tone(Color(0xFF526B3A))   // 竹青     4.9:1 —— 日记（钢蓝已退场：全 App 不引其他重色）

// ---------------------------------------------------------------- 配色方案

/**
 * 把强调色展开成一套完整的 Material 3 配色。
 *
 * 必须显式覆盖每一个槽位。M3 只填了少数几个的话，剩下的会落回基线配色——
 * 底部导航的选中指示器读的是 secondaryContainer，漏掉它就会冒出淡紫 #E8DEF8。
 * 这类漏洞在浅底主题上尤其明显，因为基线紫本来就是浅色的。
 */
internal fun jadeScheme(
    accent: Color,
    dark: Boolean,
    surface: Color,
    card: Color,
    s2: Color,
    s3: Color,
    s4: Color,
    dim: Color,
    line: Color,
    lineSoft: Color,
    onSurface: Color,
    onSurfaceVariant: Color,
    onSurfaceTertiary: Color,
    onAccent: Color,
): androidx.compose.material3.ColorScheme {
    val base = if (dark) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = accent,
        onPrimary = onAccent,
        primaryContainer = accent.copy(alpha = 0.12f).compositeOver(card),
        onPrimaryContainer = accent,
        inversePrimary = accent,

        secondary = onSurfaceVariant,
        onSecondary = onAccent,
        secondaryContainer = accent.copy(alpha = 0.10f).compositeOver(card),
        onSecondaryContainer = accent,

        tertiary = accent,
        onTertiary = onAccent,
        tertiaryContainer = accent.copy(alpha = 0.10f).compositeOver(card),
        onTertiaryContainer = accent,

        background = surface,
        onBackground = onSurface,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = s3,
        onSurfaceVariant = onSurfaceVariant,

        surfaceTint = accent,
        surfaceContainerLowest = card,
        surfaceContainerLow = card,
        surfaceContainer = s2,
        surfaceContainerHigh = s3,
        surfaceContainerHighest = s4,
        surfaceBright = card,
        surfaceDim = dim,

        outline = line,
        outlineVariant = lineSoft,

        error = if (dark) Color(0xFFE8A0A0) else Semantic.Error,
        onError = onAccent,
        errorContainer = if (dark) Color(0xFF43201F) else Semantic.ErrorContainer,
        onErrorContainer = if (dark) Color(0xFFF7CFCD) else Semantic.OnErrorContainer,

        inverseSurface = onSurface,
        inverseOnSurface = surface,
        scrim = Color.Black,
    )
}

/** 浅底方案。App 的默认外观。 */
internal fun lightJadeScheme(accent: Color) = jadeScheme(
    accent = accent, dark = false,
    surface = Paper.Base, card = Paper.Card,
    s2 = Paper.Surface2, s3 = Paper.Surface3, s4 = Paper.Surface4, dim = Paper.Dim,
    line = Paper.Line, lineSoft = Paper.LineSoft,
    onSurface = Text.Primary, onSurfaceVariant = Text.Secondary, onSurfaceTertiary = Text.Tertiary,
    onAccent = Text.OnAccent,
)

/** 深底方案（夜林）。外壳用；阅读页保持浅底护眼。 */
internal fun darkJadeScheme(accent: Color) = jadeScheme(
    accent = accent, dark = true,
    surface = Ink.Base, card = Ink.Card,
    s2 = Ink.Surface2, s3 = Ink.Surface3, s4 = Ink.Surface4, dim = Ink.Dim,
    line = Ink.Line, lineSoft = Ink.LineSoft,
    onSurface = TextDark.Primary, onSurfaceVariant = TextDark.Secondary, onSurfaceTertiary = TextDark.Tertiary,
    onAccent = TextDark.OnAccent,
)
