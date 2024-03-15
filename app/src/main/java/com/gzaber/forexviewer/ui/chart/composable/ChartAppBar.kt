package com.gzaber.forexviewer.ui.chart.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gzaber.forexviewer.R
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartAppBar(
    title: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onFavoriteClick,
                enabled = !isLoading
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.favorite)
                )
            }
        }
    )
}

@Preview
@Composable
fun ChartAppBarPreview() {
    ForexViewerTheme {
        ChartAppBar(
            title = "EUR/USD",
            isFavorite = false,
            onBackClick = { },
            onFavoriteClick = { }
        )
    }
}