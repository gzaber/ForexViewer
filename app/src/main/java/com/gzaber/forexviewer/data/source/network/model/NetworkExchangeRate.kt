package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkExchangeRate(
    val symbol: String,
    val rate: Double,
    val timestamp: Long,
    @SerialName(value = "scale_factor")
    val scaleFactor: Int? = null
)
