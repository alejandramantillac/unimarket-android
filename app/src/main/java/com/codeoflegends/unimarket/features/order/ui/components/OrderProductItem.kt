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
import com.codeoflegends.unimarket.features.order.data.model.OrderProduct

@Composable
fun OrderProductItem(
    product: OrderProduct,
    modifier: Modifier = Modifier
) {
    val selectedVariant = product.product.variants.firstOrNull() // Selecciona la primera variante como ejemplo

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen de la variante o producto
        ProductImage(
            imageUrl = selectedVariant?.variantImages?.firstOrNull(), // Usa la primera imagen de la variante
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp)
        )
        // Nombre de la variante o producto
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = selectedVariant?.name ?: product.product.name, // Usa el nombre de la variante si está disponible
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${product.quantity} unidades",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        // Precio total
        Text(
            text = "$${product.totalPrice}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}