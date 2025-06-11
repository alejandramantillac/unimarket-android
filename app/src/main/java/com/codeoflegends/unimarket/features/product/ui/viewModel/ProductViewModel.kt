package com.codeoflegends.unimarket.features.product.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.RateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductReviewsUseCase
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductActionState
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel for product listing and viewing functionality
 */
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val rateProductUseCase: RateProductUseCase,
    private val getProductReviewsUseCase: GetProductReviewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProductUiState()
    )
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<ProductActionState>(ProductActionState.Loading)
    val actionState: StateFlow<ProductActionState> = _actionState.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        loadProducts()
    }

    /**
     * Loads all products for listing
     */
    fun loadProducts() {
        viewModelScope.launch {
            try {
                _actionState.value = ProductActionState.Loading
                val productList = getAllProductsUseCase()
                _products.value = productList
                _actionState.value = ProductActionState.Idle
            } catch (e: Exception) {
                _actionState.value =
                    ProductActionState.Error("Error al cargar productos: ${e.message}")
            }
        }
    }

    /**
     * Loads a specific product for viewing
     */
    fun loadProduct(productId: String?) {
        if (productId.isNullOrEmpty()) {
            _actionState.value = ProductActionState.Error("ID de producto inválido")
            return
        }

        val uuid = try {
            UUID.fromString(productId)
        } catch (e: Exception) {
            null
        }
        
        if (uuid == null) {
            _actionState.value = ProductActionState.Error("ID de producto inválido")
            return
        }

        _actionState.value = ProductActionState.Loading

        viewModelScope.launch {
            try {
                val product = getProductUseCase(uuid)
                _uiState.value = _uiState.value.copy(
                    selectedProduct = product
                )
                _actionState.value = ProductActionState.Idle
            } catch (e: Exception) {
                Log.e(TAG, "Error loading product: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    selectedProduct = null
                )
                _actionState.value =
                    ProductActionState.Error("No se encontró el producto")
            }
        }
    }

    /**
     * Handles tab selection in detailed product view
     */
    fun onTabSelected(index: Int) {
        _uiState.value = _uiState.value.copy(
            uiState = _uiState.value.uiState.copy(selectedTab = index)
        )
    }

    /**
     * Deletes the currently selected product
     */
    fun deleteProduct(productId: UUID) {
        _actionState.value = ProductActionState.Loading

        viewModelScope.launch {
            try {
                deleteProductUseCase(productId)
                loadProducts() // Refresh the list
                _actionState.value = ProductActionState.Success
            } catch (e: Exception) {
                _actionState.value = ProductActionState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun showRatingModal() {
        Log.d(TAG, "Showing rating modal")
        _uiState.value = _uiState.value.copy(showRatingModal = true)
    }

    fun hideRatingModal() {
        Log.d(TAG, "Hiding rating modal")
        _uiState.value = _uiState.value.copy(showRatingModal = false)
    }

    fun rateProduct(rating: Float, comment: String) {
        Log.d(TAG, "Rating product with rating: $rating and comment: $comment")
        viewModelScope.launch {
            try {
                _actionState.value = ProductActionState.Loading
                val productId = _uiState.value.selectedProduct?.id
                    ?: throw Exception("No hay producto seleccionado")
                rateProductUseCase(productId, rating.toInt(), comment)
                _actionState.value = ProductActionState.Success
                hideRatingModal()
                loadProductReviews(productId, 1, 10)
            } catch (e: Exception) {
                Log.e(TAG, "Error rating product: ${e.message}", e)
                _actionState.value = ProductActionState.Error("Error al calificar el producto: ${e.message}")
            }
        }
    }

    fun loadProductReviews(productId: UUID, page: Int, limit: Int) {
        Log.d("ProductViewModel", "Loading reviews for product: $productId")
        viewModelScope.launch {
            try {
                val reviews = getProductReviewsUseCase(productId, page, limit)
                Log.d("ProductViewModel", "Received reviews: $reviews")
                _uiState.value = _uiState.value.copy(
                    reviews = reviews
                )
                Log.d("ProductViewModel", "Updated state with reviews: ${_uiState.value.reviews}")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error loading product reviews: ${e.message}", e)
            }
        }
    }
    
    companion object {
        private const val TAG = "ProductViewModel"
    }
} 