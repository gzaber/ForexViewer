package com.gzaber.forexviewer.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataStore(
    initialData: String? = null,
    val shouldThrow: Boolean = false
) : DataStore<Preferences> {

    private val _data: MutablePreferences = emptyPreferences().toMutablePreferences()

    init {
        if (initialData != null) {
            _data[stringPreferencesKey("api_key")] = initialData
        }
    }

    override val data: Flow<Preferences>
        get() = flow {
            if (shouldThrow) {
                throw Exception()
            } else {
                emit(_data)
            }
        }

    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
        _data[stringPreferencesKey("api_key")] = "123apikey"
        return transform(_data)
    }
}