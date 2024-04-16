package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.chart.ChartTimeframe
import com.gzaber.forexviewer.ui.chart.ChartType
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ChartContentTest {

    private var selectedType: ChartType = ChartType.CANDLE
    private var selectedTimeframe: ChartTimeframe = ChartTimeframe.H1

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupChartContent() {
        composeTestRule.setContent {
            ChartContent(
                uiForexPair = UiForexPair(
                    symbol = "EUR/USD",
                    base = "Euro",
                    quote = "US Dollar"
                ),
                exchangeRate = 1.2365,
                selectedType = selectedType,// ChartType.CANDLE,
                timeSeriesValues = listOf(
                    TimeSeriesValue(
                        datetime = "12:00",
                        high = 1.30,
                        open = 1.25,
                        close = 1.20,
                        low = 1.15
                    ),
                    TimeSeriesValue(
                        datetime = "13:00",
                        high = 1.35,
                        close = 1.30,
                        open = 1.20,
                        low = 1.15
                    )
                ),
                selectedTimeframe = selectedTimeframe,// ChartTimeframe.H1,
                onChartTypeClick = { selectedType = it },
                onChartTimeframeClick = { selectedTimeframe = it },
                contentPadding = PaddingValues(0.dp)
            )
        }
    }

    @Test
    fun forexPairNameAndExchangeRate_areDisplayed() {
        with(composeTestRule) {
            onNodeWithText("Euro / US Dollar").assertIsDisplayed()
            onNodeWithText("1.2365").assertIsDisplayed()
        }
    }

    @Test
    fun chartTypeMenu_valuesAreDisplayed() {
        with(composeTestRule) {
            onNodeWithText("CANDLE").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("BAR").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("LINE").assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun chartTypeMenu_invokesOnClick_whenClicked() {
        with(composeTestRule) {
            assert(selectedType == ChartType.CANDLE)
            onNodeWithText("LINE").assertIsDisplayed().performClick()
            assert(selectedType == ChartType.LINE)
        }
    }

    @Test
    fun chartTimeframeMenu_valuesAreDisplayed() {
        with(composeTestRule) {
            onNodeWithText("M5").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("M15").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("H1").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("H4").assertIsDisplayed().assertHasClickAction()
            onNodeWithText("D1").assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun chartTimeframeMenu_invokesOnClick_whenClicked() {
        with(composeTestRule) {
            assert(selectedTimeframe == ChartTimeframe.H1)
            onNodeWithText("D1").assertIsDisplayed().performClick()
            assert(selectedTimeframe == ChartTimeframe.D1)
        }
    }

    @Test
    fun forexChart_composableIsDisplayed() {
        composeTestRule.onNodeWithTag(TAG_FOREX_CHART).assertIsDisplayed()
    }
}