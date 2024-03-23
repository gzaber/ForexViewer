package com.gzaber.forexviewer.data.source.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFavoriteDao(initialFavorites: List<FavoriteEntity> = listOf()) : FavoriteDao {

    private var _favorites: MutableList<FavoriteEntity> = mutableListOf()

    var favorites: List<FavoriteEntity>
        get() = _favorites.toList()
        set(newFavorites) {
            _favorites = newFavorites.toMutableList()
        }

    init {
        favorites = initialFavorites
    }

    override suspend fun insert(favorite: FavoriteEntity) {
        _favorites.add(favorite)
    }

    override suspend fun delete(favorite: FavoriteEntity) {
        _favorites.remove(favorite)
    }

    override fun loadBySymbol(symbol: String): Flow<FavoriteEntity?> = flow {
        emit(favorites.filter { it.symbol == symbol }.firstOrNull())
    }

    override fun loadAll(): Flow<List<FavoriteEntity>> = flow {
        emit(favorites)
    }
}