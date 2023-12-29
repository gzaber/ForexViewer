package com.gzaber.forexviewer.data.repository.forexdata

import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.data.repository.forexdata.model.Quote
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.data.repository.forexdata.model.toModel
import com.gzaber.forexviewer.data.source.network.ForexDataApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultForexDataRepository(
    private val forexDataNetworkDataSource: ForexDataApiService
) : ForexDataRepository {

    override fun fetchForexPairs(): Flow<Result<List<ForexPair>>> = flow {
        try {
            val forexPairs =
                forexDataNetworkDataSource.fetchForexPairsList().data.map { it.toModel() }
            emit(Result.success(forexPairs))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun fetchExchangeRate(symbol: String): Flow<Result<ExchangeRate>> = flow {
        try {
            val exchangeRate = forexDataNetworkDataSource.fetchExchangeRate(symbol).toModel()
            emit(Result.success(exchangeRate))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun fetchQuote(symbol: String, interval: String): Flow<Result<Quote>> = flow {
        try {
            val quote = forexDataNetworkDataSource.fetchQuote(symbol, interval).toModel()
            emit(Result.success(quote))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun fetchTimeSeries(
        symbol: String,
        interval: String,
        outputSize: Int
    ): Flow<Result<List<TimeSeriesValue>>> = flow {
        try {
            val timeSeries = forexDataNetworkDataSource.fetchTimeSeries(
                symbol,
                interval,
                outputSize
            ).values.map { it.toModel() }
            emit(Result.success(timeSeries))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}