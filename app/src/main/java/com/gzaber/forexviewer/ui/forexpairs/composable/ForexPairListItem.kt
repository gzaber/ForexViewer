package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.R
import com.gzaber.forexviewer.ui.util.model.UiForexPair
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun ForexPairListItem(
    forexPair: UiForexPair,
    onClick: (String) -> Unit,
    onFavoriteClick: (UiForexPair) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(forexPair.symbol)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier.padding(16.dp)
            ) {
                Text(text = forexPair.symbol, fontWeight = FontWeight.Bold)
                Text(text = "${forexPair.base} / ${forexPair.quote}")
                Text(text = forexPair.group)
            }
            IconButton(
                onClick = { onFavoriteClick(forexPair) }
            ) {
                Icon(
                    imageVector = if (forexPair.isFavorite)
                        Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.favorite)
                )
            }
        }
        HorizontalDivider()
    }
}

@Preview
@Composable
fun ForexPairListItemPreview() {
    ForexViewerTheme {
        ForexPairListItem(
            forexPair = UiForexPair(
                symbol = "EUR/USD",
                group = "Exotic-Cross",
                base = "Euro",
                quote = "US Dollar",
                isFavorite = true
            ),
            onClick = {},
            onFavoriteClick = {}
        )
    }
}