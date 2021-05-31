package com.acv.movieredux.redux


fun interface Reducer<S : StoreState> {
    operator fun invoke(state: S, action: Action): S
}

fun interface ReducerType<S : StoreState, A : Action> {
    operator fun S.invoke(action: A): S
}

inline fun <S : StoreState, reified A : Action> Reducer(
    reducer: ReducerType<S, A>
): Reducer<S> =
    Reducer { state, action ->
        when (action) {
            is A -> reducer.run { state(action) }
            else -> state
        }
    }

fun <S : StoreState> combineReducers(vararg reducers: Reducer<S>): Reducer<S> =
    Reducer { state, action ->
        reducers.fold(state, { s, reducer -> reducer(s, action) })
    }

fun <S : StoreState> combineEnhancer(vararg enhancers: StoreEnhancer<S>): StoreEnhancer<S> =
    StoreEnhancer { store ->
        enhancers.fold(store, { storeCreator, enhancer -> enhancer(storeCreator) })
    }

fun <T> compose(functions: List<(T) -> T>): (T) -> T =
    { x -> functions.foldRight(x, { f, composed -> f(composed) }) }


operator fun <S : StoreState> Reducer<S>.plus(other: Reducer<S>): Reducer<S> =
    Reducer { state, action ->
        other(this(state, action), action)
    }