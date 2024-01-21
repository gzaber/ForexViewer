package com.gzaber.forexviewer.ui.util.model

data class UiFavorite(
    val symbol: String,
    val base: String,
    val quote: String,
    val exchangeRate: Double
)
