package com.acv.movieredux.ui.moviewhome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.acv.movieredux.Store
import com.acv.movieredux.domain.MoviesMenu
import com.acv.movieredux.ui.movielist.MoviesList

@Composable
fun MoviesHomeList(
    menu: MoviesMenu,
//    listener: MoviesMenuListPageListener,
    pageListener : () -> Unit,
    header: @Composable () -> Unit
) {
    val movies by Store.useSelector {
        moviesState.moviesList
    }

    MoviesList(
        movies = movies[menu] ?: emptyList(),
        displaySearch = true,
        pageListener = pageListener,
//        pageListener = listener,
        headerView = header
    )
}