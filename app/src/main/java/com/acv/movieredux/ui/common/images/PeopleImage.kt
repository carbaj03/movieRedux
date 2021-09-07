package com.acv.movieredux.ui.common.images

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun PeopleImage(
    image: String?
) {
    Box {
        if (image != null) {
            Image(
                painter = rememberCoilPainter(request = image),
                modifier = Modifier
                    .border(BorderStroke(0.dp, Color.Black), RoundedCornerShape(10.dp))
                    .size(60.dp, 90.dp)
                    .animateContentSize(),
                contentDescription = "poster"
            )
        } else {
            Box(
                modifier = Modifier
                    .border(BorderStroke(0.dp, Color.Black), RoundedCornerShape(10.dp))
                    .size(60.dp, 90.dp)
                    .background(Color.Gray)
                    .alpha(0.1f)
            )
        }
    }
}