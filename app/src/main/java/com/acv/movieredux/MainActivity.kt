package com.acv.movieredux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.acv.movieredux.domain.AppState
import com.acv.movieredux.domain.MoviesMenu
import com.acv.movieredux.redux.StoreProvider
import com.acv.movieredux.redux.provide
import com.acv.movieredux.ui.theme.MovieReduxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Store {
                MovieReduxTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        val custom by Store.useSelector {
                            moviesState
                        }

                        val dispatcher by Store.useDispatch()

                        dispatcher(movieActions.fetchMoviesMenuList(list = MoviesMenu.nowPlaying, page = 2))

                        LazyColumn {
                            items(custom.movies.values.toList()) {
                                Text(text = it.title)
                            }
                        }
                    }
                }
            }
        }
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