package com.gzaber.forexviewer.data.repository.favorites

import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.favorites.model.toEntity
import com.gzaber.forexviewer.data.repository.favorites.model.toModel
import com.gzaber.forexviewer.data.source.local.FavoriteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultFavoritesRepository @Inject constructor(
    private val favoritesLocalDataSource: FavoriteDao
) : FavoritesRepository {

    override suspend fun insertFavorite(favorite: Favorite) =
        favoritesLocalDataSource.insert(favorite.toEntity())

    override suspend fun deleteFavorite(favorite: Favorite) =
        favoritesLocalDataSource.delete(favorite.toEntity())

    override fun loadAllFavorites(): Flow<List<Favorite>> =
        favoritesLocalDataSource.loadAll().map {
            it.map {
                it.toModel()
            }
        }
}