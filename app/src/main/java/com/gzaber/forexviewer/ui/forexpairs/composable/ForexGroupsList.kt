package com.gzaber.forexviewer.ui.forexpairs.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gzaber.forexviewer.ui.forexpairs.ForexGroupsFilterType
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme

@Composable
fun ForexGroupsList(
    onGroupClick: (ForexGroupsFilterType) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGroup by remember {
        mutableStateOf(ForexGroupsFilterType.ALL)
    }

    LazyRow(
        modifier = modifier
    ) {
        items(ForexGroupsFilterType.entries) { filter ->
            Text(
                text = filter.group.uppercase(),
                fontWeight = if (filter == selectedGroup) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .background(
                        if (filter == selectedGroup) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.background
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        selectedGroup = filter
                        onGroupClick(selectedGroup)
                    },
            )
        }
    }
}

@Preview
@Composable
fun ForexGroupsListPreview() {
    ForexViewerTheme {
        ForexGroupsList(onGroupClick = {})
    }
}