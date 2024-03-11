package com.gzaber.forexviewer.ui.chart

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.gzaber.forexviewer.ui.chart.composable.ChartAppBar
import com.gzaber.forexviewer.ui.chart.composable.ChartContent
import com.gzaber.forexviewer.ui.util.composable.LoadingBox

@Composable
fun ChartScreen(
    onBackClick: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: ChartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ChartAppBar(
                title = uiState.symbol,
                isFavorite = uiState.uiForexPair.isFavorite,
                onBackClick = onBackClick,
                onFavoriteClick = { viewModel.toggleFavorite(uiState.uiForexPair) }
            )
        }
    ) { paddingValues ->

        if (uiState.isLoading) {
            LoadingBox(paddingValues = paddingValues)
        } else {
            ChartContent(
                uiForexPair = uiState.uiForexPair,
                exchangeRate = uiState.exchangeRate,
                timeSeriesValues = uiState.timeSeriesValues,
                selectedType = uiState.chartType,
                selectedTimeframe = uiState.chartTimeframe,
                onChartTypeClick = viewModel::onTypeChanged,
                onChartTimeframeClick = viewModel::onTimeframeChanged,
                contentPadding = paddingValues,
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