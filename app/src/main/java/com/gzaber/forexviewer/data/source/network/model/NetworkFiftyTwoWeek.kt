package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkFiftyTwoWeek(
    val low: String,
    val high: String,
    @SerialName(value = "low_change")
    val lowChange: String,
    @SerialName(value = "high_change")
    val highChange: String,
    @SerialName(value = "low_change_percent")
    val lowChangePercent: String,
    @SerialName(value = "high_change_percent")
    val highChangePercent: String,
    val range: String
)