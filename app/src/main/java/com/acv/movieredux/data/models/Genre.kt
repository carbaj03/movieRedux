package com.acv.movieredux.data.models

import kotlinx.serialization.Serializable


@Serializable
data class Genre(
    val id: String,
    val name: String
)
