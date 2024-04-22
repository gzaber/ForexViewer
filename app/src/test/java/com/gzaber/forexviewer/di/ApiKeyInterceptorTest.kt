package com.gzaber.forexviewer.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.http.GET

class ApiKeyInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var httpClient: OkHttpClient
    private lateinit var retrofit: TestApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        httpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor("demo"))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .build()
            .create(TestApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun apiKeyInterceptor_apiKeyParameterIsAddedToUrl() = runTest {
        val testDataJson = "{\"value\":\"test\"}"
        val mockResponse = MockResponse().setBody(testDataJson)

        mockWebServer.enqueue(mockResponse)
        retrofit.test()

        val requestUrl = mockWebServer.takeRequest().requestUrl
        assert(requestUrl?.query?.contains("apikey") ?: false)
        assert(requestUrl?.query?.contains("demo") ?: false)
    }
}

private interface TestApi {
    @GET("/test")
    suspend fun test(): TestData
}

@Serializable
private data class TestData(val value: String)
