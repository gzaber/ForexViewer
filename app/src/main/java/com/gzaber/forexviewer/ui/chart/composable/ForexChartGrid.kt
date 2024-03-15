package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ForexChartGrid(
    horizontalLinesNumber: Int,
    verticalLinesFrequency: Int,
    dataSize: Int,
    heightUsed: Double,
    lastPriceSpace: Int,
    bodyWidth: Int,
    bodySpace: Int,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
) {
    val canvasWidth = ((bodyWidth + bodySpace) * dataSize + lastPriceSpace).dp

    Canvas(
        modifier = modifier
            .horizontalScroll(scrollState, reverseScrolling = true)
            .width(canvasWidth)
            .fillMaxHeight()
    ) {
        repeat(horizontalLinesNumber) { index ->
            val yOffset = (1 - heightUsed) / 2 * size.height
            val yStep = size.height * heightUsed / (horizontalLinesNumber - 1)
            val baseOffset = Offset(
                x = 0f,
                y = (yOffset + yStep * index).toFloat()
            )

            drawLine(
                color = Color.LightGray,
                strokeWidth = 2f,
                start = baseOffset,
                end = baseOffset.copy(x = size.width)
            )
        }

        repeat(dataSize) { index ->
            if (index == 0 || index % verticalLinesFrequency == 0) {
                val baseOffset = Offset(
                    x = size.width - ((bodyWidth + bodySpace) * (index + 1) + lastPriceSpace - bodyWidth / 2).dp.toPx(),
                    y = 0f
                )

                drawLine(
                    color = Color.LightGray,
                    strokeWidth = 2f,
                    start = baseOffset,
                    end = baseOffset.copy(y = size.height)
                )
            }
        }
    }
}