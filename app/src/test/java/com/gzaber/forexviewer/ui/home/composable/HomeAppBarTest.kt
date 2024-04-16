package com.gzaber.forexviewer.ui.home.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeAppBarTest {

    private var isApiKeyClicked: Boolean = false
    private var isForexPairsClicked: Boolean = false

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupHomeAppBar() {
        composeTestRule.setContent {
            HomeAppBar(
                onApiKeyClick = { isApiKeyClicked = true },
                onForexPairsClick = { isForexPairsClicked = true })
        }
    }

    @Test
    fun title_isDisplayed() {
        composeTestRule.onNodeWithText("ForexViewer").assertIsDisplayed()
    }

    @Test
    fun buttons_areDisplayed() {
        with(composeTestRule) {
            onNodeWithContentDescription("Update api key").assertIsDisplayed()
                .assertHasClickAction()
            onNodeWithContentDescription("Navigate to forex pairs screen").assertIsDisplayed()
                .assertHasClickAction()
        }
    }

    @Test
    fun onApiKeyClick_invokesWhenClicked() {
         assert(isApiKeyClicked.not())
        composeTestRule.onNodeWithContentDescription("Update api key").assertIsDisplayed()
            .performClick()
        assert(isApiKeyClicked)
    }

    @Test
    fun onForexPairsClick_invokesWhenClicked() {
        assert(isForexPairsClicked.not())
        composeTestRule.onNodeWithContentDescription("Navigate to forex pairs screen")
            .assertIsDisplayed().performClick()
        assert(isForexPairsClicked)
    }
}