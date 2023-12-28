package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkExchangeRate(
    val symbol: String,
    val rate: Double,
    val timestamp: Long
)
