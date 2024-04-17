package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForexGroupsMenuTest {

    private val menuValues: List<String> = listOf("ALL", "MAJOR", "MINOR", "EXOTIC", "EXOTIC_CROSS")
    private var selectedValue: String = menuValues.first()

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupForexGroupsMenu() {
        composeTestRule.setContent {
            ForexGroupsMenu(
                values = menuValues,
                selectedValue = selectedValue,
                onValueClick = { selectedValue = it })
        }
    }

    @Test
    fun menuValues_areDisplayed() {
        with(composeTestRule) {
            onNodeWithText("ALL").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("MAJOR").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("MINOR").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("EXOTIC").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("EXOTIC-CROSS").assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun onValueClick_updatesValue_whenClicked() {
        with(composeTestRule) {
            assert(selectedValue == "ALL")
            onNodeWithText("MAJOR").assertIsDisplayed().performClick()
            assert(selectedValue == "MAJOR")
        }
    }
}