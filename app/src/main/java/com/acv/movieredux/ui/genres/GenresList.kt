package com.acv.movieredux.ui.genres

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.acv.movieredux.Store
import com.acv.movieredux.data.models.Genre
import com.acv.movieredux.movieActions

@Composable
fun GenresList(content : @Composable () -> Unit) {
    val dispatch by Store.useDispatch()
    val genres: List<Genre> by Store.useSelector { moviesState.genres }

    dispatch(movieActions.fetchGenres())

    LazyColumn {
        items(genres) {
            Text(text = it.name)
        }
    }
}