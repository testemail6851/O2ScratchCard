package sk.o2.scratchcard.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.o2.scratchcard.ui.theme.Black10
import sk.o2.scratchcard.ui.theme.Blue80
import sk.o2.scratchcard.ui.theme.Grey40

@Composable
fun ScratchCode(
    modifier: Modifier = Modifier,
    label: String,
    code: String?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .drawBehind {
                val dashEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
                drawRoundRect(
                    color = Black10,
                    style = Stroke(width = 3.dp.toPx(), pathEffect = dashEffect),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = TextUnit(1.6.dp.value, TextUnitType.Sp),
            color = Grey40
        )
        Text(
            text = code ?: "---",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = TextUnit(1.4.dp.value, TextUnitType.Sp),
            color = Blue80
        )
    }
}

@Preview
@Composable
fun PreviewScratchCode() {
    ScratchCode(
        label = "Code to activate",
        code = "1234456-7b4d-4e0f-a6c2-91d8b5e07f3"
    )
}