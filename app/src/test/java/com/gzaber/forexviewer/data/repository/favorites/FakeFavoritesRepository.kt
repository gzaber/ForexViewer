package com.gzaber.forexviewer.data.repository.favorites

import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class FakeFavoritesRepository(initialFavorites: List<Favorite> = listOf()) : FavoritesRepository {

    private var _favorites = MutableStateFlow(initialFavorites)
    private val _shouldThrowError = MutableStateFlow(false)

    fun setShouldThrowError(value: Boolean) {
        _shouldThrowError.update { value }
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        throw NotImplementedError()
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        throw NotImplementedError()
    }

    override fun loadFavoriteBySymbol(symbol: String): Flow<Favorite?> = flow {
        throw NotImplementedError()
    }

    override fun loadAllFavorites(): Flow<List<Favorite>> =
        combine(_favorites, _shouldThrowError) { favorites, shouldThrow ->
            if (shouldThrow) {
                throw Exception("failure")
            } else {
                favorites
            }
        }
}