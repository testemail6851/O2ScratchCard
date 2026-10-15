package sk.o2.scratchcard.ui.views

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.o2.scratchcard.R
import sk.o2.scratchcard.data.local.ScratchStatus
import sk.o2.scratchcard.ui.theme.Blue60
import sk.o2.scratchcard.ui.theme.Blue80
import sk.o2.scratchcard.ui.theme.Green80
import sk.o2.scratchcard.ui.theme.Grey40

@Composable
fun ScratchCardStatus(
    status: ScratchStatus,
    modifier: Modifier = Modifier
) {
    val isActivated = status.stepIndex >= ScratchStatus.Activated.stepIndex
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(224.dp)
    ) {
        Text(
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Normal,
            color = Grey40,
            text = stringResource(R.string.current_state)
        )
        Text(
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = if (isActivated) Green80 else Blue80,
            text = stringResource(status.textId)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ScratchCardStatusColumn(
                modifier = Modifier.weight(1f),
                status = ScratchStatus.Unscratched,
                isCompleted = status.stepIndex > ScratchStatus.Unscratched.stepIndex,
                isCurrent = status.stepIndex == ScratchStatus.Unscratched.stepIndex,
                isActivated = isActivated,
            )
            ScratchCardStatusColumn(
                modifier = Modifier.weight(1f),
                status = ScratchStatus.Scratched,
                isCompleted = status.stepIndex > ScratchStatus.Scratched.stepIndex,
                isCurrent = status.stepIndex == ScratchStatus.Scratched.stepIndex,
                isActivated = isActivated
            )
            ScratchCardStatusColumn(
                modifier = Modifier.weight(1f),
                status = ScratchStatus.Activated,
                isCompleted = status.stepIndex >= ScratchStatus.Activated.stepIndex,
                isCurrent = status.stepIndex == ScratchStatus.Activated.stepIndex,
                isActivated = isActivated
            )
        }
    }
}

@Composable
fun ScratchCardStatusColumn(
    modifier: Modifier = Modifier,
    status: ScratchStatus,
    isCompleted: Boolean,
    isCurrent: Boolean,
    isActivated: Boolean
) {
    val bgFilledColor = when {
        isActivated -> Green80
        status == ScratchStatus.Unscratched || status == ScratchStatus.Scratched -> Blue60
        status == ScratchStatus.Activated -> Green80
        else -> Grey40
    }

    val infiniteTransition = rememberInfiniteTransition(label = "progressAnimation")
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progressFloat"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        ) {
            val fillFraction = when {
                isCompleted -> 1f
                isCurrent -> animatedProgress
                else -> 0f
            }
            if (fillFraction > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fillFraction)
                        .height(4.dp)
                        .background(bgFilledColor, shape = RoundedCornerShape(8.dp))
                )
            }
        }
        Text(
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = if (isCompleted || isCurrent) bgFilledColor else Color.LightGray,
            text = stringResource(status.textId),
        )
    }
}

@Preview
@Composable
fun PreviewScratchCardStatus() {
    ScratchCardStatus(
        status = ScratchStatus.Activated,
        modifier = Modifier
            .background(Color.White)
    )
}