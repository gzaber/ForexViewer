package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.chart.ChartType
import com.gzaber.forexviewer.util.RobolectricTestActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForexChartTest {

    @get:Rule(order = 0)
    val robolectricTestActivityRule = RobolectricTestActivity()

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setupForexChart() {
        composeTestRule.setContent {
            ForexChart(
                chartType = ChartType.CANDLE,
                timeSeriesValues = listOf(
                    TimeSeriesValue(
                        datetime = "2024-03-14 12:00:00",
                        high = 1.25,
                        low = 1.15,
                        open = 1.20,
                        close = 1.20,
                    ),
                    TimeSeriesValue(
                        datetime = "2024-03-14 11:00:00",
                        high = 1.25,
                        low = 1.05,
                        open = 1.10,
                        close = 1.20,
                    )
                ),
                heightUsed = 0.95,
                lastPriceSpace = 110,
                bodyWidth = 8,
                bodySpace = 4,
                horizontalLabelsNumber = 6,
                verticalLabelsFrequency = 8,
                scrollState = rememberScrollState()
            )
        }
    }

    @Test
    fun forexChartGrid_composableIsDisplayed() {
        composeTestRule.onNodeWithTag(TAG_FOREX_CHART_GRID).assertIsDisplayed()
    }

    @Test
    fun forexChartContent_composableIsDisplayed() {
        composeTestRule.onNodeWithTag(TAG_FOREX_CHART_CONTENT).assertIsDisplayed()
    }

    @Test
    fun forexChartPrices_composableIsDisplayed() {
        composeTestRule.onNodeWithTag(TAG_FOREX_CHART_PRICES).assertIsDisplayed()
    }

    @Test
    fun forexChartDates_composableIsDisplayed() {
        composeTestRule.onNodeWithTag(TAG_FOREX_CHART_DATES).assertIsDisplayed()
    }
}