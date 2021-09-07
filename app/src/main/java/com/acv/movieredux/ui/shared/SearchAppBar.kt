package com.acv.movieredux.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchAppBar(
    search: String,
    onSearchCange: (String) -> Unit,
) {
    TopAppBar(
        modifier = Modifier
            .padding(12.dp)
            .border(
                BorderStroke(1.dp, Color.LightGray),
                RoundedCornerShape(50)
            ),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = 8.dp),
            imageVector = Icons.Filled.Search,
            contentDescription = "Search"
        )
        Box(
            modifier = Modifier.weight(1f),
        ) {
            if (search.isEmpty())
                Text(text = "Search")

            BasicTextField(
                value = search,
                onValueChange = onSearchCange
            )
        }
        if (search.isNotBlank())
            IconButton(onClick = { onSearchCange("") }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear"
                )
            }

        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search"
        )
    }
}