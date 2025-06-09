package com.codeoflegends.unimarket.features.home.ui.viewModel.state

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipListDto
import com.codeoflegends.unimarket.features.home.data.model.Banner
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.product.data.model.Product

data class HomeUIState(
    val banners: List<Banner> = emptyList(),
    val currentRoute: String = "home",
)

sealed class HomeActionState {
    object Idle : HomeActionState()
    object Loading : HomeActionState()
    data class Error(val message: String) : HomeActionState()
    object Success : HomeActionState()
}

data class HomeProductsUiState(
    val products: List<Product> = emptyList(),
    val page: Int = 0,
    val limit: Int = 10,
    val hasMoreItems: Boolean = true,
)

data class HomeEntrepreneurshipsUiState(
    val entrepreneurships: List<EntrepreneurshipListDto> = emptyList(),
    val page: Int = 0,
    val limit: Int = 10,
    val hasMoreItems: Boolean = true,
)