package com.gzaber.forexviewer.data.repository.forexdata

import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.data.repository.forexdata.model.Quote
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import kotlinx.coroutines.flow.Flow

interface ForexDataRepository {
    fun fetchForexPairs(): Flow<List<ForexPair>>
    fun fetchExchangeRate(symbol: String): Flow<ExchangeRate>
    fun fetchQuote(symbol: String, interval: String): Flow<Quote>
    fun fetchTimeSeries(
        symbol: String,
        interval: String,
        outputSize: Int
    ): Flow<List<TimeSeriesValue>>
}