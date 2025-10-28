package com.codeoflegends.unimarket.features.cart.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.state.MessageManager
import com.codeoflegends.unimarket.features.cart.data.model.Cart
import com.codeoflegends.unimarket.features.cart.data.usecase.*
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class CartUiState(
    val cart: Cart = Cart(),
    val isLoading: Boolean = false,
    val lastCreatedOrder: Order? = null
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val createOrderFromCartUseCase: CreateOrderFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val cart = getCartUseCase()
                _uiState.update { it.copy(cart = cart) }
            } catch (e: Exception) {
                MessageManager.showError("Error al cargar el carrito: ${e.message ?: "Error desconocido"}")
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun addToCart(product: Product, variant: ProductVariant, quantity: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            addToCartUseCase(product, variant, quantity)
                .onSuccess { loadCart() }
                .onFailure { e -> 
                    MessageManager.showError("Error al agregar al carrito: ${e.message ?: "Error desconocido"}")
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun removeFromCart(itemId: UUID) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            removeFromCartUseCase(itemId)
                .onSuccess { loadCart() }
                .onFailure { e ->
                    MessageManager.showError("Error al eliminar del carrito: ${e.message ?: "Error desconocido"}")
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun updateItemQuantity(itemId: UUID, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(itemId)
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            updateCartItemQuantityUseCase(itemId, quantity)
                .onSuccess { loadCart() }
                .onFailure { e ->
                    MessageManager.showError("Error al actualizar cantidad: ${e.message ?: "Error desconocido"}")
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun createOrder() {
        viewModelScope.launch {
            try {
                Log.d("TEST_LOG", "Starting order creation in CartViewModel")
                _uiState.update { it.copy(isLoading = true) }
                val cart = getCartUseCase() // Obtener el carrito más reciente con el usuario
                Log.d("TEST_LOG", "Cart obtained: ${cart.items.size} items, user: ${cart.userCreated?.id}")
                createOrderFromCartUseCase(cart)
                    .onSuccess { order ->
                        Log.d("TEST_LOG", "Order created successfully with ID: ${order.id}")
                        _uiState.update { it.copy(lastCreatedOrder = order) }
                        MessageManager.showSuccess("Orden creada exitosamente")
                        // Clear cart after successful order creation
                        clearCartUseCase()
                            .onSuccess {
                                Log.d("TEST_LOG", "Cart cleared successfully")
                                loadCart() // Reload cart to update UI
                                // Don't reset lastCreatedOrder immediately - let the UI handle navigation first
                            }
                            .onFailure { e ->
                                Log.e("TEST_LOG", "Error clearing cart: ${e.message}", e)
                                // Still reload cart even if clearing failed
                                loadCart()
                                // Don't reset lastCreatedOrder immediately - let the UI handle navigation first
                            }
                    }
                    .onFailure { e ->
                        Log.e("TEST_LOG", "Error creating order: ${e.message}", e)
                        MessageManager.showError("Error al crear la orden: ${e.message ?: "Error desconocido"}")
                    }
            } catch (e: Exception) {
                Log.e("TEST_LOG", "Exception in createOrder: ${e.message}", e)
                MessageManager.showError("Error al crear la orden: ${e.message ?: "Error desconocido"}")
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun resetLastCreatedOrder() {
        _uiState.update { it.copy(lastCreatedOrder = null) }
    }
}