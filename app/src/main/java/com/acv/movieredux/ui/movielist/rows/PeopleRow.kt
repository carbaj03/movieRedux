package com.acv.movieredux.ui.movielist.rows

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acv.movieredux.Store
import com.acv.movieredux.data.models.People
import com.acv.movieredux.data.models.knownForText
import com.acv.movieredux.ui.common.images.PeopleImage
import com.acv.movieredux.ui.theme.SteamedGold

@Composable
fun PeopleRow(
    peopleId: String
) {
    val isInFanClub by Store.useSelector {
        peoplesState.fanClub.contains(peopleId)
    }
    val people: People by Store.useSelector {
        peoplesState.peoples[peopleId]!!
    }
    Row() {
        PeopleImage(image = people.profile_path)
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Row() {
                if (isInFanClub) {
                    fanClubIcon()
                }
                Text(
                    modifier = Modifier
                        .background(SteamedGold)
                        .animateContentSize(),
                    text = people.name,
                    fontSize = 16.sp,
                    maxLines = 1
                )
            }
            Text(
                modifier = Modifier
                    .background(MaterialTheme.colors.secondary)
                    .height(40.dp),
                text = people.knownForText ?: "",
                maxLines = 3,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun fanClubIcon() {
    Image(
        modifier = Modifier
            .animateContentSize()
            .background(SteamedGold),
        contentScale = ContentScale.Fit,
        imageVector = Icons.Default.Star,
        contentDescription = ""
    )
}