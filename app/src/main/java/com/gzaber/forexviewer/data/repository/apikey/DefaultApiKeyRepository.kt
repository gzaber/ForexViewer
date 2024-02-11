package com.gzaber.forexviewer.data.repository.apikey

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultApiKeyRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ApiKeyRepository {

    override fun loadApiKey(): Flow<String> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preferences ->
            preferences[API_KEY] ?: DEMO_API_KEY
        }

    override suspend fun saveApiKey(apiKey: String) {
        dataStore.edit { preferences ->
            preferences[API_KEY] = apiKey
        }
    }

    private companion object {
        val API_KEY = stringPreferencesKey("api_key")
        const val DEMO_API_KEY = "demo"
    }
}