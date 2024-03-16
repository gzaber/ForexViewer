package com.gzaber.forexviewer.ui.forexpairs

import com.gzaber.forexviewer.ui.util.model.UiForexPair

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
