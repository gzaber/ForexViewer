package com.gzaber.forexviewer.data.repository.apikey

interface ApiKeyRepository {
    suspend fun saveApiKey(apiKey: String)
}