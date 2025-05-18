package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.VariantImage

@Composable
fun ProductHeader(
    product: Product?,
    variantImages: List<VariantImage> = emptyList(),
    reviewCount: Int = 0,
    modifier: Modifier = Modifier
) {
    if (product == null) return
    val images = remember(product, variantImages) {
        val main = product.variants.flatMap { it.variantImages }.firstOrNull()?.let { listOf(it) } ?: emptyList()
        (main + variantImages).distinct().filter { it.imageUrl.isNotEmpty() }
    }
    var selectedImage by remember { mutableStateOf(images.firstOrNull()) }
    val rating = product.reviews.takeIf { it.isNotEmpty() }?.map { it.rating }?.average()?.toFloat() ?: 0f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Imagen principal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            ProductImage(
                imageUrl = selectedImage!!.imageUrl,
                modifier = Modifier.fillMaxSize()
            )
        }
        // Miniaturas
        if (images.size > 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                images.forEach { img ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (img == selectedImage) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                            .clickable { selectedImage = img },
                        contentAlignment = Alignment.Center
                    ) {
                        ProductImage(
                            imageUrl = img.imageUrl,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        // Nombre
        Text(
            text = product.name,
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
            modifier = Modifier.padding(bottom = 1.dp)
        )
        // Rating y reseñas
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingStars(
                rating = rating,
                showRating = false,
                modifier = Modifier.height(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = String.format("%.1f", rating) + " (${reviewCount} reseñas)",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        // Precio
        Text(
            text = "$ ${product.price}",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.5.sp
            ),
            modifier = Modifier.padding(top = 1.dp)
        )
    }
} 