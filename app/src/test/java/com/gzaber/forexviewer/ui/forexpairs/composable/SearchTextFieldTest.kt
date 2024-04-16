package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchTextFieldTest {

    private var searchText: String = ""

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun searchText_placeholderIsDisplayed() {
        with(composeTestRule) {
            setContent {
                SearchTextField(
                    searchText = searchText,
                    onSearchTextChange = { },
                    onClearSearchText = { })
            }

            onNodeWithText("Search").assertIsDisplayed()
        }
    }

    @Test
    fun searchText_iconsAreDisplayed() {
        with(composeTestRule) {
            setContent {
                SearchTextField(
                    searchText = searchText,
                    onSearchTextChange = { },
                    onClearSearchText = { })
            }

            onNodeWithContentDescription("Search").assertIsDisplayed()
            onNodeWithContentDescription("Clear search text").assertIsDisplayed()
        }
    }

    @Test
    fun onSearchTextChange_searchTextIsChanged() {
        searchText = "USD"
        with(composeTestRule) {
            setContent {
                SearchTextField(
                    searchText = searchText,
                    onSearchTextChange = { searchText = it },
                    onClearSearchText = { })
            }

            assert(searchText == "USD")
            onNodeWithText("USD").assertIsDisplayed().performTextInput("GBP/")
            assert(searchText == "GBP/USD")
        }
    }

    @Test
    fun onClearSearchText_searchTextIsCleared() {
        searchText = "USD"
        with(composeTestRule) {
            setContent {
                SearchTextField(
                    searchText = searchText,
                    onSearchTextChange = { },
                    onClearSearchText = { searchText = "" })
            }

            assert(searchText == "USD")
            onNodeWithContentDescription("Clear search text").assertIsDisplayed().performClick()
            assert(searchText == "")
        }
    }
}