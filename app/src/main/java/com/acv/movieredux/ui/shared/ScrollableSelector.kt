package com.acv.movieredux.ui.shared

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.acv.movieredux.ui.theme.SteamedGold

@Composable
fun ScrollableSelector(
    items: List<String>,
) {
    var selection: Int by remember { mutableStateOf(0) }

    Row(Modifier.horizontalScroll(rememberScrollState())) {
        items.forEachIndexed { index, item ->
            text(
                index = index,
                item = item,
                isSelected = index == selection,
                onSelected = { selection = it }
            )
        }
    }
}

@Composable
private fun text(
    index: Int,
    item: String,
    isSelected: Boolean,
    onSelected: (Int) -> Unit
) {
    if (isSelected) {
        Text(
            modifier = Modifier
                .background(Color.White)
                .padding(4.dp)
                .background(SteamedGold, RoundedCornerShape(8.dp))
                .border(BorderStroke(0.dp, Color.White), RoundedCornerShape(8.dp))
                .clickable { onSelected(index) },
            text = item,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1
        )
    } else {
        Text(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .clickable { onSelected(index) },
            text = item,
            style = MaterialTheme.typography.body1
        )
    }
}