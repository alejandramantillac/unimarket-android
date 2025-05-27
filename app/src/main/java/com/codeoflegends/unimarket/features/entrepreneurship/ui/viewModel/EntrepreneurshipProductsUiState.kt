package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import com.codeoflegends.unimarket.features.product.data.model.Product

data class EntrepreneurshipProductsUiState(
    val products: List<Product> = emptyList(),
    val page: Int = 0,
    val limit: Int = 5,
    val hasMoreItems: Boolean = true,
)