package com.acv.movieredux.ui

import android.util.Log
import com.acv.movieredux.domain.AppState
import com.acv.movieredux.redux.Action
import com.acv.movieredux.redux.Dispatcher
import com.acv.movieredux.redux.Middleware
import com.acv.movieredux.redux.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoggerMiddleware(
    override val coroutineContext: CoroutineContext,
) : Middleware<AppState>, CoroutineScope {
    override fun invoke(
        store: Store<AppState>,
        next: Dispatcher,
        action: Action,
    ): Action {
        launch {
            Log.e("logger", action.toString())
        }
        return next(action)
    }
}