package com.pixeltools.english.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pixeltools.english.ui.theme.Gap
import com.pixeltools.english.ui.theme.Inset
import com.pixeltools.english.ui.theme.MinTouchTarget
import com.pixeltools.english.ui.theme.PixelShapes

/**
 * 全 App 唯一的页头。
 *
 * 换掉之前八处各写各的版本——首页、我的文章、影视详情、影视检索、
 * 阅读页、生词本、我的、隐私说明，每一处的字号、间距、返回键写法都不一样：
 * 有的是拿文本符号充当返回键，有的根本没有返回键，标题在 18/19/20/22sp 之间乱跳。
 *
 * 结构固定为：返回键（可选）· 标题 / 副标题 · 右侧动作（可选）。
 * 副标题走 [MetaText]，所以"12 篇""3 天前"这类统计不会突然变成全大写。
 */
@Composable
fun PageHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Inset.page, vertical = Gap.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBack != null) {
            IconAction(PixelIcons.ArrowLeft, "Back", onBack)
            Spacer(Modifier.width(Gap.xs))
        }
        Column(Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (!subtitle.isNullOrBlank()) {
                MetaText(subtitle, Modifier.padding(top = Gap.xs), maxLines = 2)
            }
        }
        if (actions != null) {
            Spacer(Modifier.width(Gap.sm))
            actions()
        }
    }
}

/**
 * 页头右侧的图标按钮。命中区至少 44dp，比图标本身大一圈——
 * 原来那些 32dp 的小方块按起来经常打偏。
 */
@Composable
fun IconAction(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier
            .size(MinTouchTarget)
            .clip(PixelShapes.medium)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription, Modifier.size(21.dp), tint = tint)
    }
}

/** 实心方块按钮：首页和"我的文章"右上角那个 +。 */
@Composable
fun FilledIconAction(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .size(MinTouchTarget)
            .clip(PixelShapes.medium)
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            icon, contentDescription,
            Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
