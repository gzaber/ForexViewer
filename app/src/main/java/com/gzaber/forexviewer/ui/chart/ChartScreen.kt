package com.gzaber.forexviewer.ui.chart

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
import com.gzaber.forexviewer.ui.chart.composable.ChartAppBar
import com.gzaber.forexviewer.ui.chart.composable.ChartContent
import com.gzaber.forexviewer.ui.util.composable.LoadingBox
import com.gzaber.forexviewer.ui.util.model.UiForexPair

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

        when (uiState.status) {
            ChartStatus.LOADING -> LoadingBox(paddingValues = paddingValues)

            else -> ChartContent(
                uiForexPair = uiState.uiForexPair,
                exchangeRate = uiState.exchangeRate,
                typesOfChart = ChartType.entries.map { it.toString() },
                selectedType = uiState.chartType.toString(),
                timeframes = ChartTimeframe.entries.map { it.toString() },
                selectedTimeframe = uiState.chartTimeframe.toString(),
                onChartTypeClick = viewModel::onTypeChanged,
                onChartTimeframeClick = viewModel::onTimeframeChanged,
                contentPadding = paddingValues,
            )
        }
    }

    if (uiState.status == ChartStatus.FAILURE) {
        val failureMessage =
            uiState.failureMessage.ifEmpty { stringResource(id = R.string.failure_message) }

        LaunchedEffect(failureMessage) {
            snackbarHostState.showSnackbar(failureMessage)
        }
    }
}