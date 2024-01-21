package com.gzaber.forexviewer.ui.forexpairs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gzaber.forexviewer.data.repository.favorites.FavoritesRepository
import com.gzaber.forexviewer.data.repository.forexdata.ForexDataRepository
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.ui.util.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ForexPairsStatus {
    LOADING, SUCCESS, FAILURE
}

enum class ForexGroup(val value: String) {
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
    val group: ForexGroup = ForexGroup.MAJOR,
    val failureMessage: String = ""
)

@HiltViewModel
class ForexPairsViewModel @Inject constructor(
    forexDataRepository: ForexDataRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _forexGroup = MutableStateFlow(ForexGroup.ALL)
    private val _searchText = MutableStateFlow("")

    val uiState = combine(
        forexDataRepository.fetchAllForexPairs(),
        favoritesRepository.loadAllFavorites(),
        _forexGroup,
        _searchText
    ) { forexPairs, favorites, group, searchText ->
        val uiForexPairs = forexPairs.map { forexPair ->
            forexPair.toUiModel(
                isFavorite = favorites.any { favorite ->
                    favorite.symbol == forexPair.symbol
                },
                favoriteId = favorites.find { it.symbol == forexPair.symbol }?.id
            )
        }.filter {
            (it.group == group.value) || (group == ForexGroup.ALL)
        }.filter {
            it.symbol.contains(_searchText.value, ignoreCase = true) ||
                    it.base.contains(_searchText.value, ignoreCase = true) ||
                    it.quote.contains(_searchText.value, ignoreCase = true)
        }
        ForexPairsUiState(
            status = ForexPairsStatus.SUCCESS,
            forexPairs = uiForexPairs,
            searchText = searchText,
            group = group
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

    fun onForexGroupSet(group: String) {
        _forexGroup.value = ForexGroup.valueOf(group)
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun onSearchTextCleared() {
        _searchText.value = ""
    }
}