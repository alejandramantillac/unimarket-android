package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StockStatus(
    stock: Int,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when {
        stock <= 0 -> Color(0xFFFFE5E5) to Color(0xFFD32F2F) // Rojo suave
        stock < 5 -> Color(0xFFFFF3E0) to Color(0xFFF57C00) // Naranja suave
        else -> Color(0xFFE8F5E9) to Color(0xFF2E7D32) // Verde suave
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = when {
                stock <= 0 -> "Sin stock"
                stock < 5 -> "Últimas unidades"
                else -> "En stock"
            },
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
} 