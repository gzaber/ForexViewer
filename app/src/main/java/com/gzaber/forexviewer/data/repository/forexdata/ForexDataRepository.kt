package com.gzaber.forexviewer.data.repository.forexdata

import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeries
import kotlinx.coroutines.flow.Flow

interface ForexDataRepository {
    fun fetchAllForexPairs(): Flow<List<ForexPair>>

    fun fetchExchangeRate(symbol: String): Flow<ExchangeRate>

    fun fetchTimeSeries(
        symbol: String,
        interval: String,
        outputSize: Int
    ): Flow<TimeSeries>
}