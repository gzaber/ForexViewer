package com.gzaber.forexviewer.ui.chart

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.gzaber.forexviewer.data.repository.favorites.FakeFavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.forexdata.FakeForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeries
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesMeta
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.chart.composable.TAG_FOREX_CHART
import com.gzaber.forexviewer.ui.navigation.ForexViewerDestinationArgs
import com.gzaber.forexviewer.ui.util.composable.TAG_LOADING_BOX
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ChartScreenTest {

    private lateinit var favoritesRepository: FakeFavoritesRepository
    private lateinit var forexDataRepository: FakeForexDataRepository
    private var isOnBackClicked: Boolean = false

    private val favorite = Favorite(1, "EUR/USD", "Euro", "US Dollar")
    private val timeSeriesMeta = TimeSeriesMeta(
        symbol = "EUR/USD",
        interval = "1h",
        base = "Euro",
        quote = "US Dollar",
        type = "Forex"
    )
    private val timeSeriesValue1 = TimeSeriesValue(
        datetime = "11:00",
        open = 1.0501,
        high = 1.1101,
        low = 1.0401,
        close = 1.1001
    )
    private val timeSeriesValue2 = TimeSeriesValue(
        datetime = "12:00",
        open = 1.1001,
        high = 1.1601,
        low = 1.0901,
        close = 1.1501
    )
    private val timeSeriesValues = listOf(
        timeSeriesValue1, timeSeriesValue2
    )
    private val timeSeries = TimeSeries(
        meta = timeSeriesMeta,
        values = timeSeriesValues
    )

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private fun setupChartScreen(timeMillisDelay: Long = 0) {
        favoritesRepository =
            FakeFavoritesRepository(initialFavorites = listOf(favorite))
        forexDataRepository =
            FakeForexDataRepository(timeSeries = timeSeries)
        val savedStateHandle =
            SavedStateHandle(mapOf(ForexViewerDestinationArgs.SYMBOL_ARG to "EUR_USD"))

        forexDataRepository.setDelay(timeMillisDelay)

        composeTestRule.setContent {
            ChartScreen(
                onBackClick = { isOnBackClicked = true },
                viewModel = ChartViewModel(
                    favoritesRepository = favoritesRepository,
                    forexDataRepository = forexDataRepository,
                    savedStateHandle = savedStateHandle
                )
            )
        }
    }

    @Test
    fun appBarTitle_isDisplayed() {
        setupChartScreen()

        composeTestRule.onNodeWithText("EUR/USD").assertIsDisplayed()
    }

    @Test
    fun onBackClick_isInvoked_whenClicked() {
        setupChartScreen()

        assert(isOnBackClicked.not())
        composeTestRule.onNodeWithContentDescription("Navigate back").assertIsDisplayed()
            .performClick()
        assert(isOnBackClicked)
    }

    @Test
    fun loadingBox_isDisplayed_whenDataIsLoading() {
        setupChartScreen(100)

        composeTestRule.onNodeWithTag(TAG_LOADING_BOX).assertIsDisplayed()
    }

    @Test
    fun snackbar_showsMessage_whenFavoritesRepositoryFlowErrorOccured() {
        setupChartScreen()
        favoritesRepository.setShouldThrowFlowError(true)

        composeTestRule.onNodeWithText("failure", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun snackbar_showsMessage_whenErrorOccuredTogglingFavorite() {
        setupChartScreen()
        favoritesRepository.setShouldThrowAsyncError(true)

        with(composeTestRule) {
            onNodeWithContentDescription("Favorite").assertIsDisplayed().performClick()
            onNodeWithText("failure", useUnmergedTree = true).assertIsDisplayed()
        }
    }

    @Test
    fun snackbar_showsMessage_whenForexDataRepositoryErrorOccured() {
        setupChartScreen()
        forexDataRepository.setShouldThrowError(true)

        composeTestRule.onNodeWithText("failure", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun forexPairNameAndExchangeRate_areDisplayed() {
        setupChartScreen()
        with(composeTestRule) {
            onNodeWithText("Euro / US Dollar").assertIsDisplayed()
            onNodeWithText("1.1001").assertIsDisplayed()
        }
    }

    @Test
    fun chartTypeMenu_valuesAreDisplayed() {
        setupChartScreen()
        with(composeTestRule) {
            onNodeWithText("CANDLE").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("BAR").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("LINE").assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun chartTimeframeMenu_valuesAreDisplayed() {
        setupChartScreen()
        with(composeTestRule) {
            onNodeWithText("M5").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("M15").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("H1").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("H4").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("D1").assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun forexChart_composableIsDisplayed() {
        setupChartScreen()
        composeTestRule.onNodeWithTag(TAG_FOREX_CHART).assertIsDisplayed()
    }
}