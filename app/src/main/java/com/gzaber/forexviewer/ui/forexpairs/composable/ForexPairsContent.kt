package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun ForexPairsContent(
    forexPairs: List<UiForexPair>,
    searchText: String,
    forexGroups: List<String>,
    selectedGroup: String,
    contentPadding: PaddingValues,
    onListItemClick: (String) -> Unit,
    onFavoriteClick: (UiForexPair) -> Unit,
    onForexGroupClick: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onClearSearchText: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        ForexGroupsMenu(
            values = forexGroups,
            selectedValue = selectedGroup,
            onValueClick = onForexGroupClick
        )
        SearchTextField(
            searchText = searchText,
            onSearchTextChange = onSearchTextChange,
            onClearSearchText = onClearSearchText
        )
        ForexPairsList(
            forexPairs = forexPairs,
            onListItemClick = onListItemClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@Preview
@Composable
fun ForexPairsContentPreview() {
    ForexViewerTheme {
        ForexPairsContent(
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
            searchText = "",
            forexGroups = listOf("ALL", "MAJOR", "MINOR", "EXOTIC", "EXOTIC-CROSS"),
            selectedGroup = "MAJOR",
            contentPadding = PaddingValues(0.dp),
            onListItemClick = {},
            onFavoriteClick = {},
            onForexGroupClick = {},
            onSearchTextChange = {},
            onClearSearchText = {}
        )
    }
}