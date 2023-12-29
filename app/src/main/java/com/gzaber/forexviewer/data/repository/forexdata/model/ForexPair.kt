package com.gzaber.forexviewer.data.repository.forexdata.model

data class ForexPair(
    val symbol: String,
    val group: String,
    val base: String,
    val quote: String
)
