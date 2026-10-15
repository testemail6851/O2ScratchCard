package sk.o2.scratchcard.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sk.o2.scratchcard.domain.model.UserSettings
import sk.o2.scratchcard.domain.repository.PreferencesRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PreferencesRepository
): ViewModel() {

    val settingsState = repository.getUserSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings()
        )

    fun resetSettings() {
        viewModelScope.launch {
            repository.resetSettings()
        }
    }

    fun deleteCode() {
        viewModelScope.launch {
            repository.deleteCode()
        }
    }
}