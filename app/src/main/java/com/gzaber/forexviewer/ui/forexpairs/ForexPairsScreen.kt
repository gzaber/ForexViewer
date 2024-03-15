package com.gzaber.forexviewer.ui.forexpairs

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.gzaber.forexviewer.ui.forexpairs.composable.ForexPairsAppBar
import com.gzaber.forexviewer.ui.forexpairs.composable.ForexPairsContent
import com.gzaber.forexviewer.ui.util.composable.LoadingBox

@Composable
fun ForexPairsScreen(
    onBackClick: () -> Unit,
    onListItemClick: (String) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: ForexPairsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ForexPairsAppBar(onBackClick = onBackClick)
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingBox(paddingValues = paddingValues)
        } else {
            ForexPairsContent(
                forexPairs = uiState.uiForexPairs,
                searchText = uiState.searchText,
                selectedGroup = uiState.group.toString(),
                onListItemClick = onListItemClick,
                onFavoriteClick = viewModel::toggleFavorite,
                onForexGroupClick = viewModel::onForexGroupSet,
                onSearchTextChange = viewModel::onSearchTextChanged,
                onClearSearchText = viewModel::onSearchTextCleared,
                contentPadding = paddingValues
            )
        }
    }

    uiState.failureMessage?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message)
            viewModel.snackbarMessageShown()
        }
    }
}