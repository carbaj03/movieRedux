package com.acv.movieredux.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.acv.movieredux.Store
import com.acv.movieredux.domain.MoviesMenu
import com.acv.movieredux.movieActions

@Composable
fun MoviesScreen(

) {
    val dispatcher by Store.useDispatch()
    var menu by remember { mutableStateOf(MoviesMenu.nowPlaying) }

    val movies by Store.useSelector { moviesState.movies }
    dispatcher(movieActions.fetchMoviesMenuList(list = menu, page = 1))
    var search by remember { mutableStateOf("") }

    Column {

        Text(text = menu.title)

        TextField(
            value = search,
            onValueChange = {
                search = it
                dispatcher(movieActions.fetchSearch(it, 1))
            },
            placeholder = { Text(text = "Search") }
        )

        ChipGroup(
            options = MoviesMenu.values().map { it.name },
            selected = menu.title,
            onSelected = { menu = MoviesMenu.valueOf(it) }
        )

        LazyColumn {
            items(movies.values.toList()) {
                Text(text = it.title)
            }
        }
    }
}

@Composable
fun ChipGroup(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
) {
    var currentChip by remember { mutableStateOf(selected) }

    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        options.forEach { chip ->
            Chip(
                modifier = Modifier.padding(4.dp),
                text = chip,
                onSelected = {
                    currentChip = chip
                    onSelected(chip)
                },
                isSelected = currentChip == chip
            )
        }
    }
}

@Composable
fun Chip(
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        backgroundColor = if (isSelected) Color.Gray else Color.LightGray
    ) {
        Text(
            modifier = Modifier.clickable { onSelected() },
            text = text
        )
    }
}