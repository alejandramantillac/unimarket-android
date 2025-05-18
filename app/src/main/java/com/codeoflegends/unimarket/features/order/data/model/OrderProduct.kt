package com.codeoflegends.unimarket.features.order.data.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codeoflegends.unimarket.core.ui.components.ProductImage
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant

data class OrderProduct(
    val product: Product,
    val quantity: Int,
    val selectedVariant: ProductVariant? = null // Variante seleccionada
){
    val totalPrice: Double
        get() = product.price * quantity

    @Composable
    fun ProductImageComposable(modifier: Modifier = Modifier) {
        val imageUrl = selectedVariant?.variantImages?.firstOrNull() ?: product.variants.firstOrNull()?.variantImages?.firstOrNull()
        ProductImage(imageUrl = imageUrl, modifier = modifier)
    }
}