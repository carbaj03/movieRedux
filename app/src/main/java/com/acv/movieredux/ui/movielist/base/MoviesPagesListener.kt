package com.acv.movieredux.ui.movielist.base;

abstract class MoviesPagesListener {
    var currentPage: Int = 1
        set(value) {
            loadPage()
            field = value
        }

    abstract fun loadPage()
}