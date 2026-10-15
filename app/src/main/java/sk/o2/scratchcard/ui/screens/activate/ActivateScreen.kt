package sk.o2.scratchcard.ui.screens.activate

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import sk.o2.scratchcard.R
import sk.o2.scratchcard.data.enums.AlertType
import sk.o2.scratchcard.data.enums.DialogType
import sk.o2.scratchcard.domain.model.ActivationResult
import sk.o2.scratchcard.ui.views.Header
import sk.o2.scratchcard.ui.views.O2Alert
import sk.o2.scratchcard.ui.views.O2Dialog
import sk.o2.scratchcard.ui.views.ScratchCode

@Composable
fun ActivateScreen(
    viewModel: ActivateViewModel,
    onNavigateBack: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val settings by viewModel.settingsState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val currentContext by rememberUpdatedState(context)
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ActivateUiEffect.ShowToast -> {
                    Toast.makeText(currentContext, currentContext.getString(effect.messageResId), Toast.LENGTH_SHORT).show()
                }
                is ActivateUiEffect.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(currentContext.getString(effect.messageResId))
                }
            }
        }
    }

    val isActivating = uiState.isActivating
    val isCodeActivated = settings.isCodeActivated

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        bottomBar = {
            ActivateBottomActions(
                isActivating = isActivating,
                isActivated = isCodeActivated,
                onActivate = {
                    settings.code?.let { code ->
                        viewModel.activateCode(code)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header(
                label = stringResource(R.string.activate_title),
                canGoBack = true,
                onNavigateBack = onNavigateBack
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ScratchCode(
                    label = if (isCodeActivated) stringResource(R.string.activated_code) else stringResource(R.string.code_to_activate),
                    code = settings.code
                )

                if (!isCodeActivated) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Light,
                        text = stringResource(R.string.verification_instruction)
                    )

                    O2Alert(
                        type = AlertType.Info,
                        text = stringResource(R.string.background_notice)
                    )
                }
            }

            val showErrorModal = uiState.result is ActivationResult.InvalidCode || uiState.result is ActivationResult.ApiError
            val errorMessage = when (val res = uiState.result) {
                is ActivationResult.InvalidCode -> res.message
                is ActivationResult.ApiError -> res.message
                else -> null
            }

            if (showErrorModal) {
                O2Dialog(
                    type = DialogType.Error,
                    title = stringResource(R.string.activation_failed_title),
                    text = stringResource(R.string.activation_failed_text),
                    additionalInfo = errorMessage,
                    confirmText = stringResource(R.string.try_again),
                    dismissText = stringResource(R.string.close),
                    onConfirmed = {
                        viewModel.dismissErrorModal()
                        settings.code?.let { code ->
                            viewModel.activateCode(code)
                        }
                    },
                    onDismissed = {
                        viewModel.dismissErrorModal()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewActivateScreen() {
    ActivateScreen(
        viewModel = hiltViewModel(),
        onNavigateBack = {}
    )
}
