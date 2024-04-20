package com.gzaber.forexviewer.ui.util.extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue

fun DrawScope.drawCandle(
    timeSeriesValue: TimeSeriesValue,
    ratio: Double,
    initialOffset: Offset,
    bodyWidth: Float
) {
    val bodyHigh =
        if (timeSeriesValue.open > timeSeriesValue.close) timeSeriesValue.open else timeSeriesValue.close
    val bodyLow =
        if (timeSeriesValue.close < timeSeriesValue.open) timeSeriesValue.close else timeSeriesValue.open
    val bodySize = Size(
        width = bodyWidth,
        height = ((bodyHigh - bodyLow) * ratio).toFloat()
    )
    val bodyOffset = Offset(
        x = initialOffset.x,
        y = initialOffset.y + ((timeSeriesValue.high - bodyHigh) * ratio).toFloat()
    )

    val wickHighOffset =
        Offset(x = initialOffset.x + bodyWidth / 2, y = (initialOffset.y))
    val wickLowOffset =
        Offset(
            x = initialOffset.x + bodyWidth / 2,
            y = (initialOffset.y + ((timeSeriesValue.high - timeSeriesValue.low) * ratio)).toFloat()
        )

    drawLine(
        color = Color.Black,
        strokeWidth = 3f,
        start = wickHighOffset,
        end = wickLowOffset
    )
    if (timeSeriesValue.open != timeSeriesValue.close) {
        drawRect(
            color = if (timeSeriesValue.open > timeSeriesValue.close) Color.Red else Color.Green,
            size = bodySize,
            topLeft = bodyOffset
        )
    } else {
        drawLine(
            color = Color.Black,
            strokeWidth = 3f,
            start = bodyOffset,
            end = bodyOffset.copy(x = bodyOffset.x + bodyWidth)
        )
    }
}

fun DrawScope.drawBar(
    timeSeriesValue: TimeSeriesValue,
    ratio: Double,
    initialOffset: Offset,
    bodyWidth: Float
) {
    val highOffset =
        Offset(x = initialOffset.x + bodyWidth / 2, y = initialOffset.y)
    val lowOffset =
        Offset(
            x = initialOffset.x + bodyWidth / 2,
            y = (initialOffset.y + ((timeSeriesValue.high - timeSeriesValue.low) * ratio)).toFloat()
        )
    val openOffset = Offset(
        x = initialOffset.x + bodyWidth / 2,
        y = (initialOffset.y + ((timeSeriesValue.high - timeSeriesValue.open) * ratio)).toFloat()
    )
    val closeOffset = Offset(
        x = initialOffset.x + bodyWidth / 2,
        y = (initialOffset.y + ((timeSeriesValue.high - timeSeriesValue.close) * ratio)).toFloat()
    )

    drawLine(
        color = Color.Black,
        strokeWidth = 3f,
        start = highOffset,
        end = lowOffset
    )
    drawLine(
        color = Color.Black,
        strokeWidth = 3f,
        start = openOffset,
        end = Offset(x = openOffset.x - bodyWidth / 2, y = openOffset.y)
    )
    drawLine(
        color = Color.Black,
        strokeWidth = 3f,
        start = closeOffset,
        end = Offset(x = closeOffset.x + bodyWidth / 2, y = closeOffset.y)
    )
}