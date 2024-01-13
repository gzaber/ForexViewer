package com.gzaber.forexviewer.ui.favorites.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
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
fun FavoritesAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(id = R.string.update_api_key)
                )
            }
        },
        actions = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = stringResource(id = R.string.navigate_to_forex_pairs)
                )
            }
        }
    )
}

@Preview
@Composable
fun FavoritesAppBarPreview() {
    ForexViewerTheme {
        FavoritesAppBar(onBackClick = {})
    }
}