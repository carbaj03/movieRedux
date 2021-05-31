package com.acv.movieredux.domain.actions

import android.util.Log
import com.acv.movieredux.data.APIService
import com.acv.movieredux.data.APIService.Endpoint
import com.acv.movieredux.data.models.*
import com.acv.movieredux.data.onFailure
import com.acv.movieredux.data.onSuccess
import com.acv.movieredux.domain.AppState
import com.acv.movieredux.domain.MoviesMenu
import com.acv.movieredux.domain.actions.MoviesActions.*
import com.acv.movieredux.redux.Action
import com.acv.movieredux.redux.thunk
import com.acv.movieredux.ui.MoviesSort
import com.acv.movieredux.ui.preferences.AppUserDefaults
import com.example.common.models.DiscoverFilter
import com.acv.movieredux.data.models.Genre
import kotlinx.serialization.Serializable

class MoviesActionsAsync(
    private val apiService: APIService,
    private val appUserDefaults: AppUserDefaults,
) {
    fun fetchMoviesMenuList(list: MoviesMenu, page: Int) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<MoviePaginatedResponse>(
                endpoint = list.endpoint,
                params = mapOf(
                    "page" to page.toString(),
                    "region" to appUserDefaults.region
                )
            ) {
                onSuccess {
                    dispatch(
                        SetMovieMenuList(
                            page = page,
                            moviesMenu = list,
                            response = it
                        )
                    )
                }
                onFailure {
                    Log.e("error", it)
                }
            }
        }

    fun fetchDetail(movie: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<Movie>(
                endpoint = Endpoint.movieDetail(movie = movie),
                params = mapOf(
                    "append_to_response" to "keywords,images", "include_image_language" to
                            /*"${Locale.current.languageCode ?: */"en,en,null"
                )
            ) {
                onSuccess { dispatch(SetDetail(movie = movie, response = it)) }
                onFailure { }
            }
        }

    fun fetchRecommended(movie: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<MoviePaginatedResponse>(
                endpoint = Endpoint.recommended(
                    movie = movie
                ), params = null
            ) {
                onSuccess { dispatch(SetRecommended(movie = movie, response = it)) }
                onFailure { }
            }
        }

    fun fetchSimilar(movie: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<MoviePaginatedResponse>(
                endpoint = Endpoint.similar(movie = movie),
                params = null
            ) {
                onSuccess { dispatch(SetSimilar(movie = movie, response = it)) }
                onFailure { }
            }
        }

    fun fetchSearch(query: String, page: Int) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<MoviePaginatedResponse>(
                endpoint = Endpoint.searchMovie,
                params = mapOf("query" to query, "page" to "${page}")
            ) {
                onSuccess {
                    dispatch(
                        SetSearch(
                            query = query,
                            page = page,
                            response = it
                        )
                    )
                }
                onFailure { }
            }
        }

    fun fetchSearchKeyword(query: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<KeywordPaginatedResponse>(
                endpoint = Endpoint.searchKeyword,
                params = mapOf("query" to query)
            ) {
                onSuccess {
                    dispatch(
                        SetSearchKeyword(
                            query = query,
                            response = it
                        )
                    )
                }
                onFailure { }
            }
        }

    fun fetchMoviesGenre(genre: Genre, page: Int, sortBy: MoviesSort) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<MoviePaginatedResponse>(
                endpoint = Endpoint.discover,
                params = mapOf(
                    "with_genres" to "${genre.id}",
                    "page" to "$page",
                    "sort_by" to sortBy.sortByAPI
                )
            ) {
                onSuccess {
                    dispatch(
                        SetMovieForGenre(
                            genre = genre,
                            page = page,
                            response = it
                        )
                    )
                }
                onFailure { }
            }
        }

    fun fetchMovieReviews(movie: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<ReviewPaginatedResponse>(
                endpoint = Endpoint.review(
                    movie =
                    movie
                ), params = mapOf("language" to "en-US")
            ) {
                onSuccess { dispatch(SetMovieReviews(movie = movie, response = it)) }
                onFailure { }
            }
        }

    fun fetchMovieWithCrew(crew: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<MoviePaginatedResponse>(
                endpoint = Endpoint.discover,
                params = mapOf("with_people" to "$crew")
            ) {
                onSuccess { dispatch(SetMovieWithCrew(crew = crew, response = it)) }
                onFailure { }
            }
        }

    fun fetchMovieWithKeywords(keyword: String, page: Int) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<MoviePaginatedResponse>(
                endpoint = Endpoint.discover, params = mapOf(
                    "page" to "$page",
                    "with_keywords" to "$keyword"
                )
            ) {
                onSuccess {
                    dispatch(
                        SetMovieWithKeyword(
                            keyword = keyword,
                            page = page,
                            response = it
                        )
                    )
                }
                onFailure { }
            }
        }

    fun fetchRandomDiscover(filter: DiscoverFilter? = null) =
        thunk<AppState> { dispatch, _, _ ->
            var filter = filter
            if (filter == null) {
                filter = DiscoverFilter.randomFilter()
            }
            apiService.GET<MoviePaginatedResponse>(
                endpoint = Endpoint.discover,
                params = filter.toParams()
            ) {
                onSuccess {
                    dispatch(
                        SetRandomDiscover(filter = filter, response = it)
                    )
                }
                onFailure { }
            }
        }

    @Serializable
    data class GenresResponse(val genres: List<Genre>)

    fun fetchGenres() =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<GenresResponse>(endpoint = Endpoint.genres, params = null) {
                onSuccess { dispatch(SetGenres(genres = it.genres)) }
                onFailure { }
            }
        }
}

sealed class MoviesActions : Action {
    data class SetMovieMenuList(
        val page: Int,
        val moviesMenu: MoviesMenu,
        val response: MoviePaginatedResponse
    ) : MoviesActions()

    data class SetDetail(
        val movie: String,
        val response: Movie
    ) : MoviesActions()

    data class SetRecommended(
        val movie: String,
        val response: MoviePaginatedResponse
    ) : MoviesActions()

    data class SetSimilar(
        val movie: String,
        val response: MoviePaginatedResponse
    ) : MoviesActions()

    data class KeywordResponse(
        val id: String,
        val keywords: List<Keyword>
    ) : MoviesActions()

    data class SetSearch(
        val query: String,
        val page: Int,
        val response: MoviePaginatedResponse
    ) : MoviesActions()

    data class SetGenres(
        val genres: List<Genre>
    ) : MoviesActions()

    data class SetSearchKeyword(
        val query: String,
        val response: KeywordPaginatedResponse
    ) : MoviesActions()

    data class AddToWishlist(
        val movie: String
    ) : MoviesActions()

    data class RemoveFromWishlist(
        val movie: String
    ) : MoviesActions()

    data class AddToSeenList(
        val movie: String
    ) : MoviesActions()

    data class RemoveFromSeenList(
        val movie: String
    ) : MoviesActions()

    data class SetMovieForGenre(
        val genre: Genre,
        val page: Int,
        val response: MoviePaginatedResponse
    ) : MoviesActions()

    data class SetMovieWithCrew(
        val crew: String,
        val response: MoviePaginatedResponse
    ) : MoviesActions()

    data class SetMovieWithKeyword(
        val keyword: String,
        val page: Int,
        val response: MoviePaginatedResponse
    ) : MoviesActions()

    object ResetRandomDiscover : MoviesActions()

    data class SetRandomDiscover(
        val filter: DiscoverFilter,
        val response: MoviePaginatedResponse
    ) : MoviesActions()

    data class PushRandomDiscover(
        val movie: String
    ) : MoviesActions()

    object PopRandromDiscover : MoviesActions()

    data class SetMovieReviews(
        val movie: String,
        val response: ReviewPaginatedResponse
    ) : MoviesActions()

    data class AddCustomList(
        val list: CustomList
    ) : MoviesActions()

    data class EditCustomList(
        val list: String,
        val title: String?,
        val cover: String?
    ) : MoviesActions()

    data class AddMovieToCustomList(
        val list: String,
        val movie: String
    ) : MoviesActions()

    data class AddMoviesToCustomList(
        val list: String,
        val movies: List<String>
    ) : MoviesActions()

    data class RemoveMovieFromCustomList(
        val list: String,
        val movie: String
    ) : MoviesActions()

    data class RemoveCustomList(
        val list: String
    ) : MoviesActions()

    data class SaveDiscoverFilter(
        val filter: DiscoverFilter
    ) : MoviesActions()

    object ClearSavedDiscoverFilters : MoviesActions()
}

