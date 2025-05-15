package com.codeoflegends.unimarket.features.product.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.usecase.CreateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.UpdateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProductActionState {
    object Idle : ProductActionState()
    object Success : ProductActionState()
    data class Error(val message: String) : ProductActionState()
    object Loading : ProductActionState()
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {
    
    // Lista predefinida de opciones para asegurar que siempre haya datos
    //TODO: Cambiar por un repositorio real que traiga los datos de la API
    private val defaultBusinessOptions = listOf(
        "Tienda de Ropa", 
        "Electrónica XYZ", 
        "Supermercado ABC", 
        "Librería Online"
    )
    
    private val defaultCategoryOptions = listOf(
        "Electrónica", 
        "Ropa", 
        "Alimentos", 
        "Libros", 
        "Deportes", 
        "Hogar"
    )
    
    private val _uiState = MutableStateFlow(
        ProductUiState(
            businessOptions = defaultBusinessOptions,
            categoryOptions = defaultCategoryOptions
        )
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

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                val productList = getAllProductsUseCase()
                _products.value = productList
            } catch (e: Exception) {
                _actionState.value = ProductActionState.Error("Error al cargar productos: ${e.message}")
            }
        }
    }

    fun loadProduct(productId: String?) {
        if (productId.isNullOrEmpty()) {
            // Si no hay ID, estamos en modo creación
            _uiState.value = _uiState.value.copy(isEdit = false)
            return
        }
        
        _actionState.value = ProductActionState.Loading
        
        viewModelScope.launch {
            try {
                val product = getProductUseCase(productId)
                
                val currentBusinessOptions = _uiState.value.businessOptions
                val currentCategoryOptions = _uiState.value.categoryOptions
                
                _uiState.value = _uiState.value.copy(
                    id = product.id,
                    selectedBusiness = product.business,
                    selectedCategory = product.category,
                    name = product.name,
                    description = product.description,
                    price = product.price.toString(),
                    lowStockAlert = product.lowStockAlert.toString(),
                    published = product.published,
                    isEdit = true,
                    // Preservar las opciones
                    businessOptions = currentBusinessOptions,
                    categoryOptions = currentCategoryOptions
                )
                _actionState.value = ProductActionState.Idle
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

    fun onPriceChanged(price: String) {
        _uiState.value = _uiState.value.copy(price = price)
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
            price = state.price.toDoubleOrNull() ?: 0.0,
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
        val productId = _uiState.value.id ?: return
        
        _actionState.value = ProductActionState.Loading
        
        viewModelScope.launch {
            try {
                deleteProductUseCase(productId)
                _actionState.value = ProductActionState.Success
                // Después de eliminar, debería navegar hacia atrás
            } catch (e: Exception) {
                _actionState.value = ProductActionState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun cancelOperation() {
        // Preservar opciones actuales
        val currentBusinessOptions = _uiState.value.businessOptions
        val currentCategoryOptions = _uiState.value.categoryOptions
        
        // Reiniciar el estado del formulario
        _uiState.value = ProductUiState(
            businessOptions = currentBusinessOptions,
            categoryOptions = currentCategoryOptions
        )
        _actionState.value = ProductActionState.Success
    }
} 