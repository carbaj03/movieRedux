package com.acv.movieredux.data.models

import kotlinx.serialization.Serializable


interface PaginatedResponse<T> {
    val page: Int?
    val totalResults: Int?
    val totalPages: Int?
    val results: List<T>
}

@Serializable
data class MoviePaginatedResponse(
    override val page: Int? = null,
    override val totalResults: Int? = null,
    override val totalPages: Int? = null,
    override val results: List<Movie>
) : PaginatedResponse<Movie>

@Serializable
data class GenrePaginatedResponse(
    override val page: Int?,
    override val totalResults: Int?,
    override val totalPages: Int?,
    override val results: List<Genre>
) : PaginatedResponse<Genre>

@Serializable
data class PeoplePaginatedResponse(
    override val page: Int?,
    override val totalResults: Int?,
    override val totalPages: Int?,
    override val results: List<People>
) : PaginatedResponse<People>

@Serializable
data class ReviewPaginatedResponse(
    override val page: Int?,
    override val totalResults: Int?,
    override val totalPages: Int?,
    override val results: List<Review>
) : PaginatedResponse<Review>

@Serializable
data class KeywordPaginatedResponse(
    override val page: Int?,
    override val totalResults: Int?,
    override val totalPages: Int?,
    override val results: List<Keyword>
) : PaginatedResponse<Keyword>
