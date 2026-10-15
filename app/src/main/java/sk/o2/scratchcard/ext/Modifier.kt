package sk.o2.scratchcard.ext

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.nativePaint
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.customDropShadow(
    color: Color = Color(0xFF10204A).copy(alpha = 0.25f),
    blur: Dp = 32.dp,
    offsetY: Dp = 12.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.nativePaint
        frameworkPaint.color = color.toArgb()
        if (blur.value > 0f) {
            frameworkPaint.maskFilter = BlurMaskFilter(
                blur.toPx(),
                BlurMaskFilter.Blur.NORMAL
            )
        }
        val spreadPx = spread.toPx()
        val left = -spreadPx + offsetX.toPx()
        val top = -spreadPx + offsetY.toPx()
        val right = size.width + spreadPx + offsetX.toPx()
        val bottom = size.height + spreadPx + offsetY.toPx()
        canvas.nativeCanvas.drawRect(
            left, top, right, bottom,
            frameworkPaint
        )
    }
}

fun Modifier.stripedBackground(
    baseColor: Color,
    stripeColor: Color = Color.White.copy(alpha = 0.4f),
    stripeWidth: Dp = 10.dp,
    stripeSpacing: Dp = 10.dp
) = this.drawBehind {
    drawRect(baseColor)

    val widthPx = stripeWidth.toPx()
    val spacingPx = stripeSpacing.toPx()
    val totalStep = widthPx + spacingPx

    var x = -size.height
    while (x < size.width + size.height) {
        drawLine(
            color = stripeColor,
            start = Offset(x = x, y = size.height),
            end = Offset(x = x + size.height, y = 0f),
            strokeWidth = widthPx
        )
        x += totalStep
    }
}