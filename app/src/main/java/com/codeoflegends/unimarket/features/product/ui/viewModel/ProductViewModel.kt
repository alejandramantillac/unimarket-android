package com.codeoflegends.unimarket.features.product.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase
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
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProductUiState()
    )
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<ProductActionState>(ProductActionState.Idle)
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
                _actionState.value =
                    ProductActionState.Error("Error al cargar el producto: ${e.message}")
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
    
    companion object {
        private const val TAG = "ProductViewModel"
    }
} 