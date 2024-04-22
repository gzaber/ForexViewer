package com.gzaber.forexviewer.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.forexviewer.util.fake.repository.FakeApiKeyRepository
import com.gzaber.forexviewer.util.fake.repository.FakeFavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.util.fake.repository.FakeForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ExchangeRate
import com.gzaber.forexviewer.ui.home.composable.TAG_API_KEY_DIALOG
import com.gzaber.forexviewer.ui.util.composable.TAG_LOADING_BOX
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeScreenTest {

    private lateinit var apiKeyRepository: FakeApiKeyRepository
    private lateinit var favoritesRepository: FakeFavoritesRepository
    private lateinit var forexDataRepository: FakeForexDataRepository

    private val favorite = Favorite(1, "EUR/USD", "Euro", "US Dollar")
    private val exchangeRate = ExchangeRate("EUR/USD", 1.1021)

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private fun setupHomeScreen(timeMillisDelay: Long = 0) {
        apiKeyRepository = FakeApiKeyRepository()
        favoritesRepository =
            FakeFavoritesRepository(initialFavorites = listOf(favorite))
        forexDataRepository =
            FakeForexDataRepository(exchangeRate = exchangeRate)

        favoritesRepository.setDelay(timeMillisDelay)

        composeTestRule.setContent {
            HomeScreen(
                onForexPairsClick = {},
                onListItemClick = {},
                viewModel = HomeViewModel(
                    apiKeyRepository = apiKeyRepository,
                    favoritesRepository = favoritesRepository,
                    forexDataRepository = forexDataRepository
                )
            )
        }
    }

    @Test
    fun appBarTitle_isDisplayed() {
        setupHomeScreen()

        composeTestRule.onNodeWithText("ForexViewer").assertIsDisplayed()
    }

    @Test
    fun loadingBox_isDisplayed_whenDataIsLoading() {
        setupHomeScreen(300)

        composeTestRule.onNodeWithTag(TAG_LOADING_BOX).assertIsDisplayed()
    }

    @Test
    fun snackbar_showsMessage_whenFavoritesRepositoryFlowErrorOccured() {
        setupHomeScreen()
        favoritesRepository.setShouldThrowFlowError(true)

        composeTestRule.onNodeWithText("failure", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun snackbar_showsMessage_whenForexDataRepositoryErrorOccured() {
        setupHomeScreen()
        forexDataRepository.setShouldThrowError(true)

        composeTestRule.onNodeWithText("failure", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun favorite_isDisplayed() {
        setupHomeScreen()

        composeTestRule.onNodeWithText("EUR/USD").assertIsDisplayed()
    }

    @Test
    fun onApiKeyClick_invoked_dialogIsDisplayed() {
        setupHomeScreen()

        with(composeTestRule) {
            onNodeWithContentDescription("Update api key").assertIsDisplayed().performClick()
            onNodeWithTag(TAG_API_KEY_DIALOG).assertIsDisplayed()
        }
    }
}