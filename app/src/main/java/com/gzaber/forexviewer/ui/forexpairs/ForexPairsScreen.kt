package com.gzaber.forexviewer.ui.forexpairs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.gzaber.forexviewer.ui.forexpairs.composable.ForexPairsAppBar
import com.gzaber.forexviewer.ui.forexpairs.composable.ForexPairsContent
import com.gzaber.forexviewer.ui.forexpairs.composable.LoadingBox

@Composable
fun ForexPairsScreen(
    viewModel: ForexPairsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            ForexPairsAppBar(onBackClick = {})
        }
    ) { paddingValues ->
        when (uiState.status) {
            ForexPairsStatus.LOADING -> LoadingBox(paddingValues = paddingValues)

            ForexPairsStatus.SUCCESS -> ForexPairsContent(
                forexPairs = uiState.forexPairs,
                onFavoriteClick = viewModel::toggleFavorite,
                onGroupClick = viewModel::setGroupFiltering,
                searchText = uiState.searchText,
                onSearchTextChange = viewModel::onSearchTextChanged,
                onClearSearchText = viewModel::onSearchTextCleared,
                contentPadding = paddingValues
            )

            ForexPairsStatus.FAILURE -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(text = "FAILURE")
                    Text(text = uiState.failureMessage)
                }
            }
        }
    }
}