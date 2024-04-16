package com.gzaber.forexviewer.ui.chart.composable

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
class ChartHorizontalMenuTest {

    private val menuValues: List<String> = listOf(
        "CANDLE", "BAR", "LINE"
    )
    private var selectedValue: String = menuValues.first()

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupChartHorizontalMenu() {
        composeTestRule.setContent {
            ChartHorizontalMenu(
                values = menuValues,
                selectedValue = selectedValue,
                onValueClick = { selectedValue = it }
            )
        }
    }

    @Test
    fun menuValues_areDisplayed() {
        with(composeTestRule) {
            onNodeWithText("CANDLE").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("BAR").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("LINE").assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun menuValue_invokesOnClick_whenClicked() {
        with(composeTestRule) {
            assert(selectedValue == "CANDLE")
            onNodeWithText("LINE").assertIsDisplayed().performClick()
            assert(selectedValue == "LINE")
        }
    }
}