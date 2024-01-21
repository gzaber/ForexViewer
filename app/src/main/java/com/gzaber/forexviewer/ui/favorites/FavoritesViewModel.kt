package com.gzaber.forexviewer.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.ui.util.model.UiFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FavoritesStatus {
    LOADING, SUCCESS, FAILURE
}

data class FavoritesUiState(
    val status: FavoritesStatus = FavoritesStatus.LOADING,
    val favorites: List<UiFavorite> = listOf(),
    val failureMessage: String = "",
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    forexDataRepository: ForexDataRepository,
    favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                favoritesRepository.loadAllFavorites()
                    .map { favorites ->
                        favorites.map { favorite ->
                            forexDataRepository.fetchExchangeRate(favorite.symbol)
                                .map { exchangeRate ->
                                    UiFavorite(
                                        symbol = favorite.symbol,
                                        base = favorite.base,
                                        quote = favorite.quote,
                                        exchangeRate = exchangeRate.rate
                                    )
                                }.first()
                        }
                    }
                    .collect { uiFavorites ->
                        _uiState.update {
                            it.copy(
                                status = FavoritesStatus.SUCCESS,
                                favorites = uiFavorites
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        status = FavoritesStatus.FAILURE,
                        failureMessage = e.message ?: ""
                    )
                }
            }
        }
    }
}