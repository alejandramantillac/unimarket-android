package com.codeoflegends.unimarket.features.product.ui.viewModel.state

import com.codeoflegends.unimarket.features.product.data.model.ProductCategory
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.Review
import java.util.UUID
import java.time.LocalDateTime

/**
 * ProductFormData: Contains all editable fields for a product
 * These are specifically the values that the user can input in the form
 */
data class ProductFormData(
    // Basic product info
    val id: UUID? = null,
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val lowStockAlert: String = "",
    val published: Boolean = false,
    
    // References to business objects
    val selectedCategory: ProductCategory? = null,
    val entrepreneurshipId: UUID? = null, // Used only in create mode
    
    // Collections that define the product
    val variants: List<ProductVariant> = emptyList(),
    val specifications: List<ProductSpecification> = emptyList(),
)

/**
 * Options available to the user from business data
 */
data class ProductFormOptions(
    val businessOptions: List<Entrepreneurship> = emptyList(),
    val categoryOptions: List<ProductCategory> = emptyList(),
)

/**
 * UI state related to the form display and interaction state
 */
data class ProductFormUiState(
    val isEdit: Boolean = false,
    val selectedTab: Int = 0,
    val hasSpecifications: Boolean = false,
    val error: String? = null,
    val isFormValid: Boolean = false,
)

/**
 * Combined state class for the product view model
 */
data class ProductUiState(
    val uiState: ProductFormUiState = ProductFormUiState(),
    val formData: ProductFormData = ProductFormData(),
    val formOptions: ProductFormOptions = ProductFormOptions(),
    // Reference to original product when editing or viewing
    val selectedProduct: Product? = null,
    val showRatingModal: Boolean = false,
    val reviews: List<Review> = emptyList()
) 