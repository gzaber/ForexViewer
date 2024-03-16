package com.gzaber.forexviewer.ui.home

import com.gzaber.forexviewer.ui.util.model.UiFavorite

data class HomeUiState(
    val uiFavorites: List<UiFavorite> = listOf(),
    val showDialog: Boolean = false,
    val apiKeyText: String = "",
    val isLoading: Boolean = true,
    val failureMessage: String? = null,
)
