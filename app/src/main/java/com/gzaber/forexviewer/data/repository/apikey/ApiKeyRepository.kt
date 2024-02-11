package com.gzaber.forexviewer.data.repository.apikey

import kotlinx.coroutines.flow.Flow

interface ApiKeyRepository {
    fun loadApiKey(): Flow<String>
    suspend fun saveApiKey(apiKey: String)
}