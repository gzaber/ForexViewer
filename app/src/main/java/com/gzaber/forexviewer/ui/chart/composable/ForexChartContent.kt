package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.chart.ChartType
import com.gzaber.forexviewer.ui.util.extension.drawBar
import com.gzaber.forexviewer.ui.util.extension.drawCandle

const val TAG_FOREX_CHART_CONTENT = "forexChartContentTag"

@Composable
fun ForexChartContent(
    chartType: ChartType,
    timeSeriesValues: List<TimeSeriesValue>,
    chartMin: Double,
    chartMax: Double,
    heightUsed: Double,
    lastPriceSpace: Int,
    bodyWidth: Int,
    bodySpace: Int,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Box(
        modifier = modifier
            .testTag(TAG_FOREX_CHART_CONTENT)
            .border(width = 1.dp, color = Color.Gray),
        contentAlignment = Alignment.CenterEnd
    )
    {
        val canvasWidth = ((bodyWidth + bodySpace) * timeSeriesValues.size + lastPriceSpace).dp

        Canvas(
            modifier = Modifier
                .horizontalScroll(scrollState, reverseScrolling = true)
                .fillMaxHeight()
                .width(canvasWidth)
        ) {
            val ratio = size.height * heightUsed / (chartMax - chartMin)

            when (chartType) {
                ChartType.LINE -> {
                    val path = Path()

                    repeat(timeSeriesValues.size) { index ->
                        val x =
                            size.width - ((bodyWidth + bodySpace) * (index + 1) + lastPriceSpace).dp.toPx()
                        val y =
                            ((chartMax - timeSeriesValues[index].close) * ratio + size.height * ((1 - heightUsed) / 2)).toFloat()
                        if (index == 0) {
                            path.moveTo(x + bodyWidth / 2 + bodySpace, y)
                        } else {
                            path.lineTo(x + bodyWidth / 2 + bodySpace, y)
                        }
                    }
                    drawPath(
                        path = path,
                        color = Color.Black,
                        style = Stroke(width = 3f)
                    )
                }

                else -> {
                    repeat(timeSeriesValues.size) { index ->
                        val yOffset =
                            (chartMax - timeSeriesValues[index].high) * ratio + size.height * ((1 - heightUsed) / 2)
                        val initialOffset = Offset(
                            x = size.width - ((bodyWidth + bodySpace) * (index + 1) + lastPriceSpace).dp.toPx(),
                            y = yOffset.toFloat()
                        )

                        if (chartType == ChartType.CANDLE) {
                            drawCandle(
                                timeSeriesValue = timeSeriesValues[index],
                                ratio = ratio,
                                initialOffset = initialOffset,
                                bodyWidth = bodyWidth.dp.toPx()
                            )
                        }
                        if (chartType == ChartType.BAR) {
                            drawBar(
                                timeSeriesValue = timeSeriesValues[index],
                                ratio = ratio,
                                initialOffset = initialOffset,
                                bodyWidth = bodyWidth.dp.toPx()
                            )
                        }
                    }
                }
            }
        }
    }
}