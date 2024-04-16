package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForexPairsListTest {

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupForexPairsList() {
        composeTestRule.setContent {
            ForexPairsList(forexPairs = listOf(
                UiForexPair(
                    symbol = "EUR/USD",
                    group = "Major",
                    base = "Euro",
                    quote = "US Dollar",
                    isFavorite = true
                ),
                UiForexPair(
                    symbol = "GBP/PLN",
                    group = "Minor",
                    base = "British Pound",
                    quote = "Polish Zloty",
                    isFavorite = false
                )
            ),
                onListItemClick = {},
                onFavoriteClick = {}
            )
        }
    }

    @Test
    fun favoriteForexPairListItem_isDisplayed() {
        with(composeTestRule) {
            onNodeWithText("EUR/USD").assertIsDisplayed()
            onNodeWithText("Euro / US Dollar").assertIsDisplayed()
            onNodeWithText("Major").assertIsDisplayed()
        }
    }

    @Test
    fun notFavoriteForexPairListItem_isDisplayed() {
        with(composeTestRule) {
            onNodeWithText("GBP/PLN").assertIsDisplayed()
            onNodeWithText("British Pound / Polish Zloty").assertIsDisplayed()
            onNodeWithText("Minor").assertIsDisplayed()
        }
    }
}