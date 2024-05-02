package com.gzaber.forexviewer.ui.home

import com.gzaber.forexviewer.util.fake.repository.FakeApiKeyRepository
import com.gzaber.forexviewer.util.fake.repository.FakeFavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.util.fake.repository.FakeForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var apiKeyRepository: FakeApiKeyRepository
    private lateinit var favoritesRepository: FakeFavoritesRepository
    private lateinit var forexDataRepository: FakeForexDataRepository

    private val favorite = Favorite(1, "EUR/USD", "Euro", "US Dollar")
    private val exchangeRate = ExchangeRate("EUR/USD", 1.1021)

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setupViewModel() {
        apiKeyRepository = FakeApiKeyRepository()
        favoritesRepository =
            FakeFavoritesRepository(initialFavorites = listOf(favorite))
        forexDataRepository = FakeForexDataRepository(exchangeRate = exchangeRate)
        viewModel = HomeViewModel(
            apiKeyRepository = apiKeyRepository,
            favoritesRepository = favoritesRepository,
            forexDataRepository = forexDataRepository
        )
    }

    @Test
    fun initialState_emitsCorrectInitialState() = runTest {
        val result = viewModel.uiState.value
        assert(result == HomeUiState())
    }

    @Test
    fun init_emitsApiKey() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        assert(viewModel.uiState.value.apiKeyText == "demo")
    }

    @Test
    fun init_apiKeyRepositoryThrowsException_emitsFailureMessage() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        apiKeyRepository.setShouldThrowError(true)
        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun init_emitsUiFavorites() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        assert(viewModel.uiState.value.uiFavorites.size == 1)
        assert(viewModel.uiState.value.uiFavorites.first().symbol == favorite.symbol)
        assert(viewModel.uiState.value.uiFavorites.first().exchangeRate == exchangeRate.rate)
    }

    @Test
    fun init_emptyFavoritesListCollected_emitsEmptyUiFavoritesList() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        favoritesRepository.clearFavorites()
        assert(viewModel.uiState.value.uiFavorites.isEmpty())
    }

    @Test
    fun init_favoritesRepositoryThrowsException_emitsFailureMessage() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        favoritesRepository.setShouldThrowFlowError(true)
        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun init_forexDataRepositoryThrowsException_emitsFailureMessage() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        forexDataRepository.setShouldThrowError(true)
        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun toggleShowingDialog_emitsToggledBooleanVariable() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        assert(!viewModel.uiState.value.showDialog)
        viewModel.toggleShowingDialog()
        assert(viewModel.uiState.value.showDialog)
    }

    @Test
    fun onApiKeyTextChanged_emitsChangedApiKeyText() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        assert(viewModel.uiState.value.apiKeyText == "demo")
        viewModel.onApiKeyTextChanged("changed")
        assert(viewModel.uiState.value.apiKeyText == "changed")
    }

    @Test
    fun saveApiKey_savesApiKey() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        viewModel.saveApiKey("newapikey")
        assert(viewModel.uiState.value.apiKeyText == "newapikey")
    }

    @Test
    fun saveApiKey_apiKeyRepositoryThrowsException_emitsFailureMessage() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        apiKeyRepository.setShouldThrowError(true)
        viewModel.saveApiKey("newapikey")
        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun snackbarMessageShown_emitsResetFailureMessage() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        apiKeyRepository.setShouldThrowError(true)
        viewModel.saveApiKey("newapikey")
        assert(viewModel.uiState.value.failureMessage == "failure")

        viewModel.snackbarMessageShown()
        assert(viewModel.uiState.value.failureMessage == null)
    }
}