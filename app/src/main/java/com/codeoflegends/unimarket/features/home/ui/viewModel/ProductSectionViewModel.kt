package com.codeoflegends.unimarket.features.home.ui.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.viewModel.FilterOption
import com.codeoflegends.unimarket.core.ui.viewModel.FilterViewModel
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.home.ui.viewModel.state.HomeActionState
import com.codeoflegends.unimarket.features.home.ui.viewModel.state.HomeProductsUiState
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSectionViewModel @Inject constructor(
    private val getAllProductsByQueryUseCase: GetAllProductsByQueryUseCase,
) : FilterViewModel() {

    private val _uiState = MutableStateFlow(HomeProductsUiState())
    val uiState: StateFlow<HomeProductsUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<HomeActionState>(HomeActionState.Idle)
    val actionState: StateFlow<HomeActionState> = _actionState.asStateFlow()

    init {
        setFilters(
            listOf(
                FilterOption("descuento", "Ofertas", "discount", "gt", 0),
                FilterOption("moda", "Moda", "category", "eq", "Moda"),
                FilterOption("tecnologia", "Tecnologia", "category", "eq", "Tecnologia"),
            )
        )
    }

    fun loadMoreProducts() {
        if (!_uiState.value.hasMoreItems) {
            return
        }
        _actionState.value = HomeActionState.Loading
        viewModelScope.launch {
            try {
                val products = getAllProductsByQueryUseCase(
                    nameContains = filterState.value.searchQuery,
                    filters = filterToQuery(),
                    page = _uiState.value.page + 1,
                    limit = _uiState.value.limit
                )
                if (products.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        products = _uiState.value.products.plus(products),
                        page = _uiState.value.page + 1
                    )
                } else {
                    _uiState.value = _uiState.value.copy(hasMoreItems = false)
                }
                _actionState.value = HomeActionState.Idle
            } catch (e: Exception) {
                _actionState.value =
                    HomeActionState.Error("Error al cargar productos: ${e.message}")
            }
        }
    }

    fun loadMoreProducts(filters: List<DirectusQuery.Filter>) {
        if (!_uiState.value.hasMoreItems) {
            return
        }
        _actionState.value = HomeActionState.Loading
        viewModelScope.launch {
            try {
                val products = getAllProductsByQueryUseCase(
                    filters = filters,
                    page = _uiState.value.page + 1,
                    limit = _uiState.value.limit
                )
                if (products.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        products = _uiState.value.products.plus(products),
                        page = _uiState.value.page + 1
                    )
                } else {
                    _uiState.value = _uiState.value.copy(hasMoreItems = false)
                }
                _actionState.value = HomeActionState.Idle
            } catch (e: Exception) {
                Log.i("ProductSectionViewModel", "Error al cargar los productos: ${e.message}")
                _actionState.value =
                    HomeActionState.Error("Error al cargar productos: ${e.message}")
            }
        }
    }

    fun resetQueryParameters() {
        _uiState.value = _uiState.value.copy(
            page = 0,
            products = emptyList(),
            hasMoreItems = true
        )
    }

    override fun onSearchQueryChanged(query: String) {
        resetQueryParameters()
        loadMoreProducts()
    }

    override fun onFilterChanged(filterId: String) {
        resetQueryParameters()
        loadMoreProducts()
    }

    override fun onAdvancedFiltersToggled() {
        // Implementar si es necesario
    }
} 