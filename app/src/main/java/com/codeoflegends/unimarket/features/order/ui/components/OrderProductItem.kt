package com.codeoflegends.unimarket.features.order.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.ProductImage
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant

@Composable
fun OrderProductItem(
    product: ProductVariant,
    quantity: Int,
    totalPrice: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del producto
        ProductImage(
            imageUrl = product.variantImages.firstOrNull(),
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp)
        )
        // Nombre del producto
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$quantity unidades",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        // Precio total
        Text(
            text = "$$totalPrice",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}