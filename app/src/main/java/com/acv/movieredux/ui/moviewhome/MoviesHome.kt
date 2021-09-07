package com.acv.movieredux.ui.moviewhome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.acv.movieredux.Store
import com.acv.movieredux.domain.MoviesMenu
import com.acv.movieredux.domain.allMovieMenuValues
import com.acv.movieredux.movieActions
import com.acv.movieredux.ui.genres.GenresList
import com.acv.movieredux.ui.moviewhome.model.MoviesSelectedMenuStore
import com.acv.movieredux.ui.shared.ScrollableSelector
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesHome() {
    val dispatch by Store.useDispatch()

    val selectedMenu by remember {
        mutableStateOf(MoviesSelectedMenuStore(MoviesMenu.popular))
    }
    var page by remember { mutableStateOf(1) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()



    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Swipe up to expand sheet")
            }
        },
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                IconButton(onClick = {
                    scope.launch { scaffoldState.bottomSheetState.expand() }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        }
    ) {
        if (selectedMenu.menu.name == MoviesMenu.genres.name) {
            GenresList {
                SegmentedView()
            }
        } else {
            MoviesHomeList(
                menu = selectedMenu.menu,
                pageListener = { page =+ 1


                    dispatch(movieActions.fetchMoviesMenuList(selectedMenu.menu, page))},
//                listener = selectedMenu.pageListener,
                header = { SegmentedView() }
            )
        }
    }
}

@Composable
fun SegmentedView() {
    ScrollableSelector(
        allMovieMenuValues().map { it.title },
    )
}