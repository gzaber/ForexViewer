package com.gzaber.forexviewer.ui.chart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.ui.navigation.ForexViewerDestinationArgs
import com.gzaber.forexviewer.ui.util.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ChartStatus {
    LOADING, SUCCESS, FAILURE
}

enum class ChartType {
    CANDLE, BAR, LINE
}

enum class ChartTimeframe {
    M5, M15, H1, H4, D1
}

data class ChartUiState(
    val status: ChartStatus = ChartStatus.LOADING,
    val symbol: String = "",
    val uiForexPair: UiForexPair = UiForexPair(),
    val exchangeRate: Double = 0.0,
    val chartType: ChartType = ChartType.CANDLE,
    val chartTimeframe: ChartTimeframe = ChartTimeframe.H1,
    val failureMessage: String = "",
)

@HiltViewModel
class ChartViewModel @Inject constructor(
    forexDataRepository: ForexDataRepository,
    private val favoritesRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _symbol: String =
        (savedStateHandle[ForexViewerDestinationArgs.SYMBOL_ARG] ?: "")
            .replace("_", "/")
    private val _chartType = MutableStateFlow(ChartType.CANDLE)
    private val _chartTimeframe = MutableStateFlow(ChartTimeframe.H1)

    val uiState = combine(
        favoritesRepository.loadAllFavorites(),
        forexDataRepository.fetchForexPair(_symbol),
        forexDataRepository.fetchExchangeRate(_symbol),
        _chartType,
        _chartTimeframe
    ) { favorites, forexPair, exchangeRate, type, timeframe ->
        ChartUiState(
            status = ChartStatus.SUCCESS,
            uiForexPair = forexPair.toUiModel(
                isFavorite = favorites.any { it.symbol == _symbol },
                favoriteId = favorites.find { it.symbol == _symbol }?.id
            ),
            symbol = _symbol,
            exchangeRate = exchangeRate.rate,
            chartType = type,
            chartTimeframe = timeframe
        )
    }
        .catch {
            ChartUiState(
                status = ChartStatus.FAILURE,
                symbol = _symbol,
                failureMessage = it.message ?: ""
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ChartUiState(symbol = _symbol)
        )

    fun toggleFavorite(uiForexPair: UiForexPair) {
        viewModelScope.launch {
            try {
                if (uiForexPair.isFavorite) {
                    favoritesRepository.deleteFavorite(uiForexPair.toFavorite())
                } else {
                    favoritesRepository.insertFavorite(uiForexPair.toFavorite())
                }
            } catch (e: Throwable) {
                // TODO: do something
            }
        }
    }

    fun onTypeChanged(type: String) {
        _chartType.value = ChartType.valueOf(type)
    }

    fun onTimeframeChanged(timeframe: String) {
        _chartTimeframe.value = ChartTimeframe.valueOf(timeframe)
    }
}