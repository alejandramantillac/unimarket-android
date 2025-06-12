package com.codeoflegends.unimarket.features.cart.data.repositories.impl

import android.content.Context
import android.content.SharedPreferences
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.cart.data.model.Cart
import com.codeoflegends.unimarket.features.cart.data.model.CartItem
import com.codeoflegends.unimarket.features.cart.data.model.CartVariant
import com.codeoflegends.unimarket.features.cart.data.repositories.interfaces.CartRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IUserGetDataRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userGetDataRepository: IUserGetDataRepository,
    private val authRepository: AuthRepository
) : CartRepository {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    private val gson = Gson()
    private val cartFlow = MutableStateFlow(Cart())

    init {
        loadCartFromPrefs()
    }

    private fun loadCartFromPrefs() {
        val cartJson = sharedPreferences.getString(CART_KEY, null)
        if (cartJson != null) {
            val type = object : TypeToken<List<CartItem>>() {}.type
            val items: List<CartItem> = gson.fromJson(cartJson, type)
            cartFlow.value = Cart(items)
        }
    }

    private fun saveCartToPrefs(cart: Cart) {
        val cartJson = gson.toJson(cart.items)
        sharedPreferences.edit().putString(CART_KEY, cartJson).apply()
        cartFlow.value = cart
    }

    override suspend fun getCart(): Cart {
        val currentCart = cartFlow.value
        if (!authRepository.isUserLoggedIn()) {
            return currentCart
        }

        return try {
            val userResult = userGetDataRepository.getUserData()
            val user = userResult.getOrThrow().toDomain()
            currentCart.copy(userCreated = user)
        } catch (e: Exception) {
            throw IllegalStateException("Error al obtener los datos del usuario: ${e.message}")
        }
    }

    override suspend fun addItem(variant: CartVariant, quantity: Int): Result<Unit> = try {
        val currentCart = cartFlow.value
        val existingItem = currentCart.items.find { it.variant.id == variant.id }

        val updatedItems = if (existingItem != null) {
            // Update quantity of existing item
            currentCart.items.map {
                if (it.variant.id == variant.id) {
                    it.copy(quantity = it.quantity + quantity)
                } else {
                    it
                }
            }
        } else {
            // Add new item
            currentCart.items + CartItem(variant = variant, quantity = quantity)
        }

        saveCartToPrefs(Cart(updatedItems))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun removeItem(itemId: UUID): Result<Unit> = try {
        val currentCart = cartFlow.value
        val updatedItems = currentCart.items.filter { it.id != itemId }
        saveCartToPrefs(Cart(updatedItems))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateItemQuantity(itemId: UUID, quantity: Int): Result<Unit> {
        if (quantity <= 0) {
            return removeItem(itemId)
        }

        return try {
            val currentCart = cartFlow.value
            val updatedItems = currentCart.items.map {
                if (it.id == itemId) {
                    it.copy(quantity = quantity)
                } else {
                    it
                }
            }
            saveCartToPrefs(Cart(updatedItems))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearCart(): Result<Unit> = try {
        saveCartToPrefs(Cart())
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun observeCart(): Flow<Cart> = cartFlow.asStateFlow()

    companion object {
        private const val PREFS_NAME = "cart_preferences"
        private const val CART_KEY = "cart_items"
    }
} 