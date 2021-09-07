package com.acv.movieredux.domain

import com.acv.movieredux.data.models.*
import com.example.common.models.DiscoverFilter

data class MoviesState(
    val movies: Map<String, Movie> = emptyMap(),
    val moviesList: Map<MoviesMenu, List<String>> = emptyMap(),
    val recommended: MutableMap<String, List<String>> = mutableMapOf(),
    val similar: MutableMap<String, List<String>> = mutableMapOf(),
    val search: Map<String, List<String>> = mutableMapOf(),
    val searchKeywords: Map<String, List<Keyword>> = mutableMapOf(),
    val recentSearches: Set<String> = setOf(),
    val moviesUserMeta: Map<String, MovieUserMeta> = mutableMapOf(),
    val discover: List<String> = mutableListOf(),
    val discoverFilter: DiscoverFilter? = null,
    val savedDiscoverFilters: List<DiscoverFilter> = mutableListOf(),
    val wishlist: Set<String> = setOf(),
    val seenlist: Set<String> = setOf(),
    val withGenre: Map<String, List<String>> = mutableMapOf(),
    val withKeywords: Map<String, List<String>> = mutableMapOf(),
    val withCrew: Map<String, List<String>> = mutableMapOf(),
    val reviews: Map<String, List<Review>> = mutableMapOf(),
    val customLists: Map<String, CustomList> = mutableMapOf(),
    val genres: List<Genre> = mutableListOf()
) {
    enum class CodingKeys(val rawValue: String) {
        movies("movies"),
        wishlist("wishlist"),
        seenlist("seenlist"),
        customLists("customLists"),
        moviesUserMeta("moviesUserMeta"),
        savedDiscoverFilters("savedDiscoverFilters");

        companion object {
            operator fun invoke(rawValue: String) =
                CodingKeys.values().firstOrNull { it.rawValue == rawValue }
        }
    }

    fun withMovieId(movieId: String): Movie =
        movies[movieId]!!

    fun withCrewId(crewId: String): List<String> =
        withCrew[crewId] ?: listOf()

    fun withListId(listId: String): CustomList? =
        customLists[listId]

    fun withKeywordId(keywordId: String) =
        withKeywords[keywordId]!!.toList()

    fun withGenreId(genreId: String): List<String> =
        withGenre[genreId] ?: listOf()

    fun getGenreById(genreId: String): Genre? =
        genres.find { it.id == genreId }

    fun search(searchText: String): List<String>? =
        search[searchText]?.toList()

    val customListsList: List<CustomList>
        get() = customLists.values.toList()

    fun reviewByMovieId(movieId: String): List<Review>? =
        reviews[movieId]

    fun recommendedMovies(movieId: String): List<Movie>? =
        recommended[movieId]?.filter { movies.containsKey(it) }?.mapNotNull { movies[it] }

    fun similarMovies(movieId: String): List<Movie>? =
        similar[movieId]?.filter { movies.containsKey(it) }?.mapNotNull { movies[it] }

    fun getMoviesToSave(): Map<String, Movie> =
        movies.filter { shouldSaveMovie(it.value.id) }

    fun getSaveState(): MoviesState =
        MoviesState(movies = getMoviesToSave(), seenlist = seenlist, wishlist = wishlist, customLists = customLists)

    private fun shouldSaveMovie(movieId: String): Boolean = seenlist.contains(movieId)
            || wishlist.contains(movieId)
            || customLists.any { it.value.movies.contains(movieId) || it.value.cover == movieId }

//    val moviesFiltered  get() = movies.filter { it.value.title.contains(sea) }
}