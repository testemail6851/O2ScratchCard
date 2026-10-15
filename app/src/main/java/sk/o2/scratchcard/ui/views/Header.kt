package sk.o2.scratchcard.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.o2.scratchcard.R
import sk.o2.scratchcard.ui.theme.Black05

@Composable
fun Header(
    modifier: Modifier = Modifier,
    label: String,
    canGoBack: Boolean = false,
    onNavigateBack: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
            .background(Black05),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (canGoBack) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.padding(start = 12.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_back),
                    tint = Color.Black
                )
            }
        }

        Text(
            modifier = Modifier.padding(
                start = if (canGoBack) 0.dp else 22.dp,
                end = 22.dp
            ),
            color = Color.Black,
            fontSize = if (canGoBack) 18.sp else 24.sp,
            fontWeight = FontWeight.Bold,
            text = label
        )
    }
}

@Preview
@Composable
fun PreviewHeader() {
    Header(
        label = "Scratch",
        canGoBack = true,
        onNavigateBack = {}
    )
}