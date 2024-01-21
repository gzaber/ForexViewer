package com.gzaber.forexviewer.ui.forexpairs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.gzaber.forexviewer.R
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
        when (uiState.status) {
            ForexPairsStatus.LOADING -> LoadingBox(paddingValues = paddingValues)

            else -> ForexPairsContent(
                forexPairs = uiState.forexPairs,
                searchText = uiState.searchText,
                forexGroups = ForexGroup.entries.map { it.toString() },
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

    if (uiState.status == ForexPairsStatus.FAILURE) {
        val failureMessage =
            uiState.failureMessage.ifEmpty { stringResource(id = R.string.failure_message) }

        LaunchedEffect(failureMessage) {
            snackbarHostState.showSnackbar(failureMessage)
        }
    }
}