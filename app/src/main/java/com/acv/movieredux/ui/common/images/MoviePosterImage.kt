package com.acv.movieredux.ui.common.images

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.acv.movieredux.ui.modifier.Size
import com.acv.movieredux.ui.modifier.posterStyle
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun MoviePosterImage(
    posterSize: Size,
    image: String?
) {
    Box {
        if (image != null) {
            Image(
                painter = rememberCoilPainter(request = image),
                modifier = Modifier
                    .posterStyle(true, posterSize)
                    .animateContentSize(),
                contentDescription = "poster"
            )
        } else {
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .posterStyle(false, posterSize)
            )
        }
    }
}
