package sk.o2.scratchcard.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import sk.o2.scratchcard.R
import sk.o2.scratchcard.data.local.ScratchStatus
import sk.o2.scratchcard.ui.views.Header
import sk.o2.scratchcard.ui.views.ScratchCard
import sk.o2.scratchcard.ui.views.ScratchCardStatus

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToScratch: () -> Unit,
    onNavigateToActivate: () -> Unit
) {
    val settings by viewModel.settingsState.collectAsStateWithLifecycle()
    val status: ScratchStatus = settings.getScratchStatus()
    Scaffold(
        bottomBar = {
            HomeBottomActions(
                status,
                onClearData = {
                    viewModel.resetSettings()
                },
                onNavigateToScratch,
                onNavigateToActivate
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        ) {
            Header(
                label = stringResource(R.string.scratch_card_header_title),
                onNavigateBack = {}
            )

            ScratchCard(
                userSettings = settings,
                isScratching = false,
                modifier = Modifier.padding(horizontal = 24.dp),
                onScratchCardClick = onNavigateToScratch
            )

            Spacer(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp)
            )

            ScratchCardStatus(
                modifier = Modifier.padding(horizontal = 24.dp),
                status = status
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        viewModel = hiltViewModel(),
        onNavigateToScratch = {},
        onNavigateToActivate = {}
    )
}