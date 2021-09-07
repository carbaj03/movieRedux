package com.acv.movieredux.ui.common

import com.acv.movieredux.data.models.Keyword
import com.acv.movieredux.domain.AppState
import com.acv.movieredux.redux.Dispatcher


data class MoviesListProps(
    val searchedMovies: List<String>?,
    val searchedKeywords: List<Keyword>?,
    val searchedPeoples: List<String>?,
    val recentSearches: List<String>,
) {
    fun isEmptySearch() = searchedPeoples?.isEmpty() == true
}


fun moviesListProps(searchText: String, isSearching: Boolean): PropsMapper<MoviesListProps> {
    fun map(state: AppState, dispatch: Dispatcher): MoviesListProps {
        val x = state.moviesState.search[searchText]
        if (isSearching) {
            return MoviesListProps(
                searchedMovies = x,
                searchedKeywords = state.moviesState.searchKeywords[searchText]?.take(5)?.map { it },
                searchedPeoples = state.peoplesState.search[searchText],
                recentSearches = state.moviesState.recentSearches.map { it })
        }
        return MoviesListProps(
            searchedMovies = null,
            searchedKeywords = null,
            searchedPeoples = null,
            recentSearches = listOf()
        )
    }
    return ::map
}

