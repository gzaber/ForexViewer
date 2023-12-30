package com.gzaber.forexviewer.di

import com.gzaber.forexviewer.data.source.network.ApiKeyInterceptor
import com.gzaber.forexviewer.data.source.network.ForexDataApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor("demo")).build()

    @Singleton
    @Provides
    fun provideForexDataApiService(httpClient: OkHttpClient): ForexDataApiService =
        Retrofit.Builder()
            .baseUrl(ForexDataApiService.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .client(httpClient)
            .build()
            .create(ForexDataApiService::class.java)
}