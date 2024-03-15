package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.chart.ChartTimeframe
import com.gzaber.forexviewer.ui.chart.ChartType
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme
import com.gzaber.forexviewer.ui.util.model.UiForexPair

@Composable
fun ChartContent(
    uiForexPair: UiForexPair,
    exchangeRate: Double,
    timeSeriesValues: List<TimeSeriesValue>,
    selectedType: ChartType,
    selectedTimeframe: ChartTimeframe,
    contentPadding: PaddingValues,
    onChartTypeClick: (ChartType) -> Unit,
    onChartTimeframeClick: (ChartTimeframe) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${uiForexPair.base} / ${uiForexPair.quote}"
            )
            Text(
                text = "$exchangeRate",
                fontWeight = FontWeight.Bold
            )
        }
        ChartHorizontalMenu(
            values = ChartType.entries.map { it.toString() },
            selectedValue = selectedType.name,
            onValueClick = { onChartTypeClick(ChartType.valueOf(it)) }
        )
        ForexChart(
            modifier = Modifier.weight(1f),
            timeSeriesValues = timeSeriesValues,
            chartType = selectedType
        )
        ChartHorizontalMenu(
            values = ChartTimeframe.entries.map { it.toString() },
            selectedValue = selectedTimeframe.name,
            onValueClick = { onChartTimeframeClick(ChartTimeframe.valueOf(it)) }
        )
    }
}

@Preview
@Composable
fun ChartContentPreview() {
    ForexViewerTheme {
        ChartContent(
            uiForexPair = UiForexPair(
                symbol = "EUR/USD",
                base = "Euro",
                quote = "US Dollar"
            ),
            exchangeRate = 1.2365,
            selectedType = ChartType.CANDLE,
            timeSeriesValues = listOf(
                TimeSeriesValue(
                    datetime = "1",
                    high = 1.30,
                    open = 1.25,
                    close = 1.20,
                    low = 1.15
                ),
                TimeSeriesValue(
                    datetime = "2",
                    high = 1.35,
                    close = 1.30,
                    open = 1.20,
                    low = 1.15
                )
            ),
            selectedTimeframe = ChartTimeframe.H1,
            onChartTypeClick = {},
            onChartTimeframeClick = {},
            contentPadding = PaddingValues(0.dp)
        )
    }
}
