package com.acv.movieredux.domain.reducers

import com.acv.movieredux.data.models.CustomList
import com.acv.movieredux.data.models.Genre
import com.acv.movieredux.data.models.Movie
import com.acv.movieredux.data.models.MovieUserMeta
import com.acv.movieredux.domain.MoviesState
import com.acv.movieredux.domain.actions.MoviesActions
import com.acv.movieredux.domain.actions.PeopleActions
import com.soywiz.klock.DateTime

fun moviesStateReducer(state: MoviesState, action: Any): MoviesState =
    when (action) {
        is MoviesActions.SetMovieMenuList -> {
            val a = if (action.page == 1) {
                state.copy(
                    moviesList = state.moviesList + (action.moviesMenu to action.response.results.map { it.id })
                )
            } else {
                var list = state.moviesList[action.moviesMenu]
                if (list != null) {
                    list = list.plus(action.response.results.map { it.id })
                    state.copy(
                        moviesList = state.moviesList + (action.moviesMenu to list)
                    )
                } else {
                    state.copy(
                        moviesList = state.moviesList + (action.moviesMenu to action.response.results.map { it.id })
                    )
                }
            }
            state.copy(movies = a.movies.plus(action.response.results))
        }
        is MoviesActions.SetDetail -> state.copy(
            movies = state.movies + (action.movie to action.response)
        )
        is MoviesActions.SetRecommended -> {
            state.recommended[action.movie] = action.response.results.map { it.id }
            mergeMovies(movies = action.response.results, state = state)
        }
        is MoviesActions.SetSimilar -> {
            state.similar[action.movie] = action.response.results.map { it.id }
            mergeMovies(movies = action.response.results, state = state)
        }
        is MoviesActions.SetSearch -> {
            val a = if (action.page == 1) {
                state.copy(search = state.search + (action.query to action.response.results.map { it.id }))
            } else {
                state.copy(search = state.search + (action.query to state.search[action.query]!!.plus(action.response.results.map { it.id })))
            }
            mergeMovies(movies = action.response.results, state = a)
        }
        is MoviesActions.SetSearchKeyword ->
            state.copy(searchKeywords = state.searchKeywords + (action.query to action.response.results))
        is MoviesActions.AddToWishlist -> {
            val meta = state.moviesUserMeta[action.movie] ?: MovieUserMeta()
            meta.addedToList = DateTime.now()
            state.copy(
                wishlist = state.wishlist.plus(action.movie),
                seenlist = state.seenlist.minus(action.movie),
                moviesUserMeta = state.moviesUserMeta + (action.movie to meta)
            )
        }
        is MoviesActions.RemoveFromWishlist ->
            state.copy(wishlist = state.wishlist.minus(action.movie))
        is MoviesActions.AddToSeenList -> {
            val meta = state.moviesUserMeta[action.movie] ?: MovieUserMeta()
            meta.addedToList = DateTime.now()
            state.copy(
                seenlist = state.seenlist.plus(action.movie),
                wishlist = state.wishlist.minus(action.movie),
                moviesUserMeta = state.moviesUserMeta + (action.movie to meta),
            )
        }
        is MoviesActions.RemoveFromSeenList ->
            state.copy(seenlist = state.seenlist.minus(action.movie))
        is MoviesActions.AddMovieToCustomList -> {
            val a: CustomList = state.customLists[action.list]!!
            val c: CustomList = a.copy(movies = state.customLists[action.list]!!.movies.plus(action.movie))
            state.copy(
                customLists = state.customLists + (action.list to c)
            )
        }
        is MoviesActions.AddMoviesToCustomList -> {
            var list: CustomList? = state.customLists[action.list]
            if (list != null) {
                for (movie in action.movies) {
                    list = list!!.copy(movies = list!!.movies.plus(movie))
                }
                state.copy(customLists = state.customLists + (action.list to list!!))
            } else state
        }
        is MoviesActions.RemoveMovieFromCustomList -> {
            val a: CustomList = state.customLists[action.list]!!
            val c: CustomList = a.copy(movies = state.customLists[action.list]!!.movies.minus(action.movie))
            state.copy(
                customLists = state.customLists + (action.list to c)
            )
        }
        is MoviesActions.SetMovieForGenre -> {
            val s = state.copy(
                withGenre = if (action.page == 1) {
                    state.withGenre + (action.genre.id to action.response.results.map { it.id })
                } else {
                    state.withGenre + (action.genre.id to state.withGenre[action.genre.id]!!.plus(action.response.results.map { it.id }))
                }
            )
            mergeMovies(movies = action.response.results, state = s)
        }
        is MoviesActions.SetRandomDiscover -> {
            val s = state.copy(
                discover = if (state.discover.isEmpty()) {
                    action.response.results.map { it.id }.toMutableList()
                } else if (state.discover.size < 10) {
                    action.response.results.map { it.id } + state.discover
                } else {
                    state.discover
                },
                discoverFilter = action.filter
            )
            mergeMovies(movies = action.response.results, state = s)
        }
        is MoviesActions.SetMovieReviews ->
            state.copy(reviews = state.reviews + (action.movie to action.response.results))
        is MoviesActions.SetMovieWithCrew -> {
            val a: List<String> = action.response.results.map { it.id }
            val s = state.copy(withCrew = state.withCrew + (action.crew to a))
            mergeMovies(movies = action.response.results, state = s)
        }
        is MoviesActions.SetMovieWithKeyword -> {
            val s = state.copy(
                withKeywords = if (action.page == 1) {
                    state.withKeywords + (action.keyword to action.response.results.map { it.id })
                } else {
                    state.withKeywords + (action.keyword to state.withKeywords[action.keyword]!!.plus(action.response.results.map { it.id }))
                }
            )
            mergeMovies(movies = action.response.results, state = s)
        }
        is MoviesActions.AddCustomList ->
            state.copy(
                customLists = state.customLists + (action.list.id to action.list)
            )
        is MoviesActions.EditCustomList -> {
            val list = state.customLists[action.list]
            state.copy(
                customLists = if (list != null) {
                    val cover = action.cover
                    if (cover != null) {
                        list.cover = cover
                    }
                    val title = action.title
                    if (title != null) {
                        list.name = title
                    }
                    state.customLists + (action.list to list)
                } else {
                    state.customLists
                }
            )
        }
        is MoviesActions.RemoveCustomList ->
            state.copy(customLists = state.customLists.minus(action.list))
        is MoviesActions.PopRandromDiscover ->
            state.copy(discover = state.discover.drop(1))
        is MoviesActions.PushRandomDiscover ->
            state.copy(discover = state.discover.plus(action.movie))
        is MoviesActions.ResetRandomDiscover -> {
            state.copy(
                discover = mutableListOf(),
                discoverFilter = null,
            )
        }
        is MoviesActions.SetGenres ->
            state.copy(
                genres = listOf(Genre(id = "-1", name = "Random")) + action.genres,
            )
        is PeopleActions.SetPeopleCredits -> {
            val crews = action.response.crew
            val casts = action.response.cast

            when {
                crews != null && casts != null -> {
                    mergeMovies(movies = crews, state = mergeMovies(movies = casts, state = state))
                }
                crews != null -> mergeMovies(movies = crews, state = state)
                casts != null -> mergeMovies(movies = casts, state = state)
                else -> state
            }
        }
        is MoviesActions.SaveDiscoverFilter ->
            state.copy(
                savedDiscoverFilters = state.savedDiscoverFilters.plus(action.filter)
            )
        is MoviesActions.ClearSavedDiscoverFilters ->
            state.copy(
                savedDiscoverFilters = mutableListOf()
            )
        else -> state
    }

operator fun Map<String, Movie>.plus(rhs: List<Movie>): Map<String, Movie> {
    var map = this
    for (movie in rhs) {
        map + (movie.id to movie)
    }
    return map
}

//fun <K, V> Map<K, V>.replace(key: K, values: V): Map<K, V> =
//    toMutableMap().apply { this[key] = values }

private fun mergeMovies(movies: List<Movie>, state: MoviesState): MoviesState {
    val map = state.movies.toMutableMap()
    for (movie in movies) {
        if (state.movies[movie.id] == null) {
            map[movie.id] = movie
        }
    }
    return state.copy(movies = map)
}