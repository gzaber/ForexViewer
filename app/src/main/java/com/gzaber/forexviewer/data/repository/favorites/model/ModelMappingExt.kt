package com.gzaber.forexviewer.data.repository.favorites.model

import com.gzaber.forexviewer.data.source.local.FavoriteEntity

fun Favorite.toEntity() = FavoriteEntity(id, symbol, base, quote)

fun FavoriteEntity.toModel() = Favorite(id, symbol, base, quote)