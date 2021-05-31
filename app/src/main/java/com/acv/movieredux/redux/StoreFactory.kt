package com.acv.movieredux.redux

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map


fun <S : StoreState> createStore(
    reducer: Reducer<S>,
    preloadedState: S,
    enhancer: StoreEnhancer<S>? = null,
): Store<S> {
    if (enhancer != null) {
        return enhancer { r, initialState, _ -> createStore(r, initialState) }(
            reducer,
            preloadedState,
            null
        )
    }

    val currentState = MutableStateFlow(preloadedState)

    val dispatcher = Dispatcher { action ->
        currentState.value = reducer(currentState.value, action)
        action
    }

    return object : Store<S> {
        override var dispatch: Dispatcher = dispatcher
        override val state: StateFlow<S> = currentState
    }
}


interface StoreProvider<S : StoreState> {
    @Composable
    operator fun invoke(content: @Composable () -> Unit)

    @Composable
    fun <A> useSelector(f: S.() -> A): State<A>

    @Composable
    fun useDispatch(): State<(Action) -> Unit>
}

fun <S : StoreState> provide(store: Store<S>): StoreProvider<S> {
    val LocalStore: ProvidableCompositionLocal<Store<S>> =
        staticCompositionLocalOf { store }

    return object : StoreProvider<S> {
        @Composable
        override fun invoke(content: @Composable () -> Unit) =
            CompositionLocalProvider(
                LocalStore provides store
            ) {
                content()
            }

        @Composable
        override fun <A> useSelector(f: S.() -> A): State<A> {
            val store: Store<S> = LocalStore.current
            val selector: Flow<A> = store.state.map { f(it) }
            return selector.collectAsState(f(store.state.value))
        }

        @Composable
        override fun useDispatch(): State<(Action) -> Unit> {
            val store: Store<S> = LocalStore.current
            return remember { mutableStateOf({ action: Action -> store.dispatch(action) }) }
        }
    }
}