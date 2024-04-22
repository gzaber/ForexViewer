package com.gzaber.forexviewer.data.repository.forexdata

import com.gzaber.forexviewer.data.repository.forexdata.model.toModel
import com.gzaber.forexviewer.util.fake.source.FakeForexDataApiService
import com.gzaber.forexviewer.data.source.network.model.NetworkExchangeRate
import com.gzaber.forexviewer.data.source.network.model.NetworkForexPair
import com.gzaber.forexviewer.data.source.network.model.NetworkForexPairsList
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeries
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeriesMeta
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeriesValue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultForexDataRepositoryTest {

    private val networkForexPair1 = NetworkForexPair(
        symbol = "EUR/USD",
        group = "Major",
        base = "Euro",
        quote = "US Dollar"
    )
    private val networkForexPair2 = NetworkForexPair(
        symbol = "GBP/USD",
        group = "Major",
        base = "British Pound",
        quote = "US Dollar"
    )
    private val networkForexPairsList = NetworkForexPairsList(
        data = listOf(networkForexPair1, networkForexPair2),
        status = "ok"
    )

    private val networkExchangeRate = NetworkExchangeRate(
        symbol = "EUR/USD",
        rate = 1.10,
        timestamp = 123
    )

    private val networkTimeSeriesMeta = NetworkTimeSeriesMeta(
        symbol = "EUR/USD",
        interval = "1h",
        base = "Euro",
        quote = "US Dollar",
        type = "Forex"
    )
    private val networkTimeSeriesValues = listOf(
        NetworkTimeSeriesValue(
            datetime = "1",
            open = "1.05",
            high = "1.11",
            low = "1.04",
            close = "1.10"
        )
    )
    private val networkTimeSeries = NetworkTimeSeries(
        meta = networkTimeSeriesMeta,
        values = networkTimeSeriesValues,
        status = "ok"
    )

    private lateinit var networkDataSource: FakeForexDataApiService
    private lateinit var repository: DefaultForexDataRepository

    @Before
    fun setupRepository() {
        networkDataSource = FakeForexDataApiService(
            networkForexPairsList = networkForexPairsList,
            networkExchangeRate = networkExchangeRate,
            networkTimeSeries = networkTimeSeries
        )
        repository = DefaultForexDataRepository(forexDataNetworkDataSource = networkDataSource)
    }

    @Test
    fun fetchAllForexPairs_emitsFromNetworkDataSource() = runTest {
        val forexPairs = listOf(networkForexPair1.toModel(), networkForexPair2.toModel())
        val result = repository.fetchAllForexPairs().take(2)

        assert(result.first() == forexPairs)
        assert(result.last() == forexPairs)
    }

    @Test
    fun fetchExchangeRate_emitsFromNetworkDataSource() = runTest {
        val result = repository.fetchExchangeRate(networkExchangeRate.symbol).take(2)

        assert(result.first() == networkExchangeRate.toModel())
        assert(result.last() == networkExchangeRate.toModel())
    }

    @Test
    fun fetchTimeSeries_emitsFromNetworkDataSource() = runTest {
        val result = repository.fetchTimeSeries(
            networkTimeSeriesMeta.symbol,
            networkTimeSeriesMeta.interval,
            networkTimeSeriesValues.size
        ).take(2)

        assert(result.first() == networkTimeSeries.toModel())
        assert(result.last() == networkTimeSeries.toModel())
    }
}