package com.acv.movieredux.domain

import com.acv.movieredux.data.APIService
import com.acv.movieredux.redux.StoreState
import kotlinx.serialization.Serializable

data class AppState(
    val moviesState: MoviesState = MoviesState(),
    val peoplesState: PeoplesState = PeoplesState()
) : StoreState {

    fun getSaveState() = copy(
        moviesState = moviesState.getSaveState(),
        peoplesState = peoplesState.getSaveState()
    )

    companion object {
        fun initialValue() = AppState()
    }
}

enum class MoviesMenu(
    val title: String,
    val endpoint: APIService.Endpoint
) {
    popular("Popular", APIService.Endpoint.popular),
    topRated("Top Rated", APIService.Endpoint.topRated),
    upcoming("Upcoming", APIService.Endpoint.upcoming),
    nowPlaying("Now Playing", APIService.Endpoint.nowPlaying),
    trending("Trending", APIService.Endpoint.trending),
    genres("Genres", APIService.Endpoint.genres);

    companion object {
        fun allValues(): List<MoviesMenu> = values().toList()
        fun fromOrdinal(ordinal: Int): MoviesMenu = MoviesMenu.values()[ordinal]
    }
}