package com.gzaber.forexviewer.ui.forexpairs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.gzaber.forexviewer.data.repository.favorites.FakeFavoritesRepository
import com.gzaber.forexviewer.data.repository.favorites.model.Favorite
import com.gzaber.forexviewer.data.repository.forexdata.FakeForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.ui.util.composable.TAG_LOADING_BOX
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForexPairsScreenTest {

    private lateinit var favoritesRepository: FakeFavoritesRepository
    private lateinit var forexDataRepository: FakeForexDataRepository

    private var isOnBackClicked: Boolean = false

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

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private fun setupForexPairsScreen(timeMillisDelay: Long = 0) {
        favoritesRepository =
            FakeFavoritesRepository(initialFavorites = listOf(favorite))
        forexDataRepository =
            FakeForexDataRepository(forexPairs = listOf(majorForexPair, minorForexPair))

        forexDataRepository.setDelay(timeMillisDelay)

        composeTestRule.setContent {
            ForexPairsScreen(
                onBackClick = { isOnBackClicked = true },
                onListItemClick = {},
                viewModel = ForexPairsViewModel(
                    favoritesRepository = favoritesRepository,
                    forexDataRepository = forexDataRepository
                )
            )
        }
    }

    @Test
    fun appBarTitle_isDisplayed() {
        setupForexPairsScreen()

        composeTestRule.onNodeWithText("Forex pairs").assertIsDisplayed()
    }

    @Test
    fun onBackClick_isInvoked_whenClicked() {
        setupForexPairsScreen()

        assert(isOnBackClicked.not())
        composeTestRule.onNodeWithContentDescription("Navigate back").assertIsDisplayed()
            .performClick()
        assert(isOnBackClicked)
    }

    @Test
    fun loadingBox_isDisplayed_whenDataIsLoading() {
        setupForexPairsScreen(100)

        composeTestRule.onNodeWithTag(TAG_LOADING_BOX).assertIsDisplayed()
    }

    @Test
    fun snackbar_showsMessage_whenFavoritesRepositoryFlowErrorOccured() {
        setupForexPairsScreen()
        favoritesRepository.setShouldThrowFlowError(true)

        composeTestRule.onNodeWithText("failure", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun snackbar_showsMessage_whenErrorOccuredTogglingFavorite() {
        setupForexPairsScreen()
        favoritesRepository.setShouldThrowAsyncError(true)

        with(composeTestRule) {
            onNodeWithContentDescription("Favorite").assertIsDisplayed().performClick()
            onNodeWithText("failure", useUnmergedTree = true).assertIsDisplayed()
        }
    }

    @Test
    fun snackbar_showsMessage_whenForexDataRepositoryErrorOccured() {
        setupForexPairsScreen()
        forexDataRepository.setShouldThrowError(true)

        composeTestRule.onNodeWithText("failure", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun defaultMajorGroupIsSelected_majorPairIsDisplayed() {
        setupForexPairsScreen()

        with(composeTestRule) {
            onNodeWithText("EUR/USD").assertIsDisplayed()
            onNodeWithText("AUD/CAD").assertDoesNotExist()
        }
    }

    @Test
    fun allGroupIsSelected_allPairsAreDisplayed() {
        setupForexPairsScreen()

        with(composeTestRule) {
            onNodeWithText("ALL").assertIsDisplayed().performClick()
            onNodeWithText("EUR/USD").assertIsDisplayed()
            onNodeWithText("AUD/CAD").assertIsDisplayed()
        }
    }

    @Test
    fun minorGroupIsSelected_minorPairIsDisplayed() {
        setupForexPairsScreen()

        with(composeTestRule) {
            onNodeWithText("MINOR").assertIsDisplayed().performClick()
            onNodeWithText("AUD/CAD").assertIsDisplayed()
            onNodeWithText("EUR/USD").assertIsNotDisplayed()
        }
    }

    @Test
    fun allGroupIsSelected_minorPairIsSearched_minorPairIsDisplayed() {
        setupForexPairsScreen()

        with(composeTestRule) {
            onNodeWithText("ALL").assertIsDisplayed().performClick()
            onNodeWithText("Search").performTextInput("AUD")

            onNodeWithText("AUD/CAD").assertIsDisplayed()
            onNodeWithText("EUR/USD").assertIsNotDisplayed()
        }
    }
}