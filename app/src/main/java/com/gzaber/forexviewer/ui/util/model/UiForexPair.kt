package com.gzaber.forexviewer.ui.util.model


data class UiForexPair(
    val symbol: String = "",
    val group: String = "",
    val base: String = "",
    val quote: String = "",
    val isFavorite: Boolean = false,
    val favoriteId: Int? = null
)

