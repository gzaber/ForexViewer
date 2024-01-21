package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun ForexGroupsMenu(
    values: List<String>,
    selectedValue: String,
    onValueClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
    ) {
        items(values) { value ->
            Text(
                text = value.replace("_", "-"),
                fontWeight = if (value == selectedValue) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .background(
                        if (value == selectedValue) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.background
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        onValueClick(value)
                    },
            )
        }
    }
}

@Preview
@Composable
fun ForexGroupsMenuPreview() {
    ForexViewerTheme {
        ForexGroupsMenu(
            values = listOf("ALL", "MAJOR", "MINOR", "EXOTIC", "EXOTIC-CROSS"),
            selectedValue = "MAJOR",
            onValueClick = {}
        )
    }
}