package com.acv.movieredux.ui.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun TextAndIconTabs(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
) {
    var state by remember { mutableStateOf(0) }

    Column {
        ScrollableTabRow(
            selectedTabIndex = state,
            backgroundColor = Color.Transparent,
        ) {
            options.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = state == index,
                    onClick = {
                        state = index
                        onSelected(options[index])
                    }
                )
            }
        }
    }
}