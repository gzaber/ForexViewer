package com.gzaber.forexviewer.ui.forexpairs

import com.gzaber.forexviewer.data.repository.favorites.FakeFavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.forexdata.FakeForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ForexPairsViewModelTest {

    private lateinit var viewModel: ForexPairsViewModel
    private lateinit var favoritesRepository: FakeFavoritesRepository
    private lateinit var forexDataRepository: FakeForexDataRepository

    private val majorForexPair = ForexPair(
        symbol = "EUR/USD",
        group = "Major",
        base = "Euro",
        quote = "US Dollar"
    )
    private val minorForexPair = ForexPair(
        symbol = "AUD/CAD",
        group = "Minor",
        base = "Australian Dollar",
        quote = "Canadian Dollar"
    )
    private val favorite = Favorite(1, "EUR/USD", "Euro", "US Dollar")
    private val majorUiForexPair = UiForexPair(
        symbol = majorForexPair.symbol,
        group = majorForexPair.group,
        base = majorForexPair.base,
        quote = majorForexPair.quote,
        isFavorite = true,
        favoriteId = favorite.id
    )
    private val minorUiForexPair = UiForexPair(
        symbol = minorForexPair.symbol,
        group = minorForexPair.group,
        base = minorForexPair.base,
        quote = minorForexPair.quote,
        isFavorite = false,
        favoriteId = null
    )

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setupViewModel() {
        favoritesRepository =
            FakeFavoritesRepository(initialFavorites = listOf(favorite))
        forexDataRepository =
            FakeForexDataRepository(forexPairs = listOf(majorForexPair, minorForexPair))
        viewModel = ForexPairsViewModel(
            favoritesRepository = favoritesRepository,
            forexDataRepository = forexDataRepository
        )
    }

    @Test
    fun init_emitsMajorUiForexPair() = runTest {
        val state = viewModel.uiState.value
        assert(state.uiForexPairs.size == 1)
        assert(state.uiForexPairs.contains(majorUiForexPair))
    }

    @Test
    fun init_favoritesRepositoryThrowsException_emitsFailureMessage() = runTest {
        favoritesRepository.setShouldThrowFlowError(true)

        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun init_forexDataRepositoryThrowsException_emitsFailureMessage() = runTest {
        forexDataRepository.setShouldThrowError(true)

        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun toggleFavorite_emitsUpdatedUiForexPair() = runTest {
        viewModel.toggleFavorite(viewModel.uiState.value.uiForexPairs.first())
        assert(viewModel.uiState.value.uiForexPairs.first().isFavorite.not())
        viewModel.toggleFavorite(viewModel.uiState.value.uiForexPairs.first())
        assert(viewModel.uiState.value.uiForexPairs.first().isFavorite)
    }

    @Test
    fun toggleFavorite_favoritesRepositoryThrowsException_emitsFailureMessage() = runTest {
        favoritesRepository.setShouldThrowAsyncError(true)
        viewModel.toggleFavorite(viewModel.uiState.value.uiForexPairs.first())

        assert(viewModel.uiState.value.uiForexPairs.first().isFavorite)
        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun onForexGroupSet_emitsUpdatedForexGroupAndMinorUiForexPair() = runTest {
        viewModel.onForexGroupSet(ForexGroup.MINOR)

        assert(viewModel.uiState.value.group == ForexGroup.MINOR)
        assert(viewModel.uiState.value.uiForexPairs.contains(minorUiForexPair))
    }

    @Test
    fun onSearchTextChanged_emitsUpdatedSearchText() = runTest {
        viewModel.onSearchTextChanged("PLN")

        assert(viewModel.uiState.value.searchText == "PLN")
    }

    @Test
    fun onSearchTextCleared_emitsClearedSearchText() = runTest {
        viewModel.onSearchTextChanged("PLN")
        assert(viewModel.uiState.value.searchText == "PLN")

        viewModel.onSearchTextCleared()
        assert(viewModel.uiState.value.searchText == "")
    }

    @Test
    fun snackbarMessageShown_emitsResetFailureMessage() = runTest {
        favoritesRepository.setShouldThrowFlowError(true)

        assert(viewModel.uiState.value.failureMessage == "failure")
        viewModel.snackbarMessageShown()
        assert(viewModel.uiState.value.failureMessage == null)
    }
}