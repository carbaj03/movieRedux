package com.acv.movieredux

import com.acv.movieredux.domain.AppState
import com.acv.movieredux.domain.actions.MoviesActionsAsync
import com.acv.movieredux.domain.reducers.appStateReducer
import com.acv.movieredux.redux.Store
import com.acv.movieredux.redux.ThunkMiddleware
import com.acv.movieredux.redux.applyMiddleware
import com.acv.movieredux.redux.createStore
import com.acv.movieredux.ui.LoggerMiddleware
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


fun create(initialState: AppState? = null): Store<AppState> =
    createStore(
        reducer = ::appStateReducer,
        preloadedState = initialState ?: AppState(),
        enhancer = applyMiddleware(
            ThunkMiddleware(),
            LoggerMiddleware(
                coroutineContext = Dispatchers.IO + SupervisorJob(),
            )
        )
    )