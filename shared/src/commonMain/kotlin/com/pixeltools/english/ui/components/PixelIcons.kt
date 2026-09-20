package com.pixeltools.english.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * App 的图标集。
 *
 * 全部是 24 网格、1.8 描边的线性图标，形态统一：同一套圆角端点、同一套粗细。
 * 之前界面上用的是一套彩色 emoji 图标——
 * 那是彩色位图字体，跟着系统皮肤变样，各家 ROM 长得都不一样，
 * 而且没法染色、基线对不齐、大小靠猜。换成矢量之后这些都消失了。
 *
 * 只定义界面真正用到的这些，不引 material-icons-extended——
 * 那个库有几千个图标，会给每次构建多加十几秒，用到的不到二十个。
 */
object PixelIcons {

    /** 描边路径。默认 1.8 宽，圆头圆角。 */
    private fun stroke(
        name: String,
        width: Float = 1.8f,
        build: PathBuilder.() -> Unit,
    ): ImageVector = ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = width,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            pathBuilder = build,
        )
    }.build()

    /** 实心路径。播放、暂停这种要一眼看清的用实心。 */
    private fun solid(name: String, build: PathBuilder.() -> Unit): ImageVector =
        ImageVector.Builder(
            name = name,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(fill = SolidColor(Color.Black), pathBuilder = build)
        }.build()

    // ------------------------------------------------------------ 底部导航

    val Home = stroke("home") {
        moveTo(3f, 9.5f); lineTo(12f, 2.5f); lineTo(21f, 9.5f)
        lineTo(21f, 20f); lineTo(3f, 20f); close()
        moveTo(9f, 20f); lineTo(9f, 12.5f); lineTo(15f, 12.5f); lineTo(15f, 20f)
    }

    val Bookmark = stroke("bookmark") {
        moveTo(18.5f, 21f); lineTo(12f, 16.2f); lineTo(5.5f, 21f); lineTo(5.5f, 5.5f)
        quadTo(5.5f, 3.5f, 7.5f, 3.5f); lineTo(16.5f, 3.5f)
        quadTo(18.5f, 3.5f, 18.5f, 5.5f); close()
    }

    val Person = stroke("person") {
        moveTo(12f, 3f)
        arcTo(4.2f, 4.2f, 0f, true, true, 12f, 11.4f)
        arcTo(4.2f, 4.2f, 0f, true, true, 12f, 3f)
        moveTo(20f, 21f); lineTo(20f, 19f)
        quadTo(20f, 15.2f, 16f, 15.2f); lineTo(8f, 15.2f)
        quadTo(4f, 15.2f, 4f, 19f); lineTo(4f, 21f)
    }

    // ------------------------------------------------------------ 栏目

    /** 我的文章 */
    val Book = stroke("book") {
        moveTo(4f, 19.4f); quadTo(4f, 17f, 6.5f, 17f); lineTo(20f, 17f)
        moveTo(6.5f, 2.6f); lineTo(20f, 2.6f); lineTo(20f, 21.4f); lineTo(6.5f, 21.4f)
        quadTo(4f, 21.4f, 4f, 19.4f); lineTo(4f, 4.6f)
        quadTo(4f, 2.6f, 6.5f, 2.6f); close()
    }

    /** 影视台词 */
    val Film = stroke("film") {
        moveTo(5f, 4f); lineTo(19f, 4f); quadTo(21f, 4f, 21f, 6f)
        lineTo(21f, 18f); quadTo(21f, 20f, 19f, 20f); lineTo(5f, 20f)
        quadTo(3f, 20f, 3f, 18f); lineTo(3f, 6f); quadTo(3f, 4f, 5f, 4f); close()
        moveTo(7.2f, 4f); lineTo(7.2f, 20f)
        moveTo(16.8f, 4f); lineTo(16.8f, 20f)
        moveTo(3f, 9f); lineTo(7.2f, 9f); moveTo(3f, 15f); lineTo(7.2f, 15f)
        moveTo(16.8f, 9f); lineTo(21f, 9f); moveTo(16.8f, 15f); lineTo(21f, 15f)
    }

    val Search = stroke("search") {
        moveTo(11f, 3.8f)
        arcTo(7.2f, 7.2f, 0f, true, true, 11f, 18.2f)
        arcTo(7.2f, 7.2f, 0f, true, true, 11f, 3.8f)
        moveTo(16.3f, 16.3f); lineTo(21f, 21f)
    }

    /** 日记 */
    val Notebook = stroke("notebook") {
        moveTo(6f, 3f); lineTo(18f, 3f); quadTo(20f, 3f, 20f, 5f)
        lineTo(20f, 19f); quadTo(20f, 21f, 18f, 21f); lineTo(6f, 21f)
        quadTo(4f, 21f, 4f, 19f); lineTo(4f, 5f); quadTo(4f, 3f, 6f, 3f); close()
        moveTo(8.4f, 3f); lineTo(8.4f, 21f)
        moveTo(11.6f, 8f); lineTo(16.6f, 8f)
        moveTo(11.6f, 12f); lineTo(16.6f, 12f)
    }

    /** 随笔 */
    val Pen = stroke("pen") {
        moveTo(4f, 20f); lineTo(4.7f, 16.2f); lineTo(16.4f, 4.5f)
        lineTo(19.5f, 7.6f); lineTo(7.8f, 19.3f); close()
        moveTo(14f, 6.9f); lineTo(17.1f, 10f)
    }

    /** 电影笔记 */
    val Clapper = stroke("clapper") {
        moveTo(3f, 9.4f); lineTo(21f, 9.4f); lineTo(21f, 19.6f); lineTo(3f, 19.6f); close()
        moveTo(3f, 9.4f); lineTo(4.6f, 4.4f); lineTo(21f, 4.4f); lineTo(21f, 9.4f)
        moveTo(7.4f, 4.4f); lineTo(9.8f, 9.4f)
        moveTo(13f, 4.4f); lineTo(15.4f, 9.4f)
    }

    // ------------------------------------------------------------ 播放

    val Play = solid("play") {
        moveTo(7f, 4.2f); lineTo(20f, 12f); lineTo(7f, 19.8f); close()
    }

    val Pause = solid("pause") {
        moveTo(6.6f, 4.4f); lineTo(10.1f, 4.4f); lineTo(10.1f, 19.6f); lineTo(6.6f, 19.6f); close()
        moveTo(13.9f, 4.4f); lineTo(17.4f, 4.4f); lineTo(17.4f, 19.6f); lineTo(13.9f, 19.6f); close()
    }

    /** 循环。两条横杠带箭头，比转圈的箭头在小尺寸下清楚得多。 */
    val Repeat = stroke("repeat") {
        moveTo(17f, 2.4f); lineTo(20.8f, 6.2f); lineTo(17f, 10f)
        moveTo(3.2f, 11.6f); lineTo(3.2f, 8.2f); quadTo(3.2f, 6.2f, 5.2f, 6.2f); lineTo(20.8f, 6.2f)
        moveTo(7f, 21.6f); lineTo(3.2f, 17.8f); lineTo(7f, 14f)
        moveTo(20.8f, 12.4f); lineTo(20.8f, 15.8f); quadTo(20.8f, 17.8f, 18.8f, 17.8f); lineTo(3.2f, 17.8f)
    }

    /** 朗读 */
    val Volume = stroke("volume") {
        moveTo(11f, 4.8f); lineTo(6.2f, 9f); lineTo(2.6f, 9f); lineTo(2.6f, 15f)
        lineTo(6.2f, 15f); lineTo(11f, 19.2f); close()
        moveTo(15.2f, 8.6f); quadTo(18.4f, 12f, 15.2f, 15.4f)
        moveTo(18.4f, 5.2f); quadTo(23f, 12f, 18.4f, 18.8f)
    }

    // ------------------------------------------------------------ 方向

    val ChevronRight = stroke("chevron_right", width = 2f) {
        moveTo(9.5f, 5f); lineTo(16.5f, 12f); lineTo(9.5f, 19f)
    }

    val ChevronUp = stroke("chevron_up", width = 2f) {
        moveTo(5.5f, 15f); lineTo(12f, 8.5f); lineTo(18.5f, 15f)
    }

    val ChevronDown = stroke("chevron_down", width = 2f) {
        moveTo(5.5f, 9f); lineTo(12f, 15.5f); lineTo(18.5f, 9f)
    }

    val ArrowLeft = stroke("arrow_left", width = 2f) {
        moveTo(15f, 4.5f); lineTo(7.5f, 12f); lineTo(15f, 19.5f)
    }

    // ------------------------------------------------------------ 动作与状态

    val Close = stroke("close", width = 1.9f) {
        moveTo(6f, 6f); lineTo(18f, 18f)
        moveTo(18f, 6f); lineTo(6f, 18f)
    }

    val Check = stroke("check", width = 2.1f) {
        moveTo(5f, 12.8f); lineTo(9.8f, 17.6f); lineTo(19f, 6.4f)
    }

    val Plus = stroke("plus", width = 2.2f) {
        moveTo(12f, 5f); lineTo(12f, 19f)
        moveTo(5f, 12f); lineTo(19f, 12f)
    }

    val Trash = stroke("trash") {
        moveTo(3.4f, 6.2f); lineTo(20.6f, 6.2f)
        moveTo(8.4f, 6.2f); lineTo(8.4f, 3.6f); lineTo(15.6f, 3.6f); lineTo(15.6f, 6.2f)
        moveTo(18.4f, 6.2f); lineTo(17.4f, 20.4f); lineTo(6.6f, 20.4f); lineTo(5.6f, 6.2f)
    }

    val Database = stroke("database") {
        moveTo(12f, 2.6f)
        arcTo(9f, 3.1f, 0f, true, true, 12f, 8.8f)
        arcTo(9f, 3.1f, 0f, true, true, 12f, 2.6f)
        moveTo(12f, 9.4f)
        arcTo(9f, 3.1f, 0f, true, true, 12f, 15.6f)
        arcTo(9f, 3.1f, 0f, true, true, 12f, 9.4f)
        moveTo(3f, 5.7f); lineTo(3f, 18.3f)
        moveTo(21f, 5.7f); lineTo(21f, 18.3f)
    }
    /** 随机循环（播放条循环模式的「随机」档） */
    val Shuffle = stroke("shuffle") {
        moveTo(3.2f, 7.4f); lineTo(6.3f, 7.4f)
        quadTo(8.1f, 7.4f, 9.3f, 8.8f); lineTo(13.5f, 13.8f)
        quadTo(14.7f, 15.2f, 16.5f, 15.2f); lineTo(20.6f, 15.2f)
        moveTo(18.2f, 12.9f); lineTo(20.7f, 15.2f); lineTo(18.2f, 17.5f)
        moveTo(3.2f, 16.6f); lineTo(6.3f, 16.6f)
        quadTo(8.1f, 16.6f, 9.3f, 15.2f); lineTo(10.5f, 13.8f)
        moveTo(13.9f, 9.7f); lineTo(13.5f, 10.2f)
        quadTo(14.7f, 8.8f, 16.5f, 8.8f); lineTo(20.6f, 8.8f)
        moveTo(18.2f, 6.5f); lineTo(20.7f, 8.8f); lineTo(18.2f, 11.1f)
    }

    /** 中英切换（两块斜着的空心长方形，长句卡的译文开关） */
    val Layers = stroke("layers") {
        moveTo(12.6f, 3.9f); lineTo(15.5f, 5.6f); lineTo(8.7f, 15.5f); lineTo(5.8f, 13.8f); close()
        moveTo(18.2f, 7.1f); lineTo(21.1f, 8.8f); lineTo(14.3f, 18.7f); lineTo(11.4f, 17.0f); close()
    }
    /** 意见信箱（右下角浮钮，与相册 App 同一枚信封） */
    val Envelope = stroke("envelope") {
        moveTo(3.5f, 5.5f); lineTo(20.5f, 5.5f); lineTo(20.5f, 18.5f); lineTo(3.5f, 18.5f); close()
        moveTo(4.5f, 7f); lineTo(12f, 13f); lineTo(19.5f, 7f)
    }
}
