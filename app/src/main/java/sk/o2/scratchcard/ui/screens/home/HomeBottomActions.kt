package sk.o2.scratchcard.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.o2.scratchcard.R
import sk.o2.scratchcard.data.local.ScratchStatus
import sk.o2.scratchcard.ui.theme.Grey40
import sk.o2.scratchcard.ui.views.O2SecondaryButton

@Composable
fun HomeBottomActions(
    status: ScratchStatus,
    onClearData: () -> Unit,
    onNavigateToScratch: () -> Unit,
    onNavigateToActivate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val canScratch = status == ScratchStatus.Unscratched
        val canActivate = status == ScratchStatus.Scratched

        if (!canScratch) {
            O2SecondaryButton(
                text = stringResource(R.string.clear_data),
                onClick = onClearData
            )
        }

        O2SecondaryButton(
            text = if (canScratch) stringResource(R.string.scratch_card) else stringResource(R.string.already_scratched),
            enabled = canScratch,
            onClick = onNavigateToScratch
        )

        O2SecondaryButton(
            text = when {
                canActivate -> stringResource(R.string.activate_card)
                else -> stringResource(R.string.already_activated)
            },
            enabled = canActivate,
            onClick = onNavigateToActivate
        )

        if (canScratch) {
            Text(
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Light,
                color = Grey40,
                text = stringResource(R.string.scratch_first_instruction)
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeBottomActions() {
    HomeBottomActions(
        status = ScratchStatus.Unscratched,
        onClearData = {},
        onNavigateToScratch = {},
        onNavigateToActivate = {}
    )
}