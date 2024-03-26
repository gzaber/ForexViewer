package com.gzaber.forexviewer.data.repository.favorites

import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.favorites.model.toEntity
import com.gzaber.forexviewer.data.repository.favorites.model.toModel
import com.gzaber.forexviewer.data.source.local.FakeFavoriteDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class DefaultFavoritesRepositoryTest {

    private val favorite1 = Favorite(1, "EUR/USD", "Euro", "US Dollar")
    private val favorite2 = Favorite(2, "GBP/USD", "British Pound", "US Dollar")
    private val favoriteEntities = listOf(favorite1.toEntity(), favorite2.toEntity())
    private val newFavorite = Favorite(null, "USD/PLN", "US Dollar", "Polish Zloty")

    private lateinit var localDataSource: FakeFavoriteDao
    private lateinit var repository: DefaultFavoritesRepository

    @Before
    fun createRepository() {
        localDataSource = FakeFavoriteDao(favoriteEntities)
        repository = DefaultFavoritesRepository(favoritesLocalDataSource = localDataSource)
    }

    @Test
    fun insertFavorite_savesToLocalDataSource() = runTest {
        repository.insertFavorite(newFavorite)
        assert(
            localDataSource.favorites == listOf(
                favorite1.toEntity(),
                favorite2.toEntity(),
                newFavorite.toEntity()
            )
        )
    }

    @Test
    fun deleteFavorite_removesFromLocalDataSource() = runTest {
        repository.deleteFavorite(favorite1)
        assert(localDataSource.favorites == listOf(favorite2.toEntity()))
    }

    @Test
    fun loadFavoriteBySymbol_emitsFavoriteFromLocalDataSource() = runTest {
        val result = repository.loadFavoriteBySymbol(favorite1.symbol).first()
        assert(result?.equals(favorite1) == true)
    }

    @Test
    fun loadFavoriteBySymbol_notFoundInLocalDataSource_emitsNull() = runTest {
        val result = repository.loadFavoriteBySymbol("CHF/PLN").first()
        assert(result == null)
    }

    @Test
    fun loadAllFavorites_emitsFavoritesFromLocalDataSource() = runTest {
        val result = repository.loadAllFavorites().first()
        assert(result == favoriteEntities.map { it.toModel() })
    }

    @Test
    fun loadAllFavorites_notFoundInLocalDataSource_emitsEmptyList() = runTest {
        repository.deleteFavorite(favorite1)
        repository.deleteFavorite(favorite2)
        val result = repository.loadAllFavorites().first()
        assert(result == emptyList<Favorite>())
    }
}


