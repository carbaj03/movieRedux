package com.acv.movieredux.redux

interface Action

object NoAction : Action

fun interface AsyncAction<S : StoreState> : Action {
    operator fun invoke(
        state: S,
        dispatcher: Dispatcher,
    )
}

fun <S : StoreState> thunk(
    f: (dispatcher: Dispatcher, state: S, a: Any) -> Unit
): AsyncAction<S> =
    AsyncAction { state, dispatcher ->
        f(dispatcher, state, "null")
    }

fun interface Dispatcher {
    operator fun invoke(action: Action): Action
}