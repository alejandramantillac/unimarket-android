package com.codeoflegends.unimarket.features.cart.data.repositories.interfaces

import com.codeoflegends.unimarket.features.cart.data.model.Cart
import com.codeoflegends.unimarket.features.cart.data.model.CartItem
import com.codeoflegends.unimarket.features.cart.data.model.CartVariant
import java.util.UUID
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCart(): Cart
    suspend fun addItem(variant: CartVariant, quantity: Int): Result<Unit>
    suspend fun removeItem(itemId: UUID): Result<Unit>
    suspend fun updateItemQuantity(itemId: UUID, quantity: Int): Result<Unit>
    suspend fun clearCart(): Result<Unit>
    fun observeCart(): Flow<Cart>
} 