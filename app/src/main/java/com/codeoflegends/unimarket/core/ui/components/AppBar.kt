package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun AppBar(
    barOptions: AppBarOptions = AppBarOptions(),
) {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color.Black.copy(alpha = 0.5f),
            Color.Black.copy(alpha = 0.3f),
            Color.Transparent
        ),
        startY = 0f,
        endY = 56f * 3
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(gradient)
            .zIndex(2f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.width(56.dp)) {
                if (barOptions.showBackButton) {
                    IconButton(onClick = barOptions.onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White,
                        )
                    }
                }
            }

            Text(
                text = barOptions.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Box(modifier = Modifier.widthIn(min = 56.dp)) {
                barOptions.barOptions()
            }
        }
    }
}

data class AppBarOptions(
    val show: Boolean = false,
    val title: String = "",
    val showBackButton: Boolean = false,
    val onBackClick: () -> Unit = {},
    val barOptions: @Composable () -> Unit = {}
)