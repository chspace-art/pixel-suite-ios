package com.pixeltools.english

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pixeltools.english.ui.components.FilledIconAction
import com.pixeltools.english.ui.components.MetaText
import com.pixeltools.english.ui.components.PageHeader
import com.pixeltools.english.ui.components.PixelIcons
import com.pixeltools.english.ui.theme.Gap
import com.pixeltools.english.ui.theme.Inset
import com.pixeltools.english.ui.theme.PillShape
import com.pixeltools.english.ui.theme.PixelShapes
import com.pixeltools.english.ui.theme.PixelTheme
import com.pixeltools.english.ui.theme.ToneEssay
import com.pixeltools.english.ui.theme.ToneHome
import com.pixeltools.english.ui.theme.ToneStudy

/**
 * iOS 移植工程的根 composable。
 *
 * **目前是骨架，不是成品**：结构（页头 / 三张入口卡 / 悬浮胶囊导航）与配色
 * 都来自安卓版 MainActivity 的 [HomeScreen] 与 [PixelNavBar]，逐行照搬；
 * 但 Pack 数据、ProgressStore、阅读器这些还没进来——它们要么依赖安卓平台件，
 * 要么要等 PhotoKit/存储层就位。
 *
 * 这一版存在的意义：证明「墨玉」令牌层能原样在 Compose Multiplatform 上编译并渲染，
 * 同时给本机（Windows 桌面目标）一个能立刻打开看的迭代靶子。
 * 桌面目标与 iOS 目标共用这一份 commonMain，所以在这里看到什么，手机上就是什么。
 */
@Composable
fun App() {
    var tab by remember { mutableStateOf(0) }
    val tone = when (tab) {
        0 -> ToneHome
        1 -> ToneStudy
        else -> ToneEssay
    }
    PixelTheme(tone) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            Box(Modifier.weight(1f)) {
                when (tab) {
                    0 -> HomeScreen()
                    1 -> StubScreen(
                        title = "生词本",
                        subtitle = "词卡、复习、掌握度——待移植",
                    )
                    else -> StubScreen(
                        title = "我的",
                        subtitle = "进度、外观、导入——待移植",
                    )
                }
            }
            PixelNavBar(tab) { tab = it }
        }
    }
}

// ---------------------------------------------------------------- 底部导航

private enum class Tab(val label: String, val icon: ImageVector) {
    HOME("Home", PixelIcons.Home),
    WORDS("Words", PixelIcons.Bookmark),
    PROFILE("Profile", PixelIcons.Person),
}

/**
 * 悬浮胶囊导航条。照搬安卓版 PixelNavBar：整条是一张带阴影的圆角面板，
 * 选中项是实心强调色胶囊，槽位之间不再各自画底。
 *
 * `.navigationBarsPadding()` 是安卓的，这里删掉——iOS 由 SwiftUI 外壳
 * 处理安全区，安卓侧全量移植时再按平台补回来。
 */
@Composable
private fun PixelNavBar(current: Int, onSelect: (Int) -> Unit) {
    val accent = MaterialTheme.colorScheme.primary
    val idle = MaterialTheme.colorScheme.onSurfaceVariant
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp, top = 6.dp)
            .shadow(7.dp, PixelShapes.medium)
            .clip(PixelShapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Tab.entries.forEachIndexed { i, t ->
            val on = current == i
            Column(
                Modifier
                    .weight(1f)
                    .clip(PillShape)
                    .background(if (on) accent else Color.Transparent)
                    .clickable { onSelect(i) }
                    .padding(vertical = 7.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    t.icon, t.label,
                    Modifier.size(20.dp),
                    tint = if (on) MaterialTheme.colorScheme.onPrimary else idle,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    when (t) {
                        Tab.HOME -> L("首页", "Home")
                        Tab.WORDS -> L("词本", "Words")
                        Tab.PROFILE -> L("我的", "Profile")
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = if (on) MaterialTheme.colorScheme.onPrimary else idle,
                )
            }
        }
    }
}

// ---------------------------------------------------------------- 首页

/** 首页三张入口卡。照搬安卓版：0% 环与「继续学习」推卡都已退场，就三张卡。 */
@Composable
private fun HomeScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        PageHeader(
            title = "句透",
            actions = { FilledIconAction(PixelIcons.Plus, "Import", onClick = {}) },
        )
        Column(Modifier.padding(horizontal = Inset.page)) {
            HomeCard(
                PixelIcons.Film,
                L("电影", "Movies"),
                L("找一部片，读它的词表和难句", "Find a film, read its hard lines and words"),
            )
            HomeCard(
                PixelIcons.Pen,
                L("摘抄", "Essay"),
                L("线上的文章，和你贴进来的英文", "Online essays and what you paste in"),
            )
            HomeCard(
                PixelIcons.Notebook,
                L("日记", "Daily"),
                L("你记下的每一天", "Journals and daily notes"),
            )
            Spacer(Modifier.height(Gap.section))
        }
    }
}

/** 入口卡：图标 + 标题 + 一句话说明 + 右侧箭头。 */
@Composable
private fun HomeCard(icon: ImageVector, title: String, subtitle: String) {
    val accent = MaterialTheme.colorScheme.primary
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = Gap.xs)
            .clickable {},
    ) {
        Row(Modifier.padding(Inset.card), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(44.dp)
                    .clip(PixelShapes.medium)
                    .background(accent.copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center,
            ) { Icon(icon, null, Modifier.size(22.dp), tint = accent) }
            Spacer(Modifier.width(Gap.lg))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                MetaText(subtitle, Modifier.padding(top = Gap.xs))
            }
            Icon(
                PixelIcons.ChevronRight, null,
                Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.outline,
            )
        }
    }
}

/** 尚未移植的页签占位。留着是为了让导航条可点、能验证切栏目的配色过渡。 */
@Composable
private fun StubScreen(title: String, subtitle: String) {
    Column(Modifier.fillMaxSize()) {
        PageHeader(title = title, subtitle = subtitle)
        Spacer(Modifier.height(Gap.lg))
        MetaText(
            "这一页还没移植。当前版本只用来打通构建链路与验证令牌层。",
            Modifier.padding(horizontal = Inset.page),
        )
    }
}
