package com.gzaber.forexviewer.data.repository.forexdata.model

import com.gzaber.forexviewer.data.source.network.model.NetworkExchangeRate
import com.gzaber.forexviewer.data.source.network.model.NetworkForexPair
import com.gzaber.forexviewer.data.source.network.model.NetworkQuote
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeries
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeriesMeta
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeriesValue

fun NetworkForexPair.toModel() = ForexPair(symbol, group, base, quote)

fun NetworkExchangeRate.toModel() = ExchangeRate(symbol, rate)

fun NetworkQuote.toModel() = Quote(symbol, name, change, percentChange)

fun NetworkTimeSeriesMeta.toModel() = TimeSeriesMeta(
    symbol, interval, base, quote, type
)

fun NetworkTimeSeriesValue.toModel() = TimeSeriesValue(
    datetime, open.toDouble(), high.toDouble(), low.toDouble(), close.toDouble()
)

fun NetworkTimeSeries.toModel() = TimeSeries(
    meta.toModel(), values.map { it.toModel() }
)