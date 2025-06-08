package com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.ProductHeader
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.Review

@Composable
fun ProductBuyerDetailPage(product: Product, reviews: List<Review>) {
    ProductHeader(
        product = product,
        variantImages = product.variants.flatMap { it.variantImages },
        reviewCount = reviews.size,
        averageRating = if (reviews.isNotEmpty()) reviews.map { it.rating }.average().toFloat() else 0f
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Vendedor:", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(product.entrepreneurship.name, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text("Descripción", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(6.dp))
        Text(product.description, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))
        Text("Especificaciones", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(6.dp))
        if (product.specifications.isEmpty()) {
            Text("No hay especificaciones", style = MaterialTheme.typography.bodyMedium)
        } else {
            product.specifications.forEach { spec ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(spec.key, style = MaterialTheme.typography.bodyMedium)
                    Text(spec.value, style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
} 