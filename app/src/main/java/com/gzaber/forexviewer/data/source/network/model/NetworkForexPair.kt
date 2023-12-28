package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkForexPair(
    val symbol: String,
    @SerialName(value = "currency_group")
    val group: String,
    @SerialName(value = "currency_base")
    val base: String,
    @SerialName(value = "currency_quote")
    val quote: String
)
