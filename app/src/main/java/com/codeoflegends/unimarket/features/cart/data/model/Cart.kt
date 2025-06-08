package com.codeoflegends.unimarket.features.cart.data.model

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.features.product.data.model.VariantImage
import java.util.UUID

data class Cart(
    val items: List<CartItem> = emptyList(),
    val userCreated: User? = null,
) {
    val totalItems: Int
        get() = items.sumOf { it.quantity }

    val subtotal: Double
        get() = items.sumOf { it.variant.price * it.quantity }
}

data class CartItem(
    val id: UUID = UUID.randomUUID(),
    val variant: CartVariant,
    val quantity: Int
)

data class CartVariant(
    val id: UUID,
    val name: String,
    val price: Double,
    val imageUrl: String?,
    val productId: UUID,
    val productName: String,
    val stock: Int,
    val variantImages: List<VariantImage> = emptyList()
) 