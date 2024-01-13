package com.gzaber.forexviewer.ui.favorites.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.favorites.model.UiFavorite
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun FavoritesList(
    favorites: List<UiFavorite>,
    contentPadding: PaddingValues,
    onClick: (UiFavorite) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(favorites, key = { it.symbol }) { favorite ->
            FavoriteListItem(
                favorite = favorite,
                onClick = { onClick(favorite) },
            )
        }
    }
}

@Preview
@Composable
fun ForexPairsListPreview() {
    ForexViewerTheme {
        FavoritesList(
            favorites = listOf(
                UiFavorite(
                    symbol = "EUR/USD",
                    base = "Euro",
                    quote = "US Dollar",
                    exchangeRate = 1.1032
                ),
                UiFavorite(
                    symbol = "GBP/USD",
                    base = "British Pound",
                    quote = "US Dollar",
                    exchangeRate = 1.2587
                ),
                UiFavorite(
                    symbol = "USD/JPY",
                    base = "US Dollar",
                    quote = "Japanese Yen",
                    exchangeRate = 144.8734
                )
            ),
            contentPadding = PaddingValues(16.dp),
            onClick = {}
        )
    }
}