package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingStars(
    rating: Float,
    showRating: Boolean = true,
    onRatingChanged: ((Float) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(5) { index ->
            val isEditable = onRatingChanged != null
            val starModifier = if (isEditable) {
                Modifier.clickable {
                    onRatingChanged?.invoke(index + 1f)
                }
            } else {
                Modifier
            }

            Icon(
                imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Estrella ${index + 1}",
                tint = if (index < rating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                modifier = starModifier
            )
        }

        if (showRating) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = String.format("%.1f", rating),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
} 