package sk.o2.scratchcard.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.o2.scratchcard.R
import sk.o2.scratchcard.data.enums.AlertType
import sk.o2.scratchcard.ui.theme.Blue80
import sk.o2.scratchcard.ui.theme.Grey04
import sk.o2.scratchcard.ui.theme.Grey40
import sk.o2.scratchcard.ui.views.O2Alert
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale
import sk.o2.scratchcard.data.local.Constants
import sk.o2.scratchcard.ui.theme.Blue60

@Composable
fun DetailScratchingStatus(
    modifier: Modifier = Modifier,
    progress: Float,
    elapsedTimeSeconds: Float,
    isScratching: Boolean,
) {
    val locale = LocalLocale.current.platformLocale
    Column(
        modifier = modifier
    ) {
        if (isScratching) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = Blue60,
                    trackColor = Grey04
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Blue80,
                        text = stringResource(R.string.revealing_code)
                    )
                    Text(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Grey40,
                        text = String.format(locale, "%.1f / ${(Constants.SCRATCH_DURATION_MS / 1000F).toInt()} s", elapsedTimeSeconds)
                    )
                }

                O2Alert(
                    type = AlertType.Warning,
                    text = stringResource(R.string.cancels_scratching_warning)
                )
            }
        } else {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black,
                text = stringResource(R.string.tap_to_scratch_instruction)
            )
        }
    }
}

@Preview
@Composable
fun PreviewDetailScratchingStatus() {
    DetailScratchingStatus(
        isScratching = true,
        progress = 0.4F,
        elapsedTimeSeconds = 1.4F
    )
}