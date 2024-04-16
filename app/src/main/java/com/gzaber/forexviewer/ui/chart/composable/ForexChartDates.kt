package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp

const val TAG_FOREX_CHART_DATES = "forexChartDatesTag"

@Composable
fun ForexChartDates(
    dates: List<String>,
    lastPriceSpace: Int,
    bodyWidth: Int,
    bodySpace: Int,
    dateLabelsFrequency: Int,
    modifier: Modifier = Modifier,
    dateSeparatorHeight: Float = 10f,
    scrollState: ScrollState = rememberScrollState(),
    textMeasurer: TextMeasurer = rememberTextMeasurer()
) {
    val canvasWidth = ((bodyWidth + bodySpace) * dates.size + lastPriceSpace).dp

    Box(
        modifier = modifier.testTag(TAG_FOREX_CHART_DATES),
        contentAlignment = Alignment.CenterEnd
    ) {
        Canvas(
            modifier = Modifier
                .horizontalScroll(scrollState, reverseScrolling = true)
                .height(25.dp)
                .width(canvasWidth)
        ) {
            repeat(dates.size) { index ->
                if (index == 0 || index % dateLabelsFrequency == 0) {
                    val measuredText = textMeasurer.measure(
                        dates[index]
                    )
                    val baseOffset = Offset(
                        x = size.width - ((bodyWidth + bodySpace) * (index + 1) + lastPriceSpace - bodyWidth / 2).dp.toPx(),
                        y = 0f
                    )

                    drawLine(
                        color = Color.Gray,
                        strokeWidth = 3f,
                        start = baseOffset,
                        end = baseOffset.copy(y = dateSeparatorHeight)
                    )

                    drawText(
                        textLayoutResult = measuredText,
                        topLeft = baseOffset.copy(y = dateSeparatorHeight)
                    )
                }
            }
        }
    }
}