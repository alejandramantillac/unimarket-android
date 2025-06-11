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
import com.codeoflegends.unimarket.features.order.data.model.OrderDetail
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import java.text.NumberFormat
import java.util.Locale
import kotlin.times

@Composable
fun OrderProductItem(
    product: OrderDetail,
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
            imageUrl = product.productVariant.variantImages.firstOrNull()?.imageUrl ?: "",
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp)
        )
        // Nombre del producto
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.productVariant.name,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = product.amount.toString() + " unidades",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        // Precio total
        val formattedPrice = NumberFormat.getNumberInstance(Locale("es", "CO")).format(product.unitPrice * product.amount)

        Text(
            text = "$$formattedPrice",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}