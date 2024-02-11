package com.gzaber.forexviewer.di

import com.gzaber.forexviewer.data.source.network.ApiKeyInterceptor
import com.gzaber.forexviewer.data.source.network.ForexDataApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForexDataNetworkModule {

    private const val MEDIA_TYPE: String = "application/json"

    private val interceptor = run {
        ApiKeyInterceptor("ca59a7e7a04e4873bec1cecf0a31f64d")
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Singleton
    @Provides
    fun provideForexDataApiService(httpClient: OkHttpClient): ForexDataApiService =
        Retrofit.Builder()
            .baseUrl(ForexDataApiService.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.get(MEDIA_TYPE)))
            .client(httpClient)
            .build()
            .create(ForexDataApiService::class.java)
}