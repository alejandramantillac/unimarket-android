package com.codeoflegends.unimarket.features.cart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.cart.data.model.Cart
import com.codeoflegends.unimarket.features.cart.data.usecase.*
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val observeCartUseCase: ObserveCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeCartUseCase().collect { cart ->
                _uiState.value = _uiState.value.copy(
                    cart = cart,
                    isLoading = false
                )
            }
        }
    }

    fun addToCart(product: Product, variant: ProductVariant, quantity: Int = 1) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            addToCartUseCase(product, variant, quantity)
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message ?: "Error al agregar al carrito",
                        isLoading = false
                    )
                }
        }
    }

    fun removeFromCart(itemId: UUID) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            removeFromCartUseCase(itemId)
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message ?: "Error al remover del carrito",
                        isLoading = false
                    )
                }
        }
    }

    fun updateItemQuantity(itemId: UUID, quantity: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            updateCartItemQuantityUseCase(itemId, quantity)
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message ?: "Error al actualizar cantidad",
                        isLoading = false
                    )
                }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            clearCartUseCase()
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message ?: "Error al limpiar el carrito",
                        isLoading = false
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class CartUiState(
    val cart: Cart = Cart(),
    val isLoading: Boolean = false,
    val error: String? = null
) 