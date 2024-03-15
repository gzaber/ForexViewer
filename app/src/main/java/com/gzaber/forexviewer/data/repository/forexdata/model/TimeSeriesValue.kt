package com.gzaber.forexviewer.data.repository.forexdata.model

data class TimeSeriesValue(
    val datetime: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double
)
