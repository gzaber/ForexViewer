package com.gzaber.forexviewer.di

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedUrl = originalRequest.url()
            .newBuilder()
            .addQueryParameter(
                PARAMETER_NAME,
                apiKey
            )
            .build()
        val request = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val PARAMETER_NAME: String = "apikey"
    }
}