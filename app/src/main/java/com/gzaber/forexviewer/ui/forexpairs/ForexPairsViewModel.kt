package com.gzaber.forexviewer.ui.forexpairs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.ui.util.model.toFavorite
import com.gzaber.forexviewer.ui.util.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class ForexGroup(val value: String) {
    ALL("All"),
    MAJOR("Major"),
    MINOR("Minor"),
    EXOTIC("Exotic"),
    EXOTIC_CROSS("Exotic-Cross")
}

data class ForexPairsUiState(
    val isLoading: Boolean = true,
    val uiForexPairs: List<UiForexPair> = listOf(),
    val searchText: String = "",
    val group: ForexGroup = ForexGroup.MAJOR,
    val failureMessage: String? = null
)

@HiltViewModel
class ForexPairsViewModel @Inject constructor(
    forexDataRepository: ForexDataRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForexPairsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                forexDataRepository.fetchAllForexPairs(),
                favoritesRepository.loadAllFavorites(),
                _uiState
            ) { forexPairs, favorites, uiState ->
                forexPairs.map { forexPair ->
                    forexPair.toUiModel(
                        isFavorite = favorites.any { favorite ->
                            favorite.symbol == forexPair.symbol
                        },
                        favoriteId = favorites.find { it.symbol == forexPair.symbol }?.id
                    )
                }.filter {
                    (it.group == uiState.group.value) || (uiState.group == ForexGroup.ALL)
                }.filter {
                    it.symbol.contains(uiState.searchText, ignoreCase = true) ||
                            it.base.contains(uiState.searchText, ignoreCase = true) ||
                            it.quote.contains(uiState.searchText, ignoreCase = true)
                }
            }
                .catch { e ->
                    _uiState.update {
                        it.copy(failureMessage = e.message)
                    }
                }
                .collect { uiForexPairs ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            uiForexPairs = uiForexPairs
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

    fun onForexGroupSet(group: String) {
        _uiState.update {
            it.copy(group = ForexGroup.valueOf(group))
        }
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update {
            it.copy(searchText = text)
        }
    }

    fun onSearchTextCleared() {
        _uiState.update {
            it.copy(searchText = "")
        }
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(failureMessage = null)
        }
    }
}