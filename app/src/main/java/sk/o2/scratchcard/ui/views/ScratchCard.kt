package sk.o2.scratchcard.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.o2.scratchcard.R
import sk.o2.scratchcard.data.enums.ChipType
import sk.o2.scratchcard.domain.model.UserSettings
import sk.o2.scratchcard.ext.customDropShadow
import sk.o2.scratchcard.ext.stripedBackground

@Composable
fun ScratchCard(
    userSettings: UserSettings,
    isScratching: Boolean,
    modifier: Modifier = Modifier,
    onScratchCardClick: () -> Unit = {}
) {
    val code = userSettings.code
    val isActivated = userSettings.isCodeActivated
    val isClickable = code == null
    Box(
        modifier = modifier
            .customDropShadow(
                color = Color(0xFF10204A).copy(alpha = 0.25f),
                blur = 32.dp,
                offsetY = 8.dp,
                offsetX = 0.dp
            )
            .fillMaxWidth()
            .background(Color(0xFF10204A), shape = RoundedCornerShape(24.dp))
            .defaultMinSize(minHeight = 224.dp)
            .clickable(enabled = isClickable, onClick = onScratchCardClick)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 20.dp)
                .align(Alignment.TopCenter)
        )  {
            Text(
                text = stringResource(R.string.scratch_card_title).uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                letterSpacing = TextUnit(1.82.dp.value, TextUnitType.Sp),
                modifier = Modifier
                    .weight(1F)
                    .align(Alignment.CenterVertically)
            )
            if (isActivated) {
                O2Chip(
                    type = ChipType.Success,
                    label = stringResource(R.string.active)
                )
            } else {
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(26.dp)
                        .background(Color(0xFFD4AE5C), RoundedCornerShape(6.dp))
                        .align(Alignment.CenterVertically)
                )
            }
        }

        val scratchAreaModifier = Modifier.align(Alignment.Center)

        if (code != null) {
            RevealedScratchArea(
                modifier = scratchAreaModifier,
                code = code
            )
        } else {
            ScratchArea(
                isScratching,
                modifier = scratchAreaModifier
            )
        }

        Text(
            text = when {
                isActivated -> stringResource(R.string.activated)
                code != null -> stringResource(R.string.ready_to_activate)
                else -> stringResource(R.string.reveal_code_instruction)
            },
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Light,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 22.dp, vertical = 20.dp)
        )
    }
}

@Composable
fun ScratchArea(
    isScratching: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .defaultMinSize(minHeight = 64.dp)
            .clip(RoundedCornerShape(12.dp))
            .alpha(if (isScratching) 0.5F else 1F)
            .stripedBackground(
                baseColor = Color(0xFFBAC0CC),
                stripeColor = Color.White.copy(alpha = 0.4f),
                stripeWidth = 6.dp,
                stripeSpacing = 8.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = (if (isScratching) stringResource(R.string.scratching_ellipsis) else stringResource(R.string.scratch_here)).uppercase(),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = TextUnit(1.6.dp.value, TextUnitType.Sp),
            color = Color(0xFF4A5468),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        )
    }
}

@Composable
fun RevealedScratchArea(
    modifier: Modifier = Modifier,
    code: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .defaultMinSize(minHeight = 64.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x08FFFFFF))
            .drawBehind {
                val dashEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
                drawRoundRect(
                    color = Color(0x44FFFFFF),
                    style = Stroke(width = 3.dp.toPx(), pathEffect = dashEffect),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx())
                )
            }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.your_code).uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = TextUnit(1.6.dp.value, TextUnitType.Sp),
            color = Color(0xFFAEB9D6)
        )
        Text(
            text = code,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = TextUnit(1.4.dp.value, TextUnitType.Sp),
            color = Color(0xFFFFFFFF)
        )
    }
}

@Preview
@Composable
fun PreviewScratchCard() {
    ScratchCard(
        userSettings = UserSettings(
//            code = "12345",
//            isCodeActivated = true
        ),
        isScratching = true,
        onScratchCardClick = {}
    )
}

@Preview
@Composable
fun PreviewScratchArea() {
    ScratchArea(
        isScratching = true
    )
}

@Preview
@Composable
fun PreviewRevealedScratchArea() {
    RevealedScratchArea(
        code = "3f9c2e1a-7b4d-4e0f-a6c2-91d8b5e07f3"
    )
}