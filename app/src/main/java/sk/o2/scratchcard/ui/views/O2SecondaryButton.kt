package sk.o2.scratchcard.ui.views

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import sk.o2.scratchcard.ui.theme.Blue60
import sk.o2.scratchcard.ui.theme.Grey05
import sk.o2.scratchcard.ui.theme.Grey10
import sk.o2.scratchcard.ui.theme.Grey20

@Composable
fun O2SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    showProgress: Boolean = false
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue60,
            contentColor = Color.White,
            disabledContainerColor = Grey05,
            disabledContentColor = Grey20
        ),
        shape = RoundedCornerShape(28.dp),
        onClick = onClick
    ) {
        if (showProgress) {
            CircularProgressIndicator(
                trackColor = Grey10,
                color = Blue60,
                strokeWidth = 2.dp,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )
        }
        Text(
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            letterSpacing = TextUnit(1.42.dp.value, TextUnitType.Sp),
            text = text
        )
    }
}

@Preview
@Composable
fun PreviewO2SecondaryButton() {
    O2SecondaryButton(
        text = "Activate card",
        onClick = {}
    )
}