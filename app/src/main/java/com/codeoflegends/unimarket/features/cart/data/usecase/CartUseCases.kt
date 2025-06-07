package com.codeoflegends.unimarket.features.cart.data.usecase

import com.codeoflegends.unimarket.features.cart.data.model.Cart
import com.codeoflegends.unimarket.features.cart.data.model.CartVariant
import com.codeoflegends.unimarket.features.cart.data.repositories.interfaces.CartRepository
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import java.util.UUID
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(): Cart = repository.getCart()
}

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(product: Product, variant: ProductVariant, quantity: Int): Result<Unit> {
        val cartVariant = CartVariant(
            id = variant.id!!,
            name = variant.name,
            price = product.price,
            imageUrl = variant.variantImages.firstOrNull()?.imageUrl,
            productId = product.id!!,
            productName = product.name
        )
        return repository.addItem(cartVariant, quantity)
    }
}

class RemoveFromCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(itemId: UUID): Result<Unit> = repository.removeItem(itemId)
}

class UpdateCartItemQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(itemId: UUID, quantity: Int): Result<Unit> = 
        repository.updateItemQuantity(itemId, quantity)
}

class ClearCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.clearCart()
}

class ObserveCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke() = repository.observeCart()
} 