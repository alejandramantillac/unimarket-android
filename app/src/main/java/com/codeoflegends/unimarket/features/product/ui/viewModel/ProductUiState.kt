package com.codeoflegends.unimarket.features.product.ui.viewModel

import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import java.util.UUID

data class ProductUiState(
    val id: UUID? = null,
    val selectedTab: Int = 0,
    val businessOptions: List<String> = emptyList(),
    val selectedBusiness: String? = null,
    val categoryOptions: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val name: String = "",
    val description: String = "",
    val sku: String = "",
    val price: String = "",
    val quantity: String = "",
    val lowStockAlert: String = "",
    val published: Boolean = false,
    val isEdit: Boolean = false, // Para distinguir entre crear y editar
    val variants: List<ProductVariant> = emptyList() // Variantes del producto
) 