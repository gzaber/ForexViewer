package com.gzaber.forexviewer.data.repository.favorites

import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.favorites.model.toEntity
import com.gzaber.forexviewer.data.repository.favorites.model.toModel
import com.gzaber.forexviewer.data.source.local.FavoriteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

class DefaultFavoritesRepository(
    private val favoritesLocalDataSource: FavoriteDao
) : FavoritesRepository {

    override suspend fun insertFavorite(favorite: Favorite) {
        return favoritesLocalDataSource.insert(favorite.toEntity())
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        return favoritesLocalDataSource.delete(favorite.toEntity())
    }

    override fun loadAllFavorites(): Flow<Result<List<Favorite>>> = flow {
        try {
            val favorites = favoritesLocalDataSource.loadAll().last().map { it.toModel() }
            emit(Result.success(favorites))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}