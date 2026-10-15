package sk.o2.scratchcard.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import sk.o2.scratchcard.ui.views.Header
import sk.o2.scratchcard.ui.views.ScratchCard

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onNavigateBack: () -> Unit
) {
    val settings by viewModel.settingsState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val status = settings.getScratchStatus()

    Scaffold(
        bottomBar = {
            DetailBottomActions(
                status = status,
                isScratching = uiState.isScratching,
                onScratchToReveal = {
                    viewModel.scratchCard()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        ) {
            Header(
                label = stringResource(R.string.scratch_header_title),
                canGoBack = true,
                onNavigateBack = onNavigateBack
            )

            ScratchCard(
                userSettings = settings,
                isScratching = uiState.isScratching,
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                onScratchCardClick = {
                    viewModel.scratchCard()
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            DetailScratchingStatus(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                progress = uiState.progress,
                elapsedTimeSeconds = uiState.elapsedTimeSeconds,
                isScratching = uiState.isScratching
            )
        }
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(
        viewModel = hiltViewModel(),
        onNavigateBack = {}
    )
}
