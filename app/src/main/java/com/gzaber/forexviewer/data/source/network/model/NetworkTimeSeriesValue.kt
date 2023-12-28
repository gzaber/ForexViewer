package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTimeSeriesValue(
    val datetime: String,
    val open: String,
    val high: String,
    val low: String,
    val close: String
)
