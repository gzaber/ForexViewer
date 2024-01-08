package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gzaber.forexviewer.ui.forexpairs.model.UiForexPair
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun ForexPairsList(
    forexPairs: List<UiForexPair>,
    onFavoriteClick: (UiForexPair) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(forexPairs, key = { it.symbol }) { forexPair ->
            ForexPairListItem(
                forexPair = forexPair,
                onClick = {},
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Preview
@Composable
fun ForexPairsListPreview() {
    ForexViewerTheme {
        ForexPairsList(
            forexPairs = listOf(
                UiForexPair(
                    symbol = "EUR/USD",
                    group = "Major",
                    base = "Euro",
                    quote = "US Dollar",
                    isFavorite = false
                ),
                UiForexPair(
                    symbol = "GBP/USD",
                    group = "Major",
                    base = "British Pound",
                    quote = "US Dollar",
                    isFavorite = true
                ),
                UiForexPair(
                    symbol = "USD/JPY",
                    group = "Major",
                    base = "US Dollar",
                    quote = "Japanese Yen",
                    isFavorite = false
                )
            ),
            onFavoriteClick = {}
        )
    }
}