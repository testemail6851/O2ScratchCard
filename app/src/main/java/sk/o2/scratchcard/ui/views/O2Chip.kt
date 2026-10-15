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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.o2.scratchcard.data.enums.ChipType
import sk.o2.scratchcard.ui.theme.Green80

@Composable
fun O2Chip(
    modifier: Modifier = Modifier,
    type: ChipType,
    label: String
) {
    Row(
        modifier = modifier
            .background(
                when (type) {
                    ChipType.Success -> Green80
                },
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 7.dp)
            .defaultMinSize(minHeight = 22.dp)
        ,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = when (type) {
                ChipType.Success -> Icons.Filled.Check
            },
            contentDescription = null,
            tint = when (type) {
                ChipType.Success -> Color.White
            }
        )
        Text(
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = TextUnit(1.42.dp.value, TextUnitType.Sp),
            text = label.uppercase(),
            color = when (type) {
                ChipType.Success -> Color.White
            }
        )
    }
}

@Preview
@Composable
fun PreviewO2Chip() {
    O2Chip(
        type = ChipType.Success,
        label = "Active"
    )
}