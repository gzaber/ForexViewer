package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme
import com.gzaber.forexviewer.ui.util.model.UiForexPair

@Composable
fun ChartContent(
    uiForexPair: UiForexPair,
    exchangeRate: Double,
    typesOfChart: List<String>,
    selectedType: String,
    timeframes: List<String>,
    selectedTimeframe: String,
    contentPadding: PaddingValues,
    onChartTypeClick: (String) -> Unit,
    onChartTimeframeClick: (String) -> Unit,
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
                text = "${uiForexPair.base} / ${uiForexPair.quote}",
             //   fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            Text(
                text = "$exchangeRate",
                fontWeight = FontWeight.Bold
            //    fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        }
        ChartHorizontalMenu(
            values = typesOfChart,
            selectedValue = selectedType,
            onValueClick = onChartTypeClick
        )
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .weight(1f)
        )
        ChartHorizontalMenu(
            values = timeframes,
            selectedValue = selectedTimeframe,
            onValueClick = onChartTimeframeClick
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
            typesOfChart = listOf(
                "CANDLE", "BAR", "LINE"
            ),
            selectedType = "BAR",
            timeframes = listOf(
                "M5", "M15", "H1", "H4", "D1"
            ),
            selectedTimeframe = "H1",
            onChartTypeClick = {},
            onChartTimeframeClick = {},
            contentPadding = PaddingValues(0.dp)
        )
    }
}
