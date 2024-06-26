package com.gzaber.forexviewer.ui.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.util.model.UiFavorite
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun FavoriteListItem(
    favorite: UiFavorite,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(favorite.symbol)
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
                Text(text = favorite.symbol, fontWeight = FontWeight.Bold)
                Text(text = "${favorite.base} / ${favorite.quote}")
            }
            Text(
                text = "${favorite.exchangeRate}",
                color = if (favorite.exchangeRate == 0.0) Color.Red else MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
        HorizontalDivider()
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