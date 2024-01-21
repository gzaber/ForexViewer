package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun ChartHorizontalMenu(
    values: List<String>,
    selectedValue: String,
    onValueClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        values.forEach { value ->
            Text(
                text = value,
                fontWeight = if (value == selectedValue) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .background(
                        color = if (value == selectedValue) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .clickable {
                        onValueClick(value)
                    }
            )
        }
    }
}

@Preview
@Composable
fun ChartHorizontalMenuPreview() {
    ForexViewerTheme {
        ChartHorizontalMenu(
            values = listOf(
                "CANDLE", "BAR", "LINE"
            ),
            selectedValue = "BAR",
            onValueClick = {}
        )
    }
}