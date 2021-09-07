package com.acv.movieredux.ui.moviewhome.model;

import com.acv.movieredux.domain.MoviesMenu

class MoviesSelectedMenuStore(
    selectedMenu: MoviesMenu
) {
    val menu: MoviesMenu = selectedMenu
    val pageListener = MoviesMenuListPageListener(menu)
}
