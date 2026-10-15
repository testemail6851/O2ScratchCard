package sk.o2.scratchcard.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import sk.o2.scratchcard.data.enums.DialogType
import sk.o2.scratchcard.ui.theme.Blue10
import sk.o2.scratchcard.ui.theme.Blue60
import sk.o2.scratchcard.ui.theme.Grey05
import sk.o2.scratchcard.ui.theme.Grey40
import sk.o2.scratchcard.ui.theme.Orange80
import sk.o2.scratchcard.ui.theme.Red05
import sk.o2.scratchcard.ui.theme.Red80

@Composable
fun O2Dialog(
    modifier: Modifier = Modifier,
    type: DialogType,
    title: String? = null,
    text: String? = null,
    additionalInfo: String? = null,
    confirmText: String? = null,
    dismissText: String? = null,
    onConfirmed: () -> Unit = { },
    onDismissed: () -> Unit = { },
) {
    Dialog(onDismissRequest = onDismissed) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(when (type) {
                            DialogType.Error -> Red05
                            DialogType.Warning -> Grey05
                            DialogType.Info -> Blue10
                        }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        tint = when (type) {
                            DialogType.Error -> Red80
                            DialogType.Warning -> Orange80
                            DialogType.Info -> Blue60
                        },
                        imageVector = when (type) {
                            DialogType.Error -> Icons.Filled.Error
                            DialogType.Warning -> Icons.Filled.Warning
                            DialogType.Info -> Icons.Filled.Info
                        },
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (title != null) {
                    Text(
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        text = title
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (text != null) {
                    Text(
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W300,
                        color = Grey40,
                        text = text
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                if (additionalInfo != null) {
                    Text(
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Grey40,
                        text = additionalInfo
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (dismissText != null) {
                        TextButton(
                            onClick = onDismissed,
                            modifier = Modifier.defaultMinSize(minHeight = 44.dp, minWidth = 108.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Blue60,
                                disabledContainerColor = Grey05,
                                disabledContentColor = Grey40
                            )
                        ) {
                            Text(
                                text = dismissText,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (confirmText != null && dismissText != null) {
                        Spacer(modifier = Modifier.size(8.dp))
                    }

                    if (confirmText != null) {
                        Button(
                            onClick = onConfirmed,
                            modifier = Modifier.defaultMinSize(minHeight = 44.dp, minWidth = 108.dp),
                            shape = RoundedCornerShape(22.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Blue60,
                                contentColor = Color.White,
                                disabledContainerColor = Grey05,
                                disabledContentColor = Grey40
                            )
                        ) {
                            Text(confirmText)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewO2Dialog() {
    O2Dialog(
        type = DialogType.Error,
        title = "Activation failed",
        text = "The service didn't accept this code, so the card stays scratched but not activated. Please try again.",
        confirmText = "Try again",
        dismissText = "Close"
    )
}