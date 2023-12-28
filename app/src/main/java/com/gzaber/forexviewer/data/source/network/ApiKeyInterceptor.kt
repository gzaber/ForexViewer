package com.gzaber.forexviewer.data.source.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedUrl = originalRequest.url()
            .newBuilder()
            .addQueryParameter(
                "apikey",
                apiKey
            )
            .build()
        val request = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        return chain.proceed(request)
    }
}