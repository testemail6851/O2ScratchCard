package sk.o2.scratchcard.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.o2.scratchcard.data.local.Constants
import sk.o2.scratchcard.domain.model.UserSettings
import sk.o2.scratchcard.domain.repository.PreferencesRepository
import java.util.UUID
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

data class DetailUiState(
    val isScratching: Boolean = false,
    val progress: Float = 0F,
    val elapsedTimeSeconds: Float = 0F
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PreferencesRepository
) : ViewModel() {

    val settingsState = repository.getUserSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings()
        )

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun scratchCard() {
        viewModelScope.launch {
            _uiState.update { it.copy(isScratching = true, progress = 0F, elapsedTimeSeconds = 0F) }

            val intervalMs = 50L
            var elapsedMs = 0L
            val maxSeconds = Constants.SCRATCH_DURATION_MS / 1000L
            while (elapsedMs < Constants.SCRATCH_DURATION_MS) {
                delay(intervalMs.milliseconds)
                elapsedMs += intervalMs
                val progress = (elapsedMs.toFloat() / Constants.SCRATCH_DURATION_MS).coerceIn(0F, 1F)
                val seconds = (elapsedMs / 1000F).coerceAtMost(maxSeconds)

                _uiState.update {
                    it.copy(progress = progress, elapsedTimeSeconds = seconds)
                }
            }

            val randomUUID = UUID.randomUUID().toString()
            repository.saveCode(randomUUID)

            _uiState.update { it.copy(isScratching = false) }
        }
    }
}