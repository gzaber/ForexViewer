package com.gzaber.forexviewer.ui.util.model

import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair

data class UiForexPair(
    val symbol: String = "",
    val group: String = "",
    val base: String = "",
    val quote: String = "",
    val isFavorite: Boolean = false,
    val favoriteId: Int? = null
) {
    fun toFavorite() = Favorite(id = favoriteId, symbol, base, quote)
}

fun ForexPair.toUiModel(isFavorite: Boolean, favoriteId: Int?) = UiForexPair(
    symbol, group, base, quote, isFavorite, favoriteId
)

