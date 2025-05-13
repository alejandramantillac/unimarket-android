package com.codeoflegends.unimarket.features.product.viewmodel

data class ProductUiState(
    val id: String? = null,
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
    val isEdit: Boolean = false // Para distinguir entre crear y editar
) 