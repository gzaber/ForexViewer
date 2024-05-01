package com.gzaber.forexviewer.ui.chart

import androidx.lifecycle.SavedStateHandle
import com.gzaber.forexviewer.util.fake.repository.FakeFavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.util.fake.repository.FakeForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeries
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesMeta
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.navigation.ForexViewerDestinationArgs
import com.gzaber.forexviewer.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChartViewModelTest {

    private lateinit var viewModel: ChartViewModel
    private lateinit var favoritesRepository: FakeFavoritesRepository
    private lateinit var forexDataRepository: FakeForexDataRepository

    private val favorite = Favorite(1, "EUR/USD", "Euro", "US Dollar")
    private val timeSeriesMeta = TimeSeriesMeta(
        symbol = "EUR/USD",
        interval = "1h",
        base = "Euro",
        quote = "US Dollar"
    )
    private val timeSeriesValue1 = TimeSeriesValue(
        datetime = "11:00",
        open = 1.05,
        high = 1.11,
        low = 1.04,
        close = 1.10
    )
    private val timeSeriesValue2 = TimeSeriesValue(
        datetime = "12:00",
        open = 1.10,
        high = 1.16,
        low = 1.09,
        close = 1.15
    )
    private val timeSeriesValues = listOf(
        timeSeriesValue1, timeSeriesValue2
    )
    private val timeSeries = TimeSeries(
        meta = timeSeriesMeta,
        values = timeSeriesValues
    )

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setupRepositories() {
        favoritesRepository =
            FakeFavoritesRepository(initialFavorites = listOf(favorite))
        forexDataRepository =
            FakeForexDataRepository(timeSeries = timeSeries)
    }

    private fun setupViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                ForexViewerDestinationArgs.SYMBOL_ARG to "EUR_USD"
            )
        )
    ) {
        viewModel = ChartViewModel(
            favoritesRepository = favoritesRepository,
            forexDataRepository = forexDataRepository,
            savedStateHandle = savedStateHandle
        )
    }


    @Test
    fun init_savedStateHandleInitialStateIsEmpty_timeSeriesAreNotCollected() = runTest {
        setupViewModel(savedStateHandle = SavedStateHandle())

        val uiState = viewModel.uiState.value
        assert(uiState.symbol == "")
        assert(uiState.isLoading)
        assert(uiState.timeSeriesValues.isEmpty())
    }

    @Test
    fun init_collectsTimeSeries() = runTest {
        setupViewModel()

        val uiState = viewModel.uiState.value
        assert(uiState.symbol == "EUR/USD")
        assert(uiState.isLoading.not())
        assert(uiState.timeSeriesValues.size == 2)
        assert(uiState.timeSeriesValues == timeSeriesValues)
    }

    @Test
    fun init_collectsFavoriteBySymbol() = runTest {
        setupViewModel()

        val uiState = viewModel.uiState.value
        assert(uiState.uiForexPair.isFavorite)
        assert(uiState.uiForexPair.favoriteId == 1)
    }

    @Test
    fun init_forexDataRepositoryThrowsException_emitsFailureMessage() = runTest {
        setupViewModel()
        forexDataRepository.setShouldThrowError(true)

        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun init_favoritesRepositoryThrowsException_emitsFailureMessage() = runTest {
        setupViewModel()
        favoritesRepository.setShouldThrowFlowError(true)

        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun toggleFavorite_emitsUpdatedUiForexPair() = runTest {
        setupViewModel()

        assert(viewModel.uiState.value.uiForexPair.isFavorite)
        viewModel.toggleFavorite(viewModel.uiState.value.uiForexPair)
        assert(viewModel.uiState.value.uiForexPair.isFavorite.not())
        viewModel.toggleFavorite(viewModel.uiState.value.uiForexPair)
        assert(viewModel.uiState.value.uiForexPair.isFavorite)
    }

    @Test
    fun toggleFavorite_favoritesRepositoryThrowsException_emitsFailureMessage() = runTest {
        setupViewModel()
        favoritesRepository.setShouldThrowAsyncError(true)

        viewModel.toggleFavorite(viewModel.uiState.value.uiForexPair)
        assert(viewModel.uiState.value.failureMessage == "failure")
    }

    @Test
    fun onTypeChanged_emitsUpdatedType() {
        setupViewModel()

        viewModel.onTypeChanged(ChartType.LINE)
        assert(viewModel.uiState.value.chartType == ChartType.LINE)
    }

    @Test
    fun onTimeframeChanged_emitsUpdatedTimeframe() {
        setupViewModel()

        viewModel.onTimeframeChanged(ChartTimeframe.D1)
        assert(viewModel.uiState.value.chartTimeframe == ChartTimeframe.D1)
    }

    @Test
    fun snackbarMessageShown_emitsResetFailureMessage() = runTest {
        setupViewModel()
        favoritesRepository.setShouldThrowFlowError(true)

        assert(viewModel.uiState.value.failureMessage == "failure")
        viewModel.snackbarMessageShown()
        assert(viewModel.uiState.value.failureMessage == null)
    }
}