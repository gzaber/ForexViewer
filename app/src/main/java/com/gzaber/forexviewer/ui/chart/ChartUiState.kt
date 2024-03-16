package com.gzaber.forexviewer.ui.chart

import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.util.model.UiForexPair


enum class ChartType {
    CANDLE, BAR, LINE
}

enum class ChartTimeframe(val apiParam: String) {
    M5("5min"),
    M15("15min"),
    H1("1h"),
    H4("4h"),
    D1("1day")
}

data class ChartUiState(
    val isLoading: Boolean = true,
    val symbol: String = "",
    val uiForexPair: UiForexPair = UiForexPair(),
    val exchangeRate: Double = 0.0,
    val timeSeriesValues: List<TimeSeriesValue> = listOf(),
    val chartType: ChartType = ChartType.CANDLE,
    val chartTimeframe: ChartTimeframe = ChartTimeframe.H1,
    val failureMessage: String? = null,
)


