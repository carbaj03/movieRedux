package com.acv.movieredux.domain.actions

import com.acv.movieredux.data.APIService
import com.acv.movieredux.data.APIService.Endpoint.*
import com.acv.movieredux.data.models.*
import com.acv.movieredux.data.onFailure
import com.acv.movieredux.data.onSuccess
import com.acv.movieredux.domain.AppState
import com.acv.movieredux.domain.actions.PeopleActions.*
import com.acv.movieredux.redux.Action
import com.acv.movieredux.redux.thunk
import kotlinx.serialization.Serializable

class PeopleActionsAsync(
    private val apiService: APIService
) {

    fun fetchDetail(people: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<People>(
                endpoint = personDetail(person = people),
                params = null
            ) {
                onSuccess { dispatch(SetDetail(person = it)) }
                onFailure { }
            }
        }

    @Serializable
    data class ImagesResponse(
        val id: Int,
        val profiles: List<ImageData>
    )

    fun fetchImages(people: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<ImagesResponse>(
                endpoint = personImages(person = people),
                params = null
            ) {
                onSuccess { dispatch(SetImages(people = people, images = it.profiles)) }
                onFailure {}
            }
        }

    @Serializable
    data class PeopleCreditsResponse(
        val cast: List<Movie>?,
        val crew: List<Movie>?
    )

    fun fetchPeopleCredits(people: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<PeopleCreditsResponse>(
                endpoint = personMovieCredits(person = people),
                params = null
            ) {
                onSuccess { dispatch(SetPeopleCredits(people = people, response = it)) }
                onFailure { }
            }
        }

    fun fetchMovieCasts(movie: String) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<CastResponse>(endpoint = credits(movie = movie), params = null) {
                onSuccess { dispatch(SetMovieCasts(movie = movie, response = it)) }
                onFailure { }
            }
        }

    fun fetchSearch(query: String, page: Int) =
        thunk<AppState> { dispatch, _, _ ->
            apiService.GET<PeoplePaginatedResponse>(
                endpoint = searchPerson,
                params = mapOf("query" to query, "page" to page.toString())
            ) {
                onSuccess { dispatch(SetSearch(query = query, page = page, response = it)) }
                onFailure {}
            }
        }

    fun fetchPopular(page: Int) =
        thunk<AppState> { dispatch, getState, extraArgument ->
            apiService.GET<PeoplePaginatedResponse>(
                endpoint = popularPersons,
                params = mapOf("page" to "$page", "region" to "us")
            ) {
                onSuccess { dispatch(SetPopular(page = page, response = it)) }
                onFailure { }
            }
        }
}

sealed class PeopleActions : Action {
    data class SetDetail(val person: People) : PeopleActions()

    data class SetImages(
        val people: String,
        val images: List<ImageData>
    ) : PeopleActions()

    data class SetMovieCasts(
        val movie: String,
        val response: CastResponse
    ) : PeopleActions()

    data class SetSearch(
        val query: String,
        val page: Int,
        val response: PeoplePaginatedResponse
    ) : PeopleActions()

    data class SetPopular(
        val page: Int,
        val response: PeoplePaginatedResponse
    ) : PeopleActions()

    data class SetPeopleCredits(
        val people: String,
        val response: PeopleActionsAsync.PeopleCreditsResponse
    ) : PeopleActions()

    data class AddToFanClub(val people: String) : PeopleActions()

    data class RemoveFromFanClub(val people: String) : PeopleActions()
}