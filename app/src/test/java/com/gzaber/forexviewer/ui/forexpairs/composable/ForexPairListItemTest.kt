package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForexPairListItemTest {

    private var isClicked: Boolean = false
    private var isFavoriteClicked: Boolean = false

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private fun setupUiForexPair(isFavorite: Boolean = false) = UiForexPair(
        symbol = "EUR/USD",
        group = "Major",
        base = "Euro",
        quote = "US Dollar",
        isFavorite = isFavorite
    )

    @Test
    fun listItem_textIsDisplayed() {
        with(composeTestRule) {
            setContent {
                ForexPairListItem(
                    forexPair = setupUiForexPair(),
                    onClick = {},
                    onFavoriteClick = {}
                )
            }

            onNodeWithText("EUR/USD").assertIsDisplayed()
            onNodeWithText("Euro / US Dollar").assertIsDisplayed()
            onNodeWithText("Major").assertIsDisplayed()
        }
    }

    @Test
    fun onClick_invokesWhenClicked() {
        with(composeTestRule) {
            setContent {
                ForexPairListItem(
                    forexPair = setupUiForexPair(),
                    onClick = { isClicked = true },
                    onFavoriteClick = {}
                )
            }

            assert(!isClicked)
            onNodeWithText("EUR/USD").assertIsDisplayed().assertHasClickAction().performClick()
            assert(isClicked)
        }
    }

    @Test
    fun onFavoriteClick_invokesWhenClicked() {
        with(composeTestRule) {
            setContent {
                ForexPairListItem(
                    forexPair = setupUiForexPair(isFavorite = true),
                    onClick = {},
                    onFavoriteClick = { isFavoriteClicked = true }
                )
            }

            assert(!isFavoriteClicked)
            onNodeWithContentDescription("Favorite").assertIsDisplayed().assertHasClickAction()
                .performClick()
            assert(isFavoriteClicked)
        }
    }
}