package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTimeSeries(
    val meta: NetworkTimeSeriesMeta,
    val values: List<NetworkTimeSeriesValue>,
    val status: String
)
