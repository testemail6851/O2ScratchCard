package sk.o2.scratchcard.ui.screens.activate

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
import sk.o2.scratchcard.ui.views.O2SecondaryButton

@Composable
fun ActivateBottomActions(
    isActivating: Boolean,
    isActivated: Boolean,
    onActivate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
    ) {
        O2SecondaryButton(
            text = when {
                isActivating -> stringResource(R.string.activating)
                isActivated -> stringResource(R.string.code_is_activated)
                else -> stringResource(R.string.activate_card)
            },
            enabled = !isActivated && !isActivating,
            showProgress = isActivating,
            onClick = onActivate
        )
    }
}

@Preview
@Composable
fun PreviewActivateBottomActions() {
    ActivateBottomActions(
        isActivating = false,
        isActivated = false,
        onActivate = {}
    )
}