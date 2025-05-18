package com.codeoflegends.unimarket.features.product.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.product.data.dto.SimpleProduct
import com.codeoflegends.unimarket.features.product.data.model.Category
import com.codeoflegends.unimarket.features.product.data.model.Entrepreneurship
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification
import com.codeoflegends.unimarket.features.product.data.usecase.CreateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.UpdateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

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
    
    private val _uiState = MutableStateFlow(
        ProductUiState(
            businessOptions = defaultBusinessOptions,
            categoryOptions = defaultCategoryOptions
        )
    )
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<ProductActionState>(ProductActionState.Idle)
    val actionState: StateFlow<ProductActionState> = _actionState.asStateFlow()

    private val _products = MutableStateFlow<List<SimpleProduct>>(emptyList())
    val products: StateFlow<List<SimpleProduct>> = _products.asStateFlow()

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
            _uiState.value = _uiState.value.copy(isEdit = false, product = null)
            return
        }
        val uuid = try { UUID.fromString(productId) } catch (e: Exception) { null }
        if (uuid == null) {
            _actionState.value = ProductActionState.Error("ID de producto inválido")
            return
        }
        _actionState.value = ProductActionState.Loading
        viewModelScope.launch {
            try {
                val product = getProductUseCase(uuid)
                if (product != null) {
                    _uiState.value = _uiState.value.copy(
                        id = product.id,
                        selectedBusiness = product.entrepreneurship,
                        selectedCategory = product.category,
                        name = product.name,
                        description = product.description,
                        price = product.price.toString(),
                        lowStockAlert = product.stockAlert.toString(),
                        published = product.published,
                        isEdit = true,
                        variants = product.variants,
                        specifications = product.specifications,
                        product = product
                    )
                    _actionState.value = ProductActionState.Idle
                } else {
                    _actionState.value = ProductActionState.Error("Producto no encontrado")
                }
            } catch (e: Exception) {
                _actionState.value = ProductActionState.Error("Error al cargar el producto: ${e.message}")
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = index)
    }

    companion object {
        private val defaultBusinessOptions = listOf(
            Entrepreneurship(id = UUID.fromString("00000000-0000-0000-0000-000000000007"), name = "Emarket"),
        )
        
        private val defaultCategoryOptions = listOf(
            Category(name = "Moda", description = "Viste con estilo."),
        )
    }

    private fun validateForm() {
        val state = _uiState.value
        val hasAnyVariantImage = state.variants.any { it.variantImages.isNotEmpty() }
        val isBasicValid = state.name.isNotBlank() &&
                state.description.isNotBlank() &&
                state.selectedBusiness != null &&
                state.selectedCategory != null &&
                (state.price.toDoubleOrNull() ?: 0.0) > 0 &&
                state.lowStockAlert.isNotBlank() &&
                hasAnyVariantImage

        val isVariantsValid = state.variants.isNotEmpty()
        val isSpecificationsValid = state.specifications.isNotEmpty()

        _uiState.value = state.copy(
            isFormValid = isBasicValid && isVariantsValid && isSpecificationsValid
        )
    }

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
        validateForm()
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
        validateForm()
    }

    fun onBusinessSelected(business: String) {
        _uiState.value = _uiState.value.copy(selectedBusiness =
            defaultBusinessOptions.find { it.name == business }
        )
        validateForm()
    }

    fun onCategorySelected(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory =
            defaultCategoryOptions.find { it.name == category }
        )
        validateForm()
    }

    fun onPriceChanged(price: String) {
        _uiState.value = _uiState.value.copy(price = price)
        validateForm()
    }

    fun onLowStockAlertChanged(alert: String) {
        _uiState.value = _uiState.value.copy(lowStockAlert = alert)
        validateForm()
    }

    fun onPublishedChanged(published: Boolean) {
        _uiState.value = _uiState.value.copy(published = published)
        validateForm()
    }

    fun saveProduct() {
        if (!_uiState.value.isFormValid) {
            _actionState.value = ProductActionState.Error("Por favor completa todos los campos requeridos")
            return
        }
        val state = _uiState.value
        val product = Product(
            id = state.id,
            entrepreneurship = state.selectedBusiness!!,
            category = state.selectedCategory!!,
            name = state.name,
            description = state.description,
            price = state.price.toDoubleOrNull() ?: 0.0,
            stockAlert = state.lowStockAlert.toIntOrNull() ?: 0,
            published = state.published,
            variants = state.variants,
            specifications = state.specifications
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

    // --- Variants Logic ---
    fun addVariant(variant: ProductVariant) {
        val updated = _uiState.value.variants + variant
        _uiState.value = _uiState.value.copy(variants = updated)
        validateForm()
    }

    fun updateVariant(updatedVariant: ProductVariant) {
        val updated = _uiState.value.variants.map {
            if (it.id == updatedVariant.id) updatedVariant else it
        }
        _uiState.value = _uiState.value.copy(variants = updated)
        validateForm()
    }

    fun removeVariant(variantId: UUID?) {
        val updated = _uiState.value.variants.filterNot { it.id == variantId }
        _uiState.value = _uiState.value.copy(variants = updated)
        validateForm()
    }

    fun updateVariantImage(variantId: UUID?, images: List<String>) {
        val updated = _uiState.value.variants.map {
            if (it.id == variantId) it.copy(variantImages = images) else it
        }
        _uiState.value = _uiState.value.copy(variants = updated)
    }

    fun updateVariantStock(variantId: UUID?, stock: Int) {
        val updated = _uiState.value.variants.map {
            if (it.id == variantId) it.copy(stock = stock) else it
        }
        _uiState.value = _uiState.value.copy(variants = updated)
    }

    // --- Specifications Logic ---
    fun addSpecification(spec: ProductSpecification) {
        val updated = _uiState.value.specifications + spec
        _uiState.value = _uiState.value.copy(
            specifications = updated,
            hasSpecifications = updated.isNotEmpty()
        )
        validateForm()
    }

    fun updateSpecification(updatedSpec: ProductSpecification) {
        val updated = _uiState.value.specifications.map {
            if (it.id == updatedSpec.id) updatedSpec else it
        }
        _uiState.value = _uiState.value.copy(
            specifications = updated,
            hasSpecifications = updated.isNotEmpty()
        )
        validateForm()
    }

    fun removeSpecification(specId: UUID?) {
        val updated = _uiState.value.specifications.filterNot { it.id == specId }
        _uiState.value = _uiState.value.copy(
            specifications = updated,
            hasSpecifications = updated.isNotEmpty()
        )
        validateForm()
    }
} 