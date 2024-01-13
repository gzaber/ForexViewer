package com.gzaber.forexviewer.ui.favorites.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.favorites.model.UiFavorite
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun FavoriteListItem(
    favorite: UiFavorite,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier.padding(16.dp)
            ) {
                Text(text = favorite.symbol, fontWeight = FontWeight.Bold)
                Text(text = "${favorite.base} / ${favorite.quote}")
            }
            Text(
                text = "${favorite.exchangeRate}",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
        Divider()
    }
}

@Preview
@Composable
fun FavoriteListItemPreview() {
    ForexViewerTheme {
        FavoriteListItem(
            favorite = UiFavorite(
                symbol = "EUR/USD",
                base = "Euro",
                quote = "US Dollar",
                exchangeRate = 1.1032
            ),
            onClick = {}
        )
    }
}