package com.acv.movieredux.ui.movielist.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.acv.movieredux.movieActions
import com.acv.movieredux.peopleActions
import com.acv.movieredux.store

class MoviesSearchPageListener : MoviesPagesListener() {
    var text: String = ""

    override fun loadPage() {
        val t = text
        if (t.isNotBlank()) {
            store.dispatch(movieActions.fetchSearchKeyword(t))
            store.dispatch(movieActions.fetchSearch(t, currentPage))
            store.dispatch(peopleActions.fetchSearch(t, currentPage))
        }
    }
}

object MoviesSearchTextWrapper {
    var searchText: MutableState<String> = mutableStateOf("")
    var searchPageListener = MoviesSearchPageListener()

    fun onUpdateTextDebounced(text: String) {
        searchText.value = text
        searchPageListener.text = text
        searchPageListener.currentPage = 1
    }
}