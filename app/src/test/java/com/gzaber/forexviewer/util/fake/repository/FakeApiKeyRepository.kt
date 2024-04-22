package com.gzaber.forexviewer.util.fake.repository

import com.gzaber.forexviewer.data.repository.apikey.ApiKeyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update


class FakeApiKeyRepository : ApiKeyRepository {

    private val _apiKey = MutableStateFlow("demo")
    private val _shouldThrowError = MutableStateFlow(false)

    private val _flow = combine(
        _apiKey,
        _shouldThrowError
    ) { apiKey, shouldThrow ->
        if (shouldThrow) {
            throw Exception("failure")
        } else {
            apiKey
        }
    }

    fun setShouldThrowError(value: Boolean) {
        _shouldThrowError.update { value }
    }

    override fun loadApiKey(): Flow<String> = _flow

    override suspend fun saveApiKey(apiKey: String) {
        if (_shouldThrowError.value) {
            throw Exception("failure")
        } else {
            _apiKey.update { apiKey }
        }
    }
}