package com.acv.movieredux.ui.movielist.rows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.acv.movieredux.Store
import com.acv.movieredux.data.models.Movie
import com.acv.movieredux.movieActions
import com.acv.movieredux.ui.common.images.ListImage
import com.acv.movieredux.ui.common.images.MoviePosterImage
import com.acv.movieredux.ui.modifier.Size
import com.acv.movieredux.ui.shared.PopularityBadge
import com.acv.movieredux.ui.theme.SteamedGold

@Composable
fun MovieRow(
    movieId: String,
    displayListImage: Boolean = true
) {
    val movie: Movie by Store.useSelector {
        moviesState.movies[movieId]!!
    }

    Row(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            MoviePosterImage(posterSize = Size.Medium, image = movie.poster_path)
            if (displayListImage) {
                ListImage(movieId)
            }
        }

        Column(
            Modifier.padding(start = 8.dp)
        ) {
            Text(
                modifier = Modifier.background(SteamedGold),
                maxLines = 2,
                text = movie.userTitle(movieActions.appUserDefaults)
            )
//                .titleStyle()
            Row {
                PopularityBadge(movie.vote_count * 10)
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary),
                    text = movie.releaseDate,
                    maxLines = 1
                )
//                    .font(.subheadline)
            }
            Text(
                modifier = Modifier
                    .background(MaterialTheme.colors.secondary),
                text = movie.overview,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}