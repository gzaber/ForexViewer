package com.gzaber.forexviewer.ui.favorites

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
import com.gzaber.forexviewer.ui.favorites.composable.FavoritesAppBar
import com.gzaber.forexviewer.ui.favorites.composable.FavoritesList
import com.gzaber.forexviewer.ui.util.composable.LoadingBox

@Composable
fun FavoritesScreen(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            FavoritesAppBar(onBackClick = {})
        }
    ) { paddingValues ->
        when (uiState.status) {
            FavoritesStatus.LOADING -> LoadingBox(paddingValues = paddingValues)

            else -> FavoritesList(
                favorites = uiState.favorites,
                contentPadding = paddingValues,
                onClick = {}
            )
        }
    }

    if (uiState.status == FavoritesStatus.FAILURE) {
        val failureMessage =
            uiState.failureMessage.ifEmpty { stringResource(id = R.string.failure_message) }

        LaunchedEffect(failureMessage) {
            snackbarHostState.showSnackbar(failureMessage)
        }
    }
}