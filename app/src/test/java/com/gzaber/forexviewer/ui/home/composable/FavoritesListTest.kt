package com.gzaber.forexviewer.ui.home.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.util.model.UiFavorite
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavoritesListTest {

    private var onClickSymbol: String = ""

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupFavoritesList() {
        composeTestRule.setContent {
            FavoritesList(
                favorites = listOf(
                    UiFavorite(
                        symbol = "EUR/USD",
                        base = "Euro",
                        quote = "US Dollar",
                        exchangeRate = 1.1032
                    ),
                    UiFavorite(
                        symbol = "GBP/PLN",
                        base = "British Pound",
                        quote = "Polish Zloty",
                        exchangeRate = 0.0
                    )
                ),
                contentPadding = PaddingValues(16.dp),
                onListItemClick = { onClickSymbol = it }
            )
        }
    }

    @Test
    fun listItem_knownExchangeRate_dataIsDisplayed() {
        with(composeTestRule) {
            onNodeWithText("EUR/USD").assertIsDisplayed()
            onNodeWithText("Euro / US Dollar").assertIsDisplayed()
            onNodeWithText("1.1032").assertIsDisplayed()
        }
    }

    @Test
    fun listItem_unknownExchangeRate_dataIsDisplayed() {
        with(composeTestRule) {
            onNodeWithText("GBP/PLN").assertIsDisplayed()
            onNodeWithText("British Pound / Polish Zloty").assertIsDisplayed()
            onNodeWithText("0.0").assertIsDisplayed()
        }
    }

    @Test
    fun onListItemClick_returnsSymbol_whenClicked() {
        assert(onClickSymbol == "")
        composeTestRule.onNodeWithText("EUR/USD").assertIsDisplayed().performClick()
        assert(onClickSymbol == "EUR/USD")
    }
}