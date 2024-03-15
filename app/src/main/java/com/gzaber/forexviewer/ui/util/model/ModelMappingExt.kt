package com.gzaber.forexviewer.ui.util.model

import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair

fun ForexPair.toUiModel(isFavorite: Boolean, favoriteId: Int?) = UiForexPair(
    symbol, group, base, quote, isFavorite, favoriteId
)

fun UiForexPair.toFavorite() = Favorite(id = favoriteId, symbol, base, quote)