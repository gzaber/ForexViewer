package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.chart.ChartType
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme


@Composable
fun ForexChart(
    chartType: ChartType,
    timeSeriesValues: List<TimeSeriesValue>,
    modifier: Modifier = Modifier,
    heightUsed: Double = 0.96,
    lastPriceSpace: Int = 120,
    bodyWidth: Int = 10,
    bodySpace: Int = 5,
    scrollState: ScrollState = rememberScrollState()
) {
    val chartMin = timeSeriesValues.map {
        it.low
    }.min()
    val chartMax = timeSeriesValues.map {
        it.high
    }.max()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 1.dp),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                ForexChartGrid(
                    horizontalLines = 8,
                    verticalLineFrequency = 10,
                    dataSize = timeSeriesValues.size,
                    heightUsed = heightUsed,
                    lastPriceSpace = lastPriceSpace,
                    bodyWidth = bodyWidth,
                    bodySpace = bodySpace,
                    scrollState = scrollState
                )
                ForexChartContent(
                    chartType = chartType,
                    timeSeriesValues = timeSeriesValues,
                    chartMin = chartMin,
                    chartMax = chartMax,
                    heightUsed = heightUsed,
                    lastPriceSpace = lastPriceSpace,
                    bodyWidth = bodyWidth,
                    bodySpace = bodySpace,
                    scrollState = scrollState
                )

            }
            ForexChartPrices(
                chartMin = chartMin,
                chartMax = chartMax,
                heightUsed = heightUsed
            )
        }
        Row {
            ForexChartDates(
                dates = timeSeriesValues.map { it.datetime }.toList(),
                lastPriceSpace = lastPriceSpace,
                bodyWidth = bodyWidth,
                bodySpace = bodySpace,
                scrollState = scrollState,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(55.dp))
        }
    }
}


@Preview
@Composable
fun ForexCandleChartPreview() {
    ForexViewerTheme {
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
                ),
                TimeSeriesValue(
                    datetime = "2024-03-14 10:00:00",
                    high = 1.20,
                    low = 1.05,
                    open = 1.15,
                    close = 1.10,
                ),
                TimeSeriesValue(
                    datetime = "2024-03-14 09:00:00",
                    high = 1.25,
                    low = 1.10,
                    open = 1.20,
                    close = 1.15
                )
            )
        )
    }
}

@Preview
@Composable
fun ForexBarChartPreview() {
    ForexViewerTheme {
        ForexChart(
            chartType = ChartType.BAR,
            timeSeriesValues = listOf(
                TimeSeriesValue(
                    datetime = "1",
                    high = 1.25,
                    low = 1.15,
                    open = 1.20,
                    close = 1.20,
                ),
                TimeSeriesValue(
                    datetime = "2",
                    high = 1.25,
                    low = 1.05,
                    open = 1.10,
                    close = 1.20,
                ),
                TimeSeriesValue(
                    datetime = "3",
                    high = 1.20,
                    low = 1.05,
                    open = 1.15,
                    close = 1.10,
                ),
                TimeSeriesValue(
                    datetime = "4",
                    high = 1.25,
                    low = 1.10,
                    open = 1.20,
                    close = 1.15
                )
            )
        )
    }
}

@Preview
@Composable
fun ForexLineChartPreview() {
    ForexViewerTheme {
        ForexChart(
            chartType = ChartType.LINE,
            timeSeriesValues = listOf(
                TimeSeriesValue(
                    datetime = "1",
                    high = 1.25,
                    low = 1.15,
                    open = 1.20,
                    close = 1.20,
                ),
                TimeSeriesValue(
                    datetime = "2",
                    high = 1.25,
                    low = 1.05,
                    open = 1.10,
                    close = 1.20,
                ),
                TimeSeriesValue(
                    datetime = "3",
                    high = 1.20,
                    low = 1.05,
                    open = 1.15,
                    close = 1.10,
                ),
                TimeSeriesValue(
                    datetime = "4",
                    high = 1.25,
                    low = 1.10,
                    open = 1.20,
                    close = 1.15
                )
            )
        )
    }
}