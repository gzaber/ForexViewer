package com.gzaber.forexviewer.ui.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.forexviewer.MainActivity
import com.gzaber.forexviewer.data.repository.apikey.ApiKeyRepository
import com.gzaber.forexviewer.data.repository.apikey.FakeApiKeyRepository
import com.gzaber.forexviewer.data.repository.favorites.FakeFavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.forexdata.FakeForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeries
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesMeta
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.di.RepositoryModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class ForexViewerNavigationTest {

    private val favorite = Favorite(1, "EUR/USD", "Euro", "US Dollar")
    private val forexPair = ForexPair(
        symbol = "EUR/USD",
        group = "Major",
        base = "Euro",
        quote = "US Dollar"
    )
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

    @BindValue
    @JvmField
    val apiKeyRepository: ApiKeyRepository = FakeApiKeyRepository()

    @BindValue
    @JvmField
    val favoritesRepository: FavoritesRepository =
        FakeFavoritesRepository(initialFavorites = listOf(favorite))

    @BindValue
    @JvmField
    val forexDataRepository: ForexDataRepository =
        FakeForexDataRepository(forexPairs = listOf(forexPair), timeSeries = timeSeries)

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun homeScreen_forexPairsButtonIsClicked_navigatesToForexPairsScreen() = runTest {
        navigateFromHomeToForexPairs()
    }

    @Test
    fun homeScreen_forexPairListItemIsClicked_navigatesToChartScreen() {
        navigateFromHomeToChart()
    }

    @Test
    fun forexPairsScreen_backButtonIsClicked_navigatesToHomeScreen() {
        navigateFromHomeToForexPairs()
        with(composeTestRule) {
            onNodeWithContentDescription("Navigate back").assertIsDisplayed().performClick()
            onNodeWithText("ForexViewer").assertIsDisplayed()
        }
    }

    @Test
    fun forexPairsScreen_forexPairListItemIsClicked_navigatesToChartScreen() {
        navigateFromHomeToForexPairs()
        with(composeTestRule) {
            onNodeWithText("EUR/USD").assertIsDisplayed().performClick()
            onNodeWithText("CANDLE").assertIsDisplayed()
            onNodeWithText("H1").assertIsDisplayed()
        }
    }

    @Test
    fun chartScreen_backButtonIsClicked_navigatesToHomeScreen() {
        navigateFromHomeToChart()
        with(composeTestRule) {
            onNodeWithContentDescription("Navigate back").assertIsDisplayed().performClick()
            onNodeWithText("ForexViewer").assertIsDisplayed()
        }
    }

    @Test
    fun chartScreen_backButtonIsClicked_navigatesToForexPairsScreen() {
        navigateFromHomeToForexPairs()
        with(composeTestRule) {
            onNodeWithText("EUR/USD").assertIsDisplayed().performClick()
            onNodeWithText("CANDLE").assertIsDisplayed()
            onNodeWithText("H1").assertIsDisplayed()
            onNodeWithContentDescription("Navigate back").assertIsDisplayed()
                .performClick()
            onNodeWithText("Forex pairs").assertIsDisplayed()
        }
    }

    private fun navigateFromHomeToForexPairs() {
        with(composeTestRule) {
            onNodeWithText("ForexViewer").assertIsDisplayed()
            onNodeWithContentDescription("Navigate to forex pairs screen").assertIsDisplayed()
                .performClick()
            onNodeWithText("Forex pairs").assertIsDisplayed()
        }
    }

    private fun navigateFromHomeToChart() {
        with(composeTestRule) {
            onNodeWithText("ForexViewer").assertIsDisplayed()
            onNodeWithText("EUR/USD").assertIsDisplayed()
                .performClick()
            onNodeWithText("CANDLE").assertIsDisplayed()
            onNodeWithText("H1").assertIsDisplayed()
        }
    }
}