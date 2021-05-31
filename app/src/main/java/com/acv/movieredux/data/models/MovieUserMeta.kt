package com.acv.movieredux.data.models

import com.soywiz.klock.DateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class MovieUserMeta(
    @Transient
    var addedToList: DateTime? = null
)
