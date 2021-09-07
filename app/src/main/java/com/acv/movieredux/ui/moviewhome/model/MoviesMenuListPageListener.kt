package com.acv.movieredux.ui.moviewhome.model;

import com.acv.movieredux.domain.MoviesMenu
import com.acv.movieredux.movieActions
import com.acv.movieredux.store
import com.acv.movieredux.ui.movielist.base.MoviesPagesListener

class MoviesMenuListPageListener(newMenu: MoviesMenu) : MoviesPagesListener() {
    init {
        newMenu.also { menu = it }
        loadPage()
    }

    var menu: MoviesMenu
        set(value) {
            currentPage = 1
            field = value
        }

    override fun loadPage() {
        store.dispatch(movieActions.fetchMoviesMenuList(menu?: MoviesMenu.popular, 1))
    }
}
