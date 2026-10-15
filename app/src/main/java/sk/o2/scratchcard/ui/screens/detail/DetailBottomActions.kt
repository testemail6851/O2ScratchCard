package sk.o2.scratchcard.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.o2.scratchcard.R
import sk.o2.scratchcard.data.local.ScratchStatus
import sk.o2.scratchcard.ui.views.O2SecondaryButton

@Composable
fun DetailBottomActions(
    status: ScratchStatus,
    isScratching: Boolean,
    onScratchToReveal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
    ) {
        val isScratched = status == ScratchStatus.Scratched
        O2SecondaryButton(
            text = when {
                isScratching -> stringResource(R.string.scratching)
                isScratched -> stringResource(R.string.card_was_scratched)
                else -> stringResource(R.string.scratch_to_reveal)
            },
            enabled = !isScratched && !isScratching,
            showProgress = isScratching,
            onClick = onScratchToReveal
        )
    }
}

@Preview
@Composable
fun PreviewDetailBottomActions() {
    DetailBottomActions(
        status = ScratchStatus.Scratched,
        isScratching = false,
        onScratchToReveal = {}
    )
}
