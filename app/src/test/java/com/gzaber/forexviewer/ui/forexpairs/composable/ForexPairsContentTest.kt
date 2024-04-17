package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForexPairsContentTest {

    private var selectedGroup: String = "MAJOR"

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupForexPairsContent() {
        composeTestRule.setContent {
            ForexPairsContent(
                forexPairs = listOf(
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
                searchText = "",
                selectedGroup = selectedGroup,
                contentPadding = PaddingValues(0.dp),
                onListItemClick = {},
                onFavoriteClick = {},
                onForexGroupClick = { selectedGroup = it.name },
                onSearchTextChange = {},
                onClearSearchText = {}
            )
        }
    }

    @Test
    fun forexGroupsMenu_valuesAreDisplayed() {
        with(composeTestRule) {
            onNodeWithText("ALL").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("MAJOR").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("MINOR").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("EXOTIC").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("EXOTIC-CROSS").assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun forexGroupsMenu_onForexGroupClick_invokesWhenValueClicked() {
        assert(selectedGroup == "MAJOR")
        composeTestRule.onNodeWithText("MINOR").assertIsDisplayed().assertHasClickAction()
            .performClick()
        assert(selectedGroup == "MINOR")
    }

    @Test
    fun searchText_placeholderAndIconsAreDisplayed() {
        with(composeTestRule) {
            onNodeWithText("Search").assertIsDisplayed()
            onNodeWithContentDescription("Search").assertIsDisplayed()
            onNodeWithContentDescription("Clear search text").assertIsDisplayed()
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