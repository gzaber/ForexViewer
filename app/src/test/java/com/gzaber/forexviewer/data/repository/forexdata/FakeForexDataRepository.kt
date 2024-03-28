package com.gzaber.forexviewer.data.repository.forexdata

import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeries
import com.gzaber.forexviewer.util.emptyExchangeRate
import com.gzaber.forexviewer.util.emptyTimeSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class FakeForexDataRepository(
    private val forexPairs: List<ForexPair> = listOf(),
    exchangeRate: ExchangeRate = emptyExchangeRate(),
    private val timeSeries: TimeSeries = emptyTimeSeries()
) : ForexDataRepository {

    private val _forexPairs = MutableStateFlow(forexPairs)
    private val _exchangeRate = MutableStateFlow(exchangeRate)
    private val _shouldThrowError = MutableStateFlow(false)

    fun setShouldThrowError(value: Boolean) {
        _shouldThrowError.update { value }
    }

    override fun fetchAllForexPairs(): Flow<List<ForexPair>> =
        combine(_forexPairs, _shouldThrowError) { forexPairs, shouldThrow ->
            if (shouldThrow) {
                throw Exception("failure")
            } else {
                forexPairs
            }
        }

    override fun fetchExchangeRate(symbol: String): Flow<ExchangeRate> =
        combine(_exchangeRate, _shouldThrowError) { exchangeRate, shouldThrow ->
            if (shouldThrow) {
                throw Exception("failure")
            } else {
                exchangeRate
            }
        }

    override fun fetchTimeSeries(
        symbol: String,
        interval: String,
        outputSize: Int
    ): Flow<TimeSeries> = flow {
        emit(timeSeries)
    }
}