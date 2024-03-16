package com.gzaber.forexviewer.data.repository.forexdata

import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeries
import com.gzaber.forexviewer.data.repository.forexdata.model.toModel
import com.gzaber.forexviewer.data.source.network.ForexDataApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultForexDataRepository @Inject constructor(
    private val forexDataNetworkDataSource: ForexDataApiService
) : ForexDataRepository {

    override fun fetchAllForexPairs(): Flow<List<ForexPair>> = flow {
        while (true) {
            val allForexPairs = forexDataNetworkDataSource.fetchAllForexPairsList().data.map {
                it.toModel()
            }
            emit(allForexPairs)
            delay(REFRESH_INTERVALS_MS)
        }
    }

    override fun fetchExchangeRate(symbol: String): Flow<ExchangeRate> = flow {
        while (true) {
            val exchangeRate = forexDataNetworkDataSource.fetchExchangeRate(symbol).toModel()
            emit(exchangeRate)
            delay(REFRESH_INTERVALS_MS)
        }
    }

    override fun fetchTimeSeries(
        symbol: String,
        interval: String,
        outputSize: Int
    ): Flow<TimeSeries> = flow {
        while (true) {
            val timeSeries = forexDataNetworkDataSource.fetchTimeSeries(
                symbol,
                interval,
                outputSize
            ).toModel()
            emit(timeSeries)
            delay(REFRESH_INTERVALS_MS)
        }
    }

    companion object {
        const val REFRESH_INTERVALS_MS: Long = 60_000
    }
}