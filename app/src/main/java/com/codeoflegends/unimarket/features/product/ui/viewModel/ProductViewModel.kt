package com.codeoflegends.unimarket.features.product.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.codeoflegends.unimarket.features.product.model.Product
import com.codeoflegends.unimarket.features.product.data.usecase.CreateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.UpdateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import com.codeoflegends.unimarket.features.product.viewmodel.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProductActionState {
    object Idle : ProductActionState()
    object Success : ProductActionState()
    data class Error(val message: String) : ProductActionState()
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<ProductActionState>(ProductActionState.Idle)
    val actionState: StateFlow<ProductActionState> = _actionState.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            try {
                val product = getProductUseCase(productId)
                _uiState.value = _uiState.value.copy(
                    id = product.id,
                    selectedBusiness = product.business,
                    selectedCategory = product.category,
                    name = product.name,
                    description = product.description,
                    sku = product.sku,
                    price = product.price.toString(),
                    quantity = product.quantity.toString(),
                    lowStockAlert = product.lowStockAlert.toString(),
                    published = product.published,
                    isEdit = true
                )
            } catch (e: Exception) {
                _actionState.value =
                    ProductActionState.Error("Error al cargar el producto: ${e.message}")
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = index)
    }

    fun onBusinessSelected(business: String) {
        _uiState.value = _uiState.value.copy(selectedBusiness = business)
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onSkuChanged(sku: String) {
        _uiState.value = _uiState.value.copy(sku = sku)
    }

    fun onPriceChanged(price: String) {
        _uiState.value = _uiState.value.copy(price = price)
    }

    fun onQuantityChanged(quantity: String) {
        _uiState.value = _uiState.value.copy(quantity = quantity)
    }

    fun onLowStockAlertChanged(alert: String) {
        _uiState.value = _uiState.value.copy(lowStockAlert = alert)
    }

    fun onPublishedChanged(published: Boolean) {
        _uiState.value = _uiState.value.copy(published = published)
    }

    fun saveProduct() {
        val state = _uiState.value
        val product = Product(
            id = state.id,
            business = state.selectedBusiness ?: "",
            category = state.selectedCategory ?: "",
            name = state.name,
            description = state.description,
            sku = state.sku,
            price = state.price.toDoubleOrNull() ?: 0.0,
            quantity = state.quantity.toIntOrNull() ?: 0,
            lowStockAlert = state.lowStockAlert.toIntOrNull() ?: 0,
            published = state.published
        )
        viewModelScope.launch {
            try {
                if (state.isEdit) {
                    updateProductUseCase(product)
                } else {
                    createProductUseCase(product)
                }
                _actionState.value = ProductActionState.Success
            } catch (e: Exception) {
                _actionState.value = ProductActionState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun deleteProduct() {
        val id = _uiState.value.id
        if (id.isNullOrBlank()) {
            _actionState.value = ProductActionState.Error("ID no válido")
            return
        }
        viewModelScope.launch {
            try {
                deleteProductUseCase(id)
                _actionState.value = ProductActionState.Success
            } catch (e: Exception) {
                _actionState.value = ProductActionState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun cancelOperation() {
        // Aquí podrías implementar la lógica para volver atrás
        // Por ejemplo, usando un evento de navegación
        _actionState.value = ProductActionState.Success
    }
} 