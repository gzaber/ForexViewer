package com.gzaber.forexviewer.ui.home.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.forexviewer.ui.util.model.UiFavorite
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavoriteListItemTest {

    private var onClickSymbol: String = ""

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupFavoriteListItem() {
        composeTestRule.setContent {
            FavoriteListItem(
                favorite = UiFavorite(
                    symbol = "EUR/USD",
                    base = "Euro",
                    quote = "US Dollar",
                    exchangeRate = 1.1032
                ),
                onClick = { onClickSymbol = it }
            )
        }
    }

    @Test
    fun listItem_symbolAndPairNameAndExchangeRate_areDisplayed() {
        with(composeTestRule) {
            onNodeWithText("EUR/USD").assertIsDisplayed()
            onNodeWithText("Euro / US Dollar").assertIsDisplayed()
            onNodeWithText("1.1032").assertIsDisplayed()
        }
    }

    @Test
    fun onClick_returnsSymbol_whenClicked() {
        assert(onClickSymbol == "")
        composeTestRule.onNodeWithText("EUR/USD").assertIsDisplayed().performClick()
        assert(onClickSymbol == "EUR/USD")
    }
}