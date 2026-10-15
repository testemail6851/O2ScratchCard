package sk.o2.scratchcard.ui.screens.activate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.o2.scratchcard.R
import sk.o2.scratchcard.domain.model.ActivationResult
import sk.o2.scratchcard.domain.model.UserSettings
import sk.o2.scratchcard.domain.repository.ApiRepository
import sk.o2.scratchcard.domain.repository.PreferencesRepository
import javax.inject.Inject

data class ActivateUiState(
    val isActivating: Boolean = false,
    val result: ActivationResult? = null,
)

sealed interface ActivateUiEffect {
    data class ShowSnackBar(@androidx.annotation.StringRes val messageResId: Int) : ActivateUiEffect
    data class ShowToast(@androidx.annotation.StringRes val messageResId: Int) : ActivateUiEffect
}

@HiltViewModel
class ActivateViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository,
    private val apiRepository: ApiRepository
) : ViewModel() {

    val settingsState = preferencesRepository.getUserSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings()
        )

    private val _uiState = MutableStateFlow(ActivateUiState())
    val uiState: StateFlow<ActivateUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ActivateUiEffect>()
    val uiEffect: SharedFlow<ActivateUiEffect> = _uiEffect.asSharedFlow()

    fun activateCode(code: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isActivating = true, result = null) }
            try {
                val result = apiRepository.activateCode(code)
                if (result is ActivationResult.NoInternet) {
                    _uiEffect.emit(ActivateUiEffect.ShowSnackBar(R.string.no_network))
                }
                _uiState.update { it.copy(result = result) }
            } catch (e: Exception) {
                _uiState.update { it.copy(result = ActivationResult.ApiError(e.message)) }
            } finally {
                _uiState.update { it.copy(isActivating = false) }
            }
        }
    }

    fun dismissErrorModal() {
        _uiState.update { it.copy(result = null) }
    }
}
