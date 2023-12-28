package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkQuote(
    val symbol: String,
    val name: String,
    val exchange: String,
    val datetime: String,
    val timestamp: Long,
    val open: String,
    val high: String,
    val low: String,
    val close: String,
    @SerialName(value = "previous_close")
    val previousClose: String,
    val change: String,
    @SerialName(value = "percent_change")
    val percentChange: String,
    @SerialName(value = "average_volume")
    val averageVolume: String,
    @SerialName(value = "is_market_open")
    val isMarketOpen: Boolean,
    @SerialName(value = "fifty_two_week")
    val fiftyTwoWeek: NetworkFiftyTwoWeek
)
