package com.gzaber.forexviewer.util.fake.repository

import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeries
import com.gzaber.forexviewer.util.emptyExchangeRate
import com.gzaber.forexviewer.util.emptyTimeSeries
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class FakeForexDataRepository(
    forexPairs: List<ForexPair> = listOf(),
    exchangeRate: ExchangeRate = emptyExchangeRate(),
    timeSeries: TimeSeries = emptyTimeSeries()
) : ForexDataRepository {

    private val _forexPairs = MutableStateFlow(forexPairs)
    private val _exchangeRate = MutableStateFlow(exchangeRate)
    private val _timeSeries = MutableStateFlow(timeSeries)
    private val _shouldThrowError = MutableStateFlow(false)

    private var _timeMillisDelay: Long = 0

    fun setShouldThrowError(value: Boolean) {
        _shouldThrowError.update { value }
    }

    fun setDelay(timeMillis: Long) {
        _timeMillisDelay = timeMillis
    }

    override fun fetchAllForexPairs(): Flow<List<ForexPair>> =
        combine(_forexPairs, _shouldThrowError) { forexPairs, shouldThrow ->
            if (shouldThrow) {
                throw Exception("failure")
            } else {
                delay(_timeMillisDelay)
                forexPairs
            }
        }

    override fun fetchExchangeRate(symbol: String): Flow<ExchangeRate> =
        combine(_exchangeRate, _shouldThrowError) { exchangeRate, shouldThrow ->
            if (shouldThrow) {
                throw Exception("failure")
            } else {
                delay(_timeMillisDelay)
                exchangeRate
            }
        }

    override fun fetchTimeSeries(
        symbol: String,
        interval: String,
        outputSize: Int
    ): Flow<TimeSeries> = combine(_timeSeries, _shouldThrowError) { timeSeries, shouldThrow ->
        if (shouldThrow) {
            throw Exception("failure")
        } else {
            delay(_timeMillisDelay)
            timeSeries
        }
    }
}