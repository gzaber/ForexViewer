package com.gzaber.forexviewer.di

import com.gzaber.forexviewer.data.repository.apikey.ApiKeyRepository
import com.gzaber.forexviewer.data.source.network.ForexDataApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForexDataNetworkModule {

    private const val MEDIA_TYPE: String = "application/json"
    private val json = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun provideApiKeyInterceptor(apiKeyRepository: ApiKeyRepository): Interceptor = runBlocking {
        ApiKeyInterceptor(apiKeyRepository.loadApiKey().first())
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Singleton
    @Provides
    fun provideForexDataApiService(httpClient: OkHttpClient): ForexDataApiService =
        Retrofit.Builder()
            .baseUrl(ForexDataApiService.BASE_URL)
            .addConverterFactory(
                json.asConverterFactory(MediaType.get(MEDIA_TYPE))
            )
            .client(httpClient)
            .build()
            .create(ForexDataApiService::class.java)

}