package com.gzaber.forexviewer.data.source.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkForexPairsList(
    val data: List<NetworkForexPair>,
    val count: Int,
    val status: String
)
