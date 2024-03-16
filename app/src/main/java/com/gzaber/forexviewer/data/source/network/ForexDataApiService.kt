package com.gzaber.forexviewer.data.source.network

import com.gzaber.forexviewer.data.source.network.model.NetworkExchangeRate
import com.gzaber.forexviewer.data.source.network.model.NetworkForexPairsList
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeries
import retrofit2.http.GET
import retrofit2.http.Query

interface ForexDataApiService {
    @GET("forex_pairs")
    suspend fun fetchAllForexPairsList(): NetworkForexPairsList

    @GET("exchange_rate")
    suspend fun fetchExchangeRate(
        @Query("symbol") symbol: String
    ): NetworkExchangeRate

    @GET("time_series")
    suspend fun fetchTimeSeries(
        @Query("symbol") symbol: String,
        @Query("interval") interval: String,
        @Query("outputsize") outputSize: Int
    ): NetworkTimeSeries

    companion object {
        const val BASE_URL: String = "https://api.twelvedata.com/"
    }
}