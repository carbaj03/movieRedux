package com.acv.movieredux.ui

import com.acv.movieredux.domain.AppState
import com.soywiz.klock.DateTime

enum class MoviesSort(val title: String, val sortByAPI: String) {
    byReleaseDate("by release date", "release_date.asc"),
    byAddedDate("by added date", "primary_release_dat.desc"),
    byScore("by rating", "vote_average.desc"),
    byPopularity("by populartiy", "popularity.desc")
}

fun Collection<String>.sortedMoviesIds(by: MoviesSort, state: AppState): List<String> =
    when (by) {
        MoviesSort.byAddedDate ->
            state.moviesState.moviesUserMeta
                .filter { contains(it.key) }
                .entries.sortedBy { it.value.addedToList ?: DateTime.now() > it.value.addedToList ?: DateTime.now() }
                .map { it.key }

        MoviesSort.byReleaseDate ->
            state.moviesState.movies
                .filter { this.contains(it.key) }
                .entries.sortedBy { it.value.releaseDate > it.value.releaseDate }
                .map { it.key }

        MoviesSort.byPopularity ->
            state.moviesState.movies
                .filter { this.contains(it.key) }
                .entries
                .sortedBy { it.value.popularity > it.value.popularity }
                .map { it.key }

        MoviesSort.byScore ->
            state.moviesState.movies
                .filter { this.contains(it.key) }
                .entries.sortedBy { it.value.vote_average > it.value.vote_average }
                .map { it.key }
    }

