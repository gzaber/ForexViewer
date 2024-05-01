package com.gzaber.forexviewer.data.repository.forexdata.model


data class TimeSeriesMeta(
    val symbol: String,
    val interval: String,
    val base: String,
    val quote: String
)
