package com.gzaber.forexviewer.util.fake.source

import com.gzaber.forexviewer.data.source.network.ForexDataApiService
import com.gzaber.forexviewer.data.source.network.model.NetworkExchangeRate
import com.gzaber.forexviewer.data.source.network.model.NetworkForexPairsList
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeries

class FakeForexDataApiService(
    private val networkForexPairsList: NetworkForexPairsList,
    private val networkExchangeRate: NetworkExchangeRate,
    private val networkTimeSeries: NetworkTimeSeries
) : ForexDataApiService {

    override suspend fun fetchAllForexPairsList(): NetworkForexPairsList = networkForexPairsList

    override suspend fun fetchExchangeRate(symbol: String): NetworkExchangeRate =
        networkExchangeRate

    override suspend fun fetchTimeSeries(
        symbol: String,
        interval: String,
        outputSize: Int
    ): NetworkTimeSeries = networkTimeSeries
}