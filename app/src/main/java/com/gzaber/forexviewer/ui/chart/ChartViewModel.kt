package com.gzaber.forexviewer.ui.chart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.TimeSeriesValue
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.ui.navigation.ForexViewerDestinationArgs
import com.gzaber.forexviewer.ui.util.model.toFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val forexDataRepository: ForexDataRepository,
    private val favoritesRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _symbol: String =
        (savedStateHandle[ForexViewerDestinationArgs.SYMBOL_ARG] ?: "")
            .replace("_", "/")

    private val _uiState = MutableStateFlow(ChartUiState(symbol = _symbol))
    val uiState = _uiState.asStateFlow()

    init {
        collectTimeSeries()

        viewModelScope.launch {
            favoritesRepository.loadFavoriteBySymbol(_symbol)
                .catch { e ->
                    _uiState.update {
                        it.copy(failureMessage = e.message)
                    }
                }
                .collect { favorite ->
                    _uiState.update {
                        it.copy(
                            uiForexPair = it.uiForexPair.copy(
                                isFavorite = favorite != null,
                                favoriteId = favorite?.id
                            )
                        )
                    }
                }
        }
    }

    fun toggleFavorite(uiForexPair: UiForexPair) {
        viewModelScope.launch {
            try {
                if (uiForexPair.isFavorite) {
                    favoritesRepository.deleteFavorite(uiForexPair.toFavorite())
                } else {
                    favoritesRepository.insertFavorite(uiForexPair.toFavorite())
                }
            } catch (e: Throwable) {
                _uiState.update {
                    it.copy(failureMessage = e.message)
                }
            }
        }
    }

    fun onTypeChanged(type: ChartType) {
        _uiState.update {
            it.copy(chartType = type)
        }
    }

    fun onTimeframeChanged(timeframe: ChartTimeframe) {
        _uiState.update {
            it.copy(chartTimeframe = timeframe)
        }
        collectTimeSeries()
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(failureMessage = null)
        }
    }

    private fun collectTimeSeries() {
        viewModelScope.launch {
            forexDataRepository.fetchTimeSeries(
                _symbol,
                _uiState.value.chartTimeframe.apiParam,
                100
            )
                .catch { e ->
                    _uiState.update {
                        it.copy(failureMessage = e.message)
                    }
                }.collect { timeSeries ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            uiForexPair = it.uiForexPair.copy(
                                symbol = timeSeries.meta.symbol,
                                base = timeSeries.meta.base,
                                quote = timeSeries.meta.quote,
                            ),
                            timeSeriesValues = timeSeries.values,
                            exchangeRate = timeSeries.values.first().close
                        )
                    }
                }
        }
    }
}