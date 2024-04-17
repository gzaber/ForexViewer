package com.gzaber.forexviewer.ui.forexpairs.composable

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
class ForexPairsAppBarTest {

    private var isNavigateBackClicked: Boolean = false

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupForexPairsAppBar() {
        composeTestRule.setContent {
            ForexPairsAppBar(onBackClick = { isNavigateBackClicked = true })
        }
    }

    @Test
    fun title_isDisplayed() {
        composeTestRule.onNodeWithText("Forex pairs").assertIsDisplayed()
    }

    @Test
    fun onBackClick_invokesWhenClicked() {
        assert(!isNavigateBackClicked)
        composeTestRule.onNodeWithContentDescription("Navigate back").assertIsDisplayed()
            .assertHasClickAction().performClick()
        assert(isNavigateBackClicked)
    }
}