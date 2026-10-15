package sk.o2.scratchcard.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import sk.o2.scratchcard.data.enums.AlertType
import sk.o2.scratchcard.ui.theme.Blue10
import sk.o2.scratchcard.ui.theme.Blue60
import sk.o2.scratchcard.ui.theme.Orange10
import sk.o2.scratchcard.ui.theme.Orange80
import sk.o2.scratchcard.ui.theme.Orange90
import sk.o2.scratchcard.ui.theme.Red10
import sk.o2.scratchcard.ui.theme.Red80

@Composable
fun O2Alert(
    modifier: Modifier = Modifier,
    type: AlertType,
    text: String
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .background(when (type) {
                AlertType.Info -> Blue10
                AlertType.Warning -> Orange10
                AlertType.Error -> Red10
            },
              shape = RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp).align(Alignment.Top),
            imageVector = when (type) {
                AlertType.Info -> Icons.Filled.Info
                AlertType.Warning -> Icons.Filled.Info
                AlertType.Error -> Icons.Filled.Error
            },
            contentDescription = null,
            tint = when (type) {
                AlertType.Info -> Blue60
                AlertType.Warning -> Orange80
                AlertType.Error -> Red80
            }
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Normal,
            letterSpacing = TextUnit(1.42.dp.value, TextUnitType.Sp),
            text = text,
            color = when (type) {
                AlertType.Info -> Blue60
                AlertType.Warning -> Orange90
                AlertType.Error -> Red80
            }
        )
    }
}

@Preview
@Composable
fun PreviewO2Alert() {
    O2Alert(
        type = AlertType.Info,
        text = "This is a warning"
    )
}