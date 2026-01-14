package com.muhammad.pilltime.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.muhammad.pilltime.domain.repository.SettingPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SettingPreferencesImp(
    private val context : Context
) : SettingPreferences {
    companion object {
        private val Context.settingPreferences by preferencesDataStore("setting_preferences")
        private const val SHOW_BOARDING_KEY = "show_boarding"
        private const val USERNAME_KEY = "username"
    }
    private val showBoarding = booleanPreferencesKey(SHOW_BOARDING_KEY)
    private val username = stringPreferencesKey(USERNAME_KEY)
    override suspend fun saveShowBoarding(show: Boolean) {
        context.settingPreferences.edit {preferences ->
            preferences[showBoarding] = show
        }
    }

    override fun observeShowBoarding(): Flow<Boolean> {
        return context.settingPreferences.data.map { preferences ->
            preferences[showBoarding] ?: true
        }.distinctUntilChanged()
    }

    override suspend fun saveUsername(name: String) {
        context.settingPreferences.edit { preferences ->
            preferences[username] = name
        }
    }

    override fun observeUsername(): Flow<String> {
        return context.settingPreferences.data.map { preferences ->
            preferences[username] ?: ""
        }.distinctUntilChanged()
    }
}