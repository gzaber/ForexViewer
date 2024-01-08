package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun ForexPairsAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.forex_pairs))
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back)
                )
            }
        }
    )
}

@Preview
@Composable
fun ForexPairsAppBarPreview() {
    ForexViewerTheme {
        ForexPairsAppBar(onBackClick = {})
    }
}