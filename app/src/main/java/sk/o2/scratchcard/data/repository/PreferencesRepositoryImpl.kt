package sk.o2.scratchcard.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import sk.o2.scratchcard.domain.model.UserSettings
import sk.o2.scratchcard.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private object PreferencesKeys {
        val SCRATCH_CODE = stringPreferencesKey("scratch_code")
        val IS_CODE_ACTIVATED = booleanPreferencesKey("is_code_activated")
    }

    override fun getUserSettings(): Flow<UserSettings> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            UserSettings(
                code = preferences[PreferencesKeys.SCRATCH_CODE],
                isCodeActivated = preferences[PreferencesKeys.IS_CODE_ACTIVATED] ?: false
            )
        }
    }

    override suspend fun resetSettings() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun saveCode(code: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SCRATCH_CODE] = code
            preferences[PreferencesKeys.IS_CODE_ACTIVATED] = false
        }
    }

    override suspend fun deleteCode() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.SCRATCH_CODE)
        }
    }

    override suspend fun updateIsCodeActivated(isActivated: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_CODE_ACTIVATED] = isActivated
        }
    }

}