package com.acv.movieredux.domain.reducers

import com.acv.movieredux.domain.AppState

fun appStateReducer(state: AppState, action: Any): AppState =
    state.copy(
        moviesState = moviesStateReducer(state = state.moviesState, action = action),
        peoplesState = peoplesStateReducer(state.peoplesState, action)
    )
