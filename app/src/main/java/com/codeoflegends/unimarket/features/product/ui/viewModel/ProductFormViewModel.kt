package com.codeoflegends.unimarket.features.product.ui.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.usecase.UploadImageUseCase
import com.codeoflegends.unimarket.features.product.data.model.ProductCategory
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.product.data.model.NewProduct
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.model.VariantImage
import com.codeoflegends.unimarket.features.product.data.usecase.CreateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductCategoriesUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.UpdateProductUseCase
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductActionState
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductFormData
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductFormOptions
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductUiState
import com.codeoflegends.unimarket.features.product.ui.viewModel.validation.ProductValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel for product creation and editing functionality
 */
@HiltViewModel
class ProductFormViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getAllProductCategoriesUseCase: GetAllProductCategoriesUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProductUiState(
            formOptions = ProductFormOptions(
                businessOptions = emptyList(),
                categoryOptions = defaultCategoryOptions
            )
        )
    )
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<ProductActionState>(ProductActionState.Idle)
    val actionState: StateFlow<ProductActionState> = _actionState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        loadProductCategories()
    }

    private lateinit var entrepreneurshipId: UUID

    fun loadEntrepreneurshipId(entrepreneurshipId: String?) {
        if (!entrepreneurshipId.isNullOrEmpty()) {
            this.entrepreneurshipId = UUID.fromString(entrepreneurshipId)
        }
    }

    /**
     * Loads all product categories for the form
     */
    private fun loadProductCategories() {
        viewModelScope.launch {
            try {
                val categories = getAllProductCategoriesUseCase()
                _uiState.value = _uiState.value.copy(
                    formOptions = _uiState.value.formOptions.copy(
                        categoryOptions = categories
                    )
                )
                _actionState.value = ProductActionState.Idle
            } catch (e: Exception) {
                Log.e(TAG, "Error loading product categories: ${e.message}")
                _actionState.value =
                    ProductActionState.Error("Error al cargar categorías de productos: ${e.message}")
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
                        // selectedBusiness = null
                    ),
                    uiState = _uiState.value.uiState.copy(
                        isEdit = true,
                        hasSpecifications = product.specifications.isNotEmpty()
                    ),
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

    // Tab navigation
    fun onTabSelected(index: Int) {
        _uiState.value = _uiState.value.copy(
            uiState = _uiState.value.uiState.copy(selectedTab = index)
        )
    }

    /**
     * Validates the form and updates the UI state
     */
    private fun validateForm() {
        val formData = _uiState.value.formData
        val isEdit = _uiState.value.uiState.isEdit
        
        val isValid = ProductValidation.validateForm(formData, isEdit)
        
        _uiState.value = _uiState.value.copy(
            uiState = _uiState.value.uiState.copy(isFormValid = isValid)
        )
    }

    // Basic field updates
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
        
        _actionState.value = ProductActionState.Loading

        viewModelScope.launch {
            try {
                if (isEdit) {
                    // For edit mode: upload images with product ID, then update
                    val productId = formData.id ?: throw Exception("Product ID is required for editing")
                    val updatedVariants = uploadVariantImages(formData.variants, productId)
                    val updatedFormData = formData.copy(variants = updatedVariants)
                    val product = buildProductFromForm(updatedFormData, isEdit)
                    if (product == null) {
                        return@launch
                    }
                    updateProductUseCase(product)
                } else {
                    // For create mode: generate ID, upload images first, then create product with URLs
                    val productId = UUID.randomUUID()
                    
                    // Upload images using the product ID as filename
                    val updatedVariants = uploadVariantImages(formData.variants, productId)
                    
                    // Create form data with the product ID and uploaded image URLs
                    val formDataWithId = formData.copy(
                        id = productId,
                        variants = updatedVariants
                    )
                    
                    // Build the product object with generated ID and image URLs
                    val product = buildProductFromForm(formDataWithId, isEdit)
                    if (product == null) {
                        return@launch
                    }
                    
                    // Create the product with all data including image URLs
                    createProductUseCase(product)
                }
                _actionState.value = ProductActionState.Success
            } catch (e: Exception) {
                Log.e(TAG, "Error saving product: ${e.message}")
                _actionState.value = ProductActionState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    
    /**
     * Uploads all images from variants that have local URIs and returns updated variants
     * with server URLs
     * @param variants List of variants to process
     * @param productId The product ID to use as the filename for uploaded images
     */
    private suspend fun uploadVariantImages(variants: List<ProductVariant>, productId: UUID): List<ProductVariant> {
        var imageCounter = 0
        
        return variants.mapIndexed { variantIndex, variant ->
            // Upload each image in the variant
            val uploadedImages = variant.variantImages.mapIndexed { imageIndex, variantImage ->
                // Check if this is a local URI (starts with content:// or file://)
                val imageUrl = variantImage.imageUrl
                if (imageUrl.startsWith("content://") || imageUrl.startsWith("file://")) {
                    try {
                        val uri = Uri.parse(imageUrl)
                        // Use productId as the filename base
                        val fileName = "${productId}_${imageCounter}"
                        imageCounter++
                        val serverUrl = uploadImageUseCase(uri, fileName)
                        variantImage.copy(imageUrl = serverUrl)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error uploading image: ${e.message}")
                        throw Exception("Error al subir imagen: ${e.message}")
                    }
                } else {
                    // Already a server URL, keep it
                    variantImage
                }
            }
            
            variant.copy(variantImages = uploadedImages)
        }
    }
    
    /**
     * Builds a Product entity from form data
     */
    private fun buildProductFromForm(formData: ProductFormData, isEdit: Boolean): NewProduct? {
        return if (isEdit) {
            // If editing, use the original product's entrepreneurship
            val originalProduct = _uiState.value.selectedProduct
            if (originalProduct == null) {
                _actionState.value = ProductActionState.Error("Producto original no encontrado")
                return null
            }

            NewProduct(
                id = formData.id,
                entrepreneurship = originalProduct.entrepreneurship.id!!, // Keep original entrepreneurship
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
            NewProduct(
                id = formData.id,
                entrepreneurship = entrepreneurshipId,
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
    }

    fun deleteProduct() {
        val productId = _uiState.value.formData.id ?: return

        _actionState.value = ProductActionState.Loading

        viewModelScope.launch {
            try {
                deleteProductUseCase(productId)
                _actionState.value = ProductActionState.Success
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

    fun updateVariantImage(variantId: UUID?, images: List<VariantImage>) {
        val updated = _uiState.value.formData.variants.map {
            if (it.id == variantId) it.copy(variantImages = images) else it
        }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(variants = updated)
        )
        validateForm()
    }

    fun updateVariantStock(variantId: UUID?, stock: Int) {
        val updated = _uiState.value.formData.variants.map {
            if (it.id == variantId) it.copy(stock = stock) else it
        }
        _uiState.value = _uiState.value.copy(
            formData = _uiState.value.formData.copy(variants = updated)
        )
        validateForm()
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
    
    companion object {
        private const val TAG = "ProductFormViewModel"
        
        private val defaultBusinessOptions = listOf(
            Entrepreneurship(
                id = UUID.fromString("00000000-0000-0000-0000-000000000007"),
                name = "Emarket",
                slogan = "",
                description = "",
                creationDate = LocalDateTime.now(),
                customization = EntrepreneurshipCustomization(
                    profileImg = "",
                    bannerImg = "",
                    color1 = "",
                    color2 = ""
                ),
                email = "",
                phone = "",
                category = 0
            )
        )
        
        private val defaultCategoryOptions = listOf(
            ProductCategory(name = "Moda", description = "Viste con estilo.")
        )
    }
} 