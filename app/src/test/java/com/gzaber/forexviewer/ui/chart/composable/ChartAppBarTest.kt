package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class ChartAppBarTest {

    private var isNavigateBackClicked = false
    private var isFavoriteClicked = false

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()


    @Test
    fun title_isDisplayed() {
        with(composeTestRule) {
            setContent {
                ChartAppBar(
                    title = "title",
                    isFavorite = true,
                    onBackClick = {},
                    onFavoriteClick = {}
                )
            }

            onNodeWithText("title").assertIsDisplayed()
        }
    }

    @Test
    fun backButton_isDisplayed() {
        with(composeTestRule) {
            setContent {
                ChartAppBar(
                    title = "title",
                    isFavorite = true,
                    onBackClick = {},
                    onFavoriteClick = {}
                )
            }

            onNodeWithContentDescription("Navigate back")
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }

    @Test
    fun backButton_invokesOnClick_whenClicked() {
        with(composeTestRule) {
            setContent {
                ChartAppBar(
                    title = "title",
                    isFavorite = true,
                    onBackClick = { isNavigateBackClicked = true },
                    onFavoriteClick = {}
                )
            }

            assert(!isNavigateBackClicked)
            onNodeWithContentDescription("Navigate back").assertIsDisplayed().performClick()
            assert(isNavigateBackClicked)
        }
    }

    @Test
    fun favoriteButton_isEnabled() {
        with(composeTestRule) {
            setContent {
                ChartAppBar(
                    title = "title",
                    isFavorite = false,
                    onBackClick = {},
                    onFavoriteClick = {}
                )
            }

            onNodeWithContentDescription("Favorite")
                .assertIsDisplayed()
                .assertHasClickAction()
                .assertIsEnabled()
        }
    }

    @Test
    fun favoriteButton_isDisabled() {
        with(composeTestRule) {
            setContent {
                ChartAppBar(
                    title = "title",
                    isFavorite = true,
                    onBackClick = {},
                    onFavoriteClick = {},
                    isLoading = true
                )
            }

            onNodeWithContentDescription("Favorite")
                .assertIsDisplayed()
                .assertHasClickAction()
                .assertIsNotEnabled()
        }
    }

    @Test
    fun favoriteButton_invokesOnClick_whenClicked() {
        with(composeTestRule) {
            setContent {
                ChartAppBar(
                    title = "title",
                    isFavorite = true,
                    onBackClick = { },
                    onFavoriteClick = { isFavoriteClicked = true }
                )
            }

            assert(!isFavoriteClicked)
            onNodeWithContentDescription("Favorite").assertIsDisplayed().performClick()
            assert(isFavoriteClicked)
        }
    }
}