package com.acv.movieredux.data.models

import com.acv.movieredux.domain.AppState
import com.acv.movieredux.ui.MoviesSort
import com.acv.movieredux.ui.sortedMoviesIds
import kotlinx.serialization.Serializable

@Serializable
data class CustomList(
    val id: String,
    var name: String,
    var cover: String? = null,
    var movies: Set<String>
) {
    fun sortedMoviesIds(by: MoviesSort, state: AppState) =
        movies.sortedMoviesIds(by, state)
}
