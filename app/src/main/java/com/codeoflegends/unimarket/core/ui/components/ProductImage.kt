package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    if (imageUrl != null && imageUrl.isNotBlank()) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Product image",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Icon(
            imageVector = Icons.Default.Image,
            contentDescription = "No image available",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
} 