package sk.o2.scratchcard.domain.repository

import kotlinx.coroutines.flow.Flow
import sk.o2.scratchcard.domain.model.UserSettings

interface PreferencesRepository {
    fun getUserSettings(): Flow<UserSettings>
    suspend fun resetSettings()
    suspend fun saveCode(code: String)
    suspend fun deleteCode()
    suspend fun updateIsCodeActivated(isActivated: Boolean)
}