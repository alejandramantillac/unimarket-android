package com.codeoflegends.unimarket.features.product.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            formOptions = ProductFormOptions(
                businessOptions = defaultBusinessOptions,
                categoryOptions = defaultCategoryOptions
            ),
            formData = ProductFormData(
                // Set default business in create mode
                selectedBusiness = defaultBusinessOptions.firstOrNull()
            )
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
                _actionState.value =
                    ProductActionState.Error("Error al cargar productos: ${e.message}")
            }
        }
    }

    fun loadProduct(productId: String?) {
        if (productId.isNullOrEmpty()) {
            // Creating a new product
            _uiState.value = _uiState.value.copy(
                uiState = _uiState.value.uiState.copy(isEdit = false),
                selectedProduct = null
            )
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
                    formData = ProductFormData(
                        id = product.id,
                        selectedCategory = product.category,
                        name = product.name,
                        description = product.description,
                        price = product.price.toString(),
                        lowStockAlert = product.stockAlert.toString(),
                        published = product.published,
                        variants = product.variants,
                        specifications = product.specifications,
                        // Don't set selectedBusiness for edit mode
                        selectedBusiness = null
                    ),
                    uiState = _uiState.value.uiState.copy(
                        isEdit = true,
                        hasSpecifications = product.specifications.isNotEmpty()
                    ),
                    selectedProduct = product
                )
                _actionState.value = ProductActionState.Idle
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error loading product: ${e.message}")
                _actionState.value =
                    ProductActionState.Error("Error al cargar el producto: ${e.message}")
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.value = _uiState.value.copy(
            uiState = _uiState.value.uiState.copy(selectedTab = index)
        )
    }

    companion object {
        private val defaultBusinessOptions = listOf(
            Entrepreneurship(
                id = UUID.fromString("00000000-0000-0000-0000-000000000007"),
                name = "Emarket"
            ),
        )

        private val defaultCategoryOptions = listOf(
            Category(name = "Moda", description = "Viste con estilo."),
        )
    }

    private fun validateForm() {
        val formData = _uiState.value.formData
        val isEdit = _uiState.value.uiState.isEdit

        val hasAnyVariantImage = formData.variants.any { it.variantImages.isNotEmpty() }

        // Basic validation that applies to both create and edit modes
        val isBasicValid = formData.name.isNotBlank() &&
                formData.description.isNotBlank() &&
                formData.selectedCategory != null &&
                (formData.price.toDoubleOrNull() ?: 0.0) > 0 &&
                formData.lowStockAlert.isNotBlank() &&
                hasAnyVariantImage

        // Additional validation for create mode: must select a business
        val isBusinessValid = isEdit || formData.selectedBusiness != null

        val isVariantsValid = formData.variants.isNotEmpty()
        val isSpecificationsValid = formData.specifications.isNotEmpty()

        _uiState.value = _uiState.value.copy(
            uiState = _uiState.value.uiState.copy(
                isFormValid = isBasicValid && isBusinessValid && isVariantsValid && isSpecificationsValid
            )
        )
    }

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(name = name)
        )
        validateForm()
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(description = description)
        )
        validateForm()
    }

    fun onCategorySelected(categoryName: String) {
        val selectedCategory =
            _uiState.value.formOptions.categoryOptions.find { it.name == categoryName }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(selectedCategory = selectedCategory)
        )
        validateForm()
    }

    fun onBusinessSelected(businessName: String) {
        val selectedBusiness =
            _uiState.value.formOptions.businessOptions.find { it.name == businessName }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(selectedBusiness = selectedBusiness)
        )
        validateForm()
    }

    fun onPriceChanged(price: String) {
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(price = price)
        )
        validateForm()
    }

    fun onLowStockAlertChanged(alert: String) {
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(lowStockAlert = alert)
        )
        validateForm()
    }

    fun onPublishedChanged(published: Boolean) {
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(published = published)
        )
        validateForm()
    }

    fun saveProduct() {
        if (!_uiState.value.uiState.isFormValid) {
            _actionState.value =
                ProductActionState.Error("Por favor completa todos los campos requeridos")
            return
        }

        val state = _uiState.value
        val formData = state.formData
        val isEdit = state.uiState.isEdit

        // Build the product object for saving
        val product = if (isEdit) {
            // If editing, use the original product's entrepreneurship
            val originalProduct = state.selectedProduct
            if (originalProduct == null) {
                _actionState.value = ProductActionState.Error("Producto original no encontrado")
                return
            }

            Product(
                id = formData.id,
                entrepreneurship = originalProduct.entrepreneurship, // Keep the original entrepreneurship
                category = formData.selectedCategory!!,
                name = formData.name,
                description = formData.description,
                price = formData.price.toDoubleOrNull() ?: 0.0,
                stockAlert = formData.lowStockAlert.toIntOrNull() ?: 0,
                published = formData.published,
                variants = formData.variants,
                specifications = formData.specifications
            )
        } else {
            // If creating, use the selected business
            if (formData.selectedBusiness == null) {
                _actionState.value =
                    ProductActionState.Error("Por favor selecciona un emprendimiento")
                return
            }

            Product(
                id = formData.id,
                entrepreneurship = formData.selectedBusiness,
                category = formData.selectedCategory!!,
                name = formData.name,
                description = formData.description,
                price = formData.price.toDoubleOrNull() ?: 0.0,
                stockAlert = formData.lowStockAlert.toIntOrNull() ?: 0,
                published = formData.published,
                variants = formData.variants,
                specifications = formData.specifications
            )
        }

        viewModelScope.launch {
            try {
                if (isEdit) {
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
        val productId = _uiState.value.formData.id ?: return

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
        // Preserve current options
        val currentOptions = _uiState.value.formOptions

        // Reset the form state but keep options
        _uiState.value = ProductUiState(
            formOptions = currentOptions,
            formData = ProductFormData(
                selectedBusiness = defaultBusinessOptions.firstOrNull()
            )
        )
        _actionState.value = ProductActionState.Success
    }

    // --- Variants Logic ---
    fun addVariant(variant: ProductVariant) {
        val updated = _uiState.value.formData.variants + variant
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(variants = updated)
        )
        validateForm()
    }

    fun updateVariant(updatedVariant: ProductVariant) {
        val updated = _uiState.value.formData.variants.map {
            if (it.id == updatedVariant.id) updatedVariant else it
        }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(variants = updated)
        )
        validateForm()
    }

    fun removeVariant(variantId: UUID?) {
        val updated = _uiState.value.formData.variants.filterNot { it.id == variantId }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(variants = updated)
        )
        validateForm()
    }

    fun updateVariantImage(variantId: UUID?, images: List<String>) {
        val updated = _uiState.value.formData.variants.map {
            if (it.id == variantId) it.copy(variantImages = images) else it
        }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(variants = updated)
        )
    }

    fun updateVariantStock(variantId: UUID?, stock: Int) {
        val updated = _uiState.value.formData.variants.map {
            if (it.id == variantId) it.copy(stock = stock) else it
        }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(variants = updated)
        )
    }

    // --- Specifications Logic ---
    fun addSpecification(spec: ProductSpecification) {
        val updated = _uiState.value.formData.specifications + spec
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(specifications = updated),
            uiState = _uiState.value.uiState.copy(hasSpecifications = true)
        )
        validateForm()
    }

    fun updateSpecification(updatedSpec: ProductSpecification) {
        val updated = _uiState.value.formData.specifications.map {
            if (it.id == updatedSpec.id) updatedSpec else it
        }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(specifications = updated),
            uiState = _uiState.value.uiState.copy(hasSpecifications = updated.isNotEmpty())
        )
        validateForm()
    }

    fun removeSpecification(specId: UUID?) {
        val updated = _uiState.value.formData.specifications.filterNot { it.id == specId }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(specifications = updated),
            uiState = _uiState.value.uiState.copy(hasSpecifications = updated.isNotEmpty())
        )
        validateForm()
    }
} 