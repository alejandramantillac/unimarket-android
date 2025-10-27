package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.viewModel.FilterOption
import com.codeoflegends.unimarket.core.ui.viewModel.FilterViewModel
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsByQueryUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class EntrepreneurshipGeneralProductsActionState {
    object Idle : EntrepreneurshipGeneralProductsActionState()
    object Loading : EntrepreneurshipGeneralProductsActionState()
    data class Error(val message: String) : EntrepreneurshipGeneralProductsActionState()
    object Success : EntrepreneurshipGeneralProductsActionState()
}

sealed class EntrepreneurshipQueryProductsActionState {
    object Idle : EntrepreneurshipQueryProductsActionState()
    object Loading : EntrepreneurshipQueryProductsActionState()
    data class Error(val message: String) : EntrepreneurshipQueryProductsActionState()
}


@HiltViewModel
class EntrepreneurshipProductsViewModel @Inject constructor(
    private val getAllProductsByQueryUseCase: GetAllProductsByQueryUseCase,
    ): FilterViewModel() {

    private lateinit var entrepreneurshipId: UUID

    init {
        setFilters(
            listOf(
                FilterOption(id = "activo", label = "Activo", field = "published", operator = "eq", value = true),
                FilterOption(id = "inactivo", label = "Inactivo", field = "published", operator = "eq", value = false)
            )
        )
    }

    private val _uiState = MutableStateFlow(EntrepreneurshipProductsUiState())
    val uiState: StateFlow<EntrepreneurshipProductsUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipGeneralProductsActionState>(EntrepreneurshipGeneralProductsActionState.Idle)
    val actionState: StateFlow<EntrepreneurshipGeneralProductsActionState> = _actionState.asStateFlow()

    private val _queryActionState = MutableStateFlow<EntrepreneurshipQueryProductsActionState>(EntrepreneurshipQueryProductsActionState.Loading)
    val queryActionState: StateFlow<EntrepreneurshipQueryProductsActionState> = _queryActionState.asStateFlow()

    fun initialize(entrepreneurshipId: UUID) {
        this.entrepreneurshipId = entrepreneurshipId
        loadMoreProducts(entrepreneurshipId)
    }

    fun loadMoreProducts(entrepreneurshipId: UUID) {
        if (!_uiState.value.hasMoreItems) {
            return
        }

        _queryActionState.value = EntrepreneurshipQueryProductsActionState.Loading
        Log.i("EntrepreneurshipProductsViewModel", "Loading more products for entrepreneurship $entrepreneurshipId")

        viewModelScope.launch {
            try {
                val products = getAllProductsByQueryUseCase(
                    entrepreneurshipId = entrepreneurshipId,
                    nameContains = filterState.value.searchQuery,
                    filters = filterToQuery(),
                    page = _uiState.value.page + 1,
                    limit = _uiState.value.limit
                )

                Log.i("EntrepreneurshipProductsViewModel", "Productos resultado: $products")

                if (products.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        products = _uiState.value.products.plus(products),
                        page = _uiState.value.page + 1
                    )
                } else {
                    _uiState.value = _uiState.value.copy(hasMoreItems = false)
                }
                _queryActionState.value = EntrepreneurshipQueryProductsActionState.Idle
            } catch (e: Exception) {
                Log.i("EntrepreneurshipProductsViewModel", "Error al cargar los productos: ${e.message}")
                _queryActionState.value =
                    EntrepreneurshipQueryProductsActionState.Error("Error al cargar productos: ${e.message}")
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

    fun refreshProducts() {
        resetQueryParameters()
        loadMoreProducts(entrepreneurshipId)
    }

    override fun onSearchQueryChanged(query: String) {
        resetQueryParameters()
        loadMoreProducts(entrepreneurshipId)
    }

    override fun onFilterChanged(filterId: String) {
        resetQueryParameters()
        loadMoreProducts(entrepreneurshipId)
    }

    override fun onAdvancedFiltersToggled() {
        TODO("Not yet implemented")
    }
}