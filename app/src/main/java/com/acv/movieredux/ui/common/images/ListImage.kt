package com.acv.movieredux.ui.common.images

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.acv.movieredux.Store
import com.acv.movieredux.ui.common.ListImageProps


@Composable
fun ListImage(
    movieId: String
) {
    val props: ListImageProps by Store.useSelector {
        ListImageProps(
            isInwishlist = moviesState.wishlist.contains(movieId),
            isInSeenlist = moviesState.seenlist.contains(movieId),
            isInCustomList = moviesState.customLists
                .filterValues { it.movies.contains(movieId) }
                .isNotEmpty()
        )
    }

    Icon(props)?.let {
        Image(
            imageVector = it,
            modifier = Modifier
                .background(Color.White)
                .animateContentSize(),
            contentDescription = ""
        )
    }
}

@Composable
private fun Icon(props: ListImageProps): ImageVector? =
    when {
        props.isInwishlist -> Icons.Filled.Favorite
        props.isInSeenlist -> Icons.Filled.Search
        props.isInCustomList -> Icons.Filled.ShoppingCart
        else -> null
    }
