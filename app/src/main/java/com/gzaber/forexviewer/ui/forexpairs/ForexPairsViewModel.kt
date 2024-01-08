package com.gzaber.forexviewer.ui.forexpairs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.data.repository.forexdata.model.ForexPair
import com.gzaber.forexviewer.ui.forexpairs.model.UiForexPair
import com.gzaber.forexviewer.ui.forexpairs.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ForexPairsStatus {
    LOADING, SUCCESS, FAILURE
}

enum class ForexGroupsFilterType(val group: String) {
    ALL("All"),
    MAJOR("Major"),
    MINOR("Minor"),
    EXOTIC("Exotic"),
    EXOTIC_CROSS("Exotic-Cross")
}

data class ForexPairsUiState(
    val status: ForexPairsStatus = ForexPairsStatus.LOADING,
    val forexPairs: List<UiForexPair> = listOf(),
    val searchText: String = "",
    val failureMessage: String = ""
)

@HiltViewModel
class ForexPairsViewModel @Inject constructor(
    forexDataRepository: ForexDataRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _forexGroupsFilterType = MutableStateFlow(ForexGroupsFilterType.MAJOR)
    private val _searchText = MutableStateFlow("")

    val uiState = combine(
        forexDataRepository.fetchForexPairs(),
        favoritesRepository.loadAllFavorites(),
        _forexGroupsFilterType,
        _searchText
    ) { forexPairs, favorites, filterType, searchText ->
        val uiForexPairs = forexPairs.map { forexPair ->
            forexPair.toUiModel(
                isFavorite = favorites.any { favorite ->
                    favorite.symbol == forexPair.symbol
                },
                favoriteId = favorites.find { it.symbol == forexPair.symbol }?.id
            )
        }.filter {
            (it.group == filterType.group) || (filterType == ForexGroupsFilterType.ALL)
        }.filter {
            it.symbol.contains(_searchText.value, ignoreCase = true) ||
                    it.base.contains(_searchText.value, ignoreCase = true) ||
                    it.quote.contains(_searchText.value, ignoreCase = true)
        }
        ForexPairsUiState(
            status = ForexPairsStatus.SUCCESS,
            forexPairs = uiForexPairs,
            searchText = searchText
        )
    }
        .catch {
            ForexPairsUiState(
                status = ForexPairsStatus.FAILURE,
                failureMessage = it.message ?: ""
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ForexPairsUiState()
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

    fun setGroupFiltering(filterType: ForexGroupsFilterType) {
        _forexGroupsFilterType.value = filterType
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun onSearchTextCleared() {
        _searchText.value = ""
    }
}