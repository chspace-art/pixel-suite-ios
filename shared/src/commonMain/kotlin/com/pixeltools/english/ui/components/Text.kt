package com.pixeltools.english.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.pixeltools.english.ui.theme.Gap
import com.pixeltools.english.ui.theme.LocalTextTertiary
import com.pixeltools.english.ui.theme.PixelShapes

/**
 * 界面上的三档文字。取代原来那个到处都在用的万能小标签组件。
 *
 * 原来只有一个小标签组件，干着三件完全不同的活：
 * 分区标题（"RECENT"）、字段名（"英文正文"）、元信息（"海明威 · 21 段"）。
 * 它把所有传入的字符串都 .uppercase()，于是中文说明旁边冒出全大写英文，
 * 长说明被拉成一行挤成两截，词性标签和段落标题长得一模一样——
 * 用户根本分不出哪个是标题、哪个是注释。
 *
 * 现在按语义拆成三个，各自只干一件事：
 *
 *   SectionTitle  区块标题，中文短语，一屏最多三四个
 *   MetaText      元信息、说明、时间戳，可以多行
 *   Tag           极小号徽标，词性、状态
 *
 * 都不做大小写转换——中文没有大小写，英文缩写该大写就自己写大写。
 */

/**
 * 区块标题。用在小标题上，比如"最近""今日""学习进度"。
 *
 * 一屏里最多三四个。它下面的内容才是主角，标题只需要让人知道
 * "从这里开始是另一类东西"。
 */
@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null,
) {
    if (trailing == null) {
        Text(
            text,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier,
        )
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(Gap.sm))
            trailing()
        }
    }
}

/**
 * 元信息：一行说明、一个统计、一个时间戳。
 *
 * 这是全 App 用得最多的一档：卡片副标题、列表项下面的灰字、
 * 设置项的一句话解释。行高留得比字号宽，因为中文说明经常要折两行。
 */
@Composable
fun MetaText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalTextTertiary.current,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

/**
 * 徽标。词性（n. / adj.）、状态（已掌握）这类一两个词的极短标签。
 *
 * 有底色，和正文拉开。不用它承载句子——超过四个字就该换 [MetaText]。
 */
@Composable
fun Tag(text: String, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.primary) {
    Text(
        text,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        maxLines = 1,
        modifier = modifier
            .clip(PixelShapes.extraSmall)
            .then(Modifier.background(color.copy(alpha = 0.10f)))
            .padding(horizontal = Gap.sm, vertical = Gap.xs),
    )
}

/** 表单字段名。输入框上方那行，比如"英文正文"。 */
@Composable
fun FieldLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.padding(bottom = Gap.xs),
    )
}
