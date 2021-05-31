package com.acv.movieredux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.acv.movieredux.domain.AppState
import com.acv.movieredux.domain.MoviesMenu
import com.acv.movieredux.redux.StoreProvider
import com.acv.movieredux.redux.provide
import com.acv.movieredux.ui.screen.HomeScreen
import com.acv.movieredux.ui.theme.MovieReduxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Store {
                MovieReduxTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        HomeScreen()
                    }
                }
            }
        }

        store.dispatch(movieActions.fetchMoviesMenuList(list = MoviesMenu.nowPlaying, page = 1))
        store.dispatch(movieActions.fetchGenres())
    }
}

val Store: StoreProvider<AppState> =
    provide(store = store)

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieReduxTheme {
        Greeting("Android")
    }
}