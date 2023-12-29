package com.gzaber.forexviewer.data.repository.forexdata.model

import com.gzaber.forexviewer.data.source.network.model.NetworkExchangeRate
import com.gzaber.forexviewer.data.source.network.model.NetworkForexPair
import com.gzaber.forexviewer.data.source.network.model.NetworkQuote
import com.gzaber.forexviewer.data.source.network.model.NetworkTimeSeriesValue

fun NetworkForexPair.toModel() = ForexPair(symbol, group, base, quote)

fun NetworkExchangeRate.toModel() = ExchangeRate(symbol, rate)

fun NetworkQuote.toModel() = Quote(symbol, name, change, percentChange)

fun NetworkTimeSeriesValue.toModel() = TimeSeriesValue(datetime, open, high, low, close)