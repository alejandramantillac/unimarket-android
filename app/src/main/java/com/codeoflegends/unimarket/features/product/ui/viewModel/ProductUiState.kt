package com.codeoflegends.unimarket.features.product.ui.viewModel

import com.codeoflegends.unimarket.features.product.data.model.Category
import com.codeoflegends.unimarket.features.product.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.util.UUID

data class ProductUiState(
    val id: UUID? = null,
    val selectedBusiness: Entrepreneurship? = null,
    val selectedCategory: Category? = null,
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val lowStockAlert: String = "",
    val published: Boolean = false,
    val isEdit: Boolean = false,
    val variants: List<ProductVariant> = emptyList(),
    val specifications: List<ProductSpecification> = emptyList(),
    val businessOptions: List<Entrepreneurship> = emptyList(),
    val categoryOptions: List<Category> = emptyList(),
    val selectedTab: Int = 0,
    val hasSpecifications: Boolean = false,
    val error: String? = null,
    val isFormValid: Boolean = false,
    val product: Product? = null
) 