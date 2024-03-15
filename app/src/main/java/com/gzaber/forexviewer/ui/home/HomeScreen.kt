package com.gzaber.forexviewer.ui.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.gzaber.forexviewer.R
import com.gzaber.forexviewer.ui.home.composable.ApiKeyDialog
import com.gzaber.forexviewer.ui.home.composable.FavoritesList
import com.gzaber.forexviewer.ui.home.composable.HomeAppBar
import com.gzaber.forexviewer.ui.util.composable.LoadingBox

@Composable
fun HomeScreen(
    onForexPairsClick: () -> Unit,
    onListItemClick: (String) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            HomeAppBar(
                onApiKeyClick = viewModel::toggleShowingDialog,
                onForexPairsClick = onForexPairsClick
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingBox(paddingValues = paddingValues)
        } else FavoritesList(
            favorites = uiState.uiFavorites,
            contentPadding = paddingValues,
            onListItemClick = onListItemClick
        )
    }

    if (uiState.showDialog) {
        ApiKeyDialog(
            title = stringResource(id = R.string.api_key),
            apiKeyText = uiState.apiKeyText,
            confirmText = stringResource(id = R.string.confirm_api_key),
            dismissText = stringResource(id = R.string.dismiss_api_key),
            onApiKeyTextChanged = viewModel::onApiKeyTextChanged,
            onDismissRequest = viewModel::toggleShowingDialog,
            onConfirmRequest = viewModel::saveApiKey
        )
    }

    uiState.failureMessage?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message)
            viewModel.snackbarMessageShown()
        }
    }
}