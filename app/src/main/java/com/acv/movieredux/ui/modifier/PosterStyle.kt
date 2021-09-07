package com.acv.movieredux.ui.modifier

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.posterStyle(
    loaded: Boolean,
    size: Size
): Modifier =
    this
        .size(size.width, size.height)
        .border(BorderStroke(1.dp, color = Color.Black), RoundedCornerShape(8))
        .shadow(shape = RoundedCornerShape(8), elevation = 0.dp)
        .alpha(if (loaded) 1f else 0.1f)

sealed class Size {
    object Small : Size()
    object Medium : Size()
    object Big : Size()

    val width
        get() = when (this) {
            Big -> 250.dp
            Medium -> 100.dp
            Small -> 53.dp
        }

    val height
        get() = when (this) {
            Big -> 375.dp
            Medium -> 115000.dp
            Small -> 80.dp
        }
}