package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
    heightUsed: Double = 0.96
) {
    val chartMin = timeSeriesValues.map {
        it.low
    }.min()
    val chartMax = timeSeriesValues.map {
        it.high
    }.max()

    Box(modifier = modifier.fillMaxSize()) {
        if (timeSeriesValues.isEmpty()) return@Box

        Row(modifier = Modifier.padding(start = 2.dp)) {
            ForexChartContent(
                chartType = chartType,
                timeSeriesValues = timeSeriesValues,
                chartMin = chartMin,
                chartMax = chartMax,
                heightUsed = heightUsed,
                modifier = Modifier.weight(1f)
            )
            ForexChartPrices(
                chartMin = chartMin,
                chartMax = chartMax,
                heightUsed = heightUsed
            )
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