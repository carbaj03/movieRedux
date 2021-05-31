package com.acv.movieredux.ui.screen

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Movies : Screen("moviews", com.acv.movieredux.R.string.movies)
    object Discover : Screen("discover", com.acv.movieredux.R.string.discover)
    object FanClub : Screen("fanclub", com.acv.movieredux.R.string.fan_club)
    object MyList : Screen("mylist", com.acv.movieredux.R.string.my_list)

    companion object {
        fun screens() = listOf(Movies, Discover, FanClub, MyList)
    }
}

@Composable
fun HomeScreen() {
    val items = Screen.screens()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Movies.route
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.startDestinationRoute!!) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Movies.route
        ) {
            composable(Screen.Movies.route) {

            }
            composable(Screen.Discover.route) {

            }
            composable(Screen.FanClub.route) {

            }
            composable(Screen.MyList.route) {

            }
        }
    }
}