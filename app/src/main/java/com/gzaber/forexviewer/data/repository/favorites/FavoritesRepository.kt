package com.gzaber.forexviewer.data.repository.favorites

import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun deleteFavorite(favorite: Favorite)
    fun loadAllFavorites(): Flow<List<Favorite>>
}