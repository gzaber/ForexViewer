package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import java.util.Locale

const val TAG_FOREX_CHART_PRICES = "forexChartPricesTag"

@Composable
fun ForexChartPrices(
    chartMin: Double,
    chartMax: Double,
    priceLabelsNumber: Int,
    heightUsed: Double,
    modifier: Modifier = Modifier,
    priceSeparatorWidth: Float = 10f,
    textMeasurer: TextMeasurer = rememberTextMeasurer()
) {

    Canvas(
        modifier = modifier
            .testTag(TAG_FOREX_CHART_PRICES)
            .width(55.dp)
            .fillMaxHeight()
    ) {
        val ratio = size.height * heightUsed / (chartMax - chartMin)
        val step = (chartMax - chartMin) / (priceLabelsNumber - 1)

        repeat(priceLabelsNumber) {
            val measuredText = textMeasurer.measure(
                String.format(Locale.ENGLISH, "%.4f", chartMax - (it * step))
            )
            val baseOffset = Offset(
                x = 0f,
                y = (it * step * ratio + size.height * ((1 - heightUsed) / 2)).toFloat() - measuredText.size.height / 2
            )

            drawLine(
                color = Color.Gray,
                strokeWidth = 3f,
                start = baseOffset.copy(
                    y = baseOffset.y + measuredText.size.height / 2
                ),
                end = Offset(
                    x = priceSeparatorWidth,
                    y = baseOffset.y + measuredText.size.height / 2
                )
            )

            drawText(
                textLayoutResult = measuredText,
                topLeft = baseOffset.copy(x = priceSeparatorWidth)
            )
        }
    }
}