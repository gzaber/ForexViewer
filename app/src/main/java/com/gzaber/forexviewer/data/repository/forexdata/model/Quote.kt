package com.gzaber.forexviewer.data.repository.forexdata.model

data class Quote(
    val symbol: String,
    val name: String,
    val change: String,
    val percentChange: String
)
