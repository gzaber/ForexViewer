package com.gzaber.forexviewer.data.repository.forexdata

import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.data.repository.forexdata.model.Quote
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.data.repository.forexdata.model.toModel
import com.gzaber.forexviewer.data.source.network.ForexDataApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultForexDataRepository @Inject constructor(
    private val forexDataNetworkDataSource: ForexDataApiService
) : ForexDataRepository {

    override fun fetchAllForexPairs(): Flow<List<ForexPair>> = flow {
        val allForexPairs = forexDataNetworkDataSource.fetchAllForexPairsList().data.map {
            it.toModel()
        }
        emit(allForexPairs)
    }

    override fun fetchForexPair(symbol: String): Flow<ForexPair> = flow {
        val forexPair = forexDataNetworkDataSource.fetchForexPair(symbol).data.first().toModel()
        emit(forexPair)
    }

    override fun fetchExchangeRate(symbol: String): Flow<ExchangeRate> = flow {
        val exchangeRate = forexDataNetworkDataSource.fetchExchangeRate(symbol).toModel()
        emit(exchangeRate)
    }

    override fun fetchQuote(symbol: String, interval: String): Flow<Quote> = flow {
        forexDataNetworkDataSource.fetchQuote(symbol, interval).toModel()
    }

    override fun fetchTimeSeries(
        symbol: String,
        interval: String,
        outputSize: Int
    ): Flow<List<TimeSeriesValue>> = flow {
        forexDataNetworkDataSource.fetchTimeSeries(
            symbol,
            interval,
            outputSize
        ).values.map { it.toModel() }
    }
}