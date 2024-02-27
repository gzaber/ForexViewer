package com.gzaber.forexviewer.data.repository.forexdata.model


data class TimeSeries(
    val meta: TimeSeriesMeta,
    val values: List<TimeSeriesValue>
)
