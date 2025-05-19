package com.codeoflegends.unimarket.features.entrepreneurship.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun RatingBar(rating: Float) {
    Row {
        repeat(5) { index ->
            val icon = if (index < rating.toInt()) Icons.Default.Star else Icons.Default.StarBorder
            Icon(icon, contentDescription = null, tint = Color(0xFFFFC107)) // Amarillo estrella
        }
    }
}
