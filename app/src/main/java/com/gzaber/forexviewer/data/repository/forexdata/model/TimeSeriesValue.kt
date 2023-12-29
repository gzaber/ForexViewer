package com.gzaber.forexviewer.data.repository.forexdata.model

data class TimeSeriesValue(
    val datetime: String,
    val open: String,
    val high: String,
    val low: String,
    val close: String
)
