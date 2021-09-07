package com.acv.movieredux.ui.movielist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.acv.movieredux.Store
import com.acv.movieredux.data.models.Keyword
import com.acv.movieredux.store
import com.acv.movieredux.ui.common.MoviesListProps
import com.acv.movieredux.ui.common.moviesListProps
import com.acv.movieredux.ui.movielist.base.MoviesSearchTextWrapper
import com.acv.movieredux.ui.movielist.rows.MovieRow
import com.acv.movieredux.ui.movielist.rows.PeopleRow
import com.acv.movieredux.ui.shared.SearchAppBar

@Composable
fun MoviesList(
    movies: List<String>,
    displaySearch: Boolean,
    pageListener: () -> Unit,
//    pageListener: MoviesMenuListPageListener,
    headerView: (@Composable () -> Unit)?
) {
    val searchFilter: String = SearchFilter.movies.name
    val searchTextWrapper = MoviesSearchTextWrapper
//    val searchTextWrapper by remember { mutableStateOf("") }
    val isSearching = false

    val searchedKeywords by Store.useSelector {
        moviesState.searchKeywords
    }

    val props by Store.useSelector {
        moviesListProps(searchTextWrapper.searchText.value, isSearching)(this, store.dispatch)
    }

    LazyColumn {
        if (displaySearch)
            item {
                SearchAppBar(
                    search = searchTextWrapper.searchText.value,
                    onSearchCange = { searchTextWrapper.onUpdateTextDebounced(it) }
                )
            }
        if (headerView != null && !isSearching) {
            item {
                headerView()
            }
        }

        if (isSearching) {
            item {
                SearchFilterView()
                if (searchedKeywords.isNotEmpty() && searchFilter == SearchFilter.movies.name) {
                    KeywordsSection(searchedKeywords[searchFilter]!!)
                }
            }
        }

        if (isSearching && searchFilter == SearchFilter.peoples.name) {
            item { PeoplesSection(isSearching, props) }
        } else {
            MovieSection(isSearching, props, searchTextWrapper)
        }

        pageListener()
//        if (!movies.isEmpty() || props.searchedMovies?.isEmpty() == false) {
//            //appear
//            if (isSearching && props.searchedMovies?.isEmpty() == false) {
//                pageListener()
////                searchTextWrapper.searchPageListener.currentPage += 1
//            } else if (pageListener != null && !isSearching && !movies.isEmpty()) {
//                pageListener()
////                pageListener?.currentPage += 1
//            }
//        }
    }
}

private fun LazyListScope.MovieSection(
    isSearching: Boolean,
    props: MoviesListProps,
    searchTextWrapper: MoviesSearchTextWrapper
) {
    if (isSearching) {
        item { Text("Results for ${searchTextWrapper}") }
        item {
            if (isSearching && props.searchedMovies == null) {
                Text(text = "Searching movies...")
            } else if (isSearching && props.searchedMovies?.isEmpty() == true) {
                Text(text = "No results")
            } else {
                MoviesRows(props.searchedMovies!!)
            }
        }
    } else {
        item { MoviesRows(emptyList()) }
    }
}

@Composable
private fun MoviesRows(
    searchedMovies: List<String>,
) {
    searchedMovies.forEach {
        MovieRow(it)
    }
//    ForEach(isSearching ? props . searchedMovies ?? [] : movies.map { id in id }, id: \.self) {
//        id in
//                NavigationLink(destination: MovieDetail(movieId: id)) {
//        MovieRow(movieId: id)
//    }
//    }
}

@Composable
private fun SearchFilterView() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
                Text("Movies")
            }
            Divider()
            DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                Text("People")
            }
        }
    }
}

@Composable
private fun KeywordsSection(keywords: List<Keyword>) {
    LazyRow {
        items(keywords) {
            Text(text = it.name)
        }
    }
}

@Composable
private fun PeoplesSection(
    isSearching: Boolean,
    props: MoviesListProps
) {
    if (isSearching && props.searchedPeoples == null) {
        Text("Searching peoples...")
    } else if (isSearching && props.isEmptySearch()) {
        Text("No results")
    } else {
        LazyColumn {
            items(props.searchedPeoples!!) {
                PeopleRow(peopleId = it)
            }
        }
    }
}


private enum class SearchFilter {
    movies, peoples
}