package com.gzaber.forexviewer.util

import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeries
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesMeta

fun emptyExchangeRate() = ExchangeRate(symbol = "", rate = 0.0)

fun emptyTimeSeriesMeta() =
    TimeSeriesMeta(symbol = "", interval = "", base = "", quote = "")

fun emptyTimeSeries() = TimeSeries(meta = emptyTimeSeriesMeta(), values = listOf())