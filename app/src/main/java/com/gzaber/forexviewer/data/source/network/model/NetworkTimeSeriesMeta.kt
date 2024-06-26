package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTimeSeriesMeta(
    val symbol: String,
    val interval: String,
    @SerialName(value = "currency_base")
    val base: String,
    @SerialName(value = "currency_quote")
    val quote: String,
    val type: String,
    @SerialName(value = "scale_factor")
    val scaleFactor: Int? = null
)
