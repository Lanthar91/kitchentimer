package com.example.timer.prefs

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepository(private val context: Context) {
    private val Context.dataStore by preferencesDataStore("settings")

    private val SOUND_KEY = stringPreferencesKey("sound_uri")
    private val VIBRATION_KEY = booleanPreferencesKey("vibration")

    val soundUri: Flow<String?> = context.dataStore.data.map { it[SOUND_KEY] }
    val vibrationEnabled: Flow<Boolean> = context.dataStore.data.map { it[VIBRATION_KEY] ?: true }

    suspend fun setSoundUri(uri: String?) {
        context.dataStore.edit { prefs ->
            if (uri != null) prefs[SOUND_KEY] = uri else prefs.remove(SOUND_KEY)
        }
    }

    suspend fun setVibration(enabled: Boolean) {
        context.dataStore.edit { it[VIBRATION_KEY] = enabled }
    }
}
