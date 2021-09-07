package com.acv.movieredux.ui.shared

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acv.movieredux.ui.theme.Orange

@Composable
fun PopularityBadge(
    score: Int
) {
    Box(modifier = Modifier.size(40.dp)) {
        Circle(
            modifier = Modifier
                .size(40.dp)
//                .foregroundColor(. clear)
//                .overlay(overlay)
        )

        Text(
            modifier = Modifier.background(MaterialTheme.colors.primary),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            text = score.toString()
        )
//            .font(Font.system(size: 10))
    }
}

@Composable
private fun Overlay(
    score: Int
) {
    var isDisplayed by remember {
        mutableStateOf(false)
    }

    val scoreColor: Color =
        when {
            score < 40 -> Red
            score < 60 -> Orange
            score < 75 -> Yellow
            else -> Green
        }

    Box(
        Modifier
            .rotate(-90f)
            .onGloballyPositioned { isDisplayed = true }
    ) {
        Circle(Modifier.border(border = BorderStroke(2.dp, MaterialTheme.colors.secondary)))
        Circle(
            Modifier
                .border(border = BorderStroke(2.dp, scoreColor))
                .shadow(elevation = 0.dp, RoundedCornerShape(4.dp))
                .animateContentSize(spring(stiffness = 60f, dampingRatio = 10f))
        )
    }
}

@Composable
private fun Circle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(64.dp)
            .background(Color.Gray, CircleShape)
    )
}