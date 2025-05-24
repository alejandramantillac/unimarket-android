package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.viewModel.FilterOption
import com.codeoflegends.unimarket.core.ui.viewModel.FilterViewModel
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class EntrepreneurshipProductsActionState {
    object Idle : EntrepreneurshipProductsActionState()
    object Loading : EntrepreneurshipProductsActionState()
    data class Error(val message: String) : EntrepreneurshipProductsActionState()
    object Success : EntrepreneurshipProductsActionState()
}

@HiltViewModel
class EntrepreneurshipProductsViewModel @Inject constructor(
    private val getAllEntrepreneurshipProductsUseCase: GetAllProductsUseCase,
    ): FilterViewModel() {

    init {
        setFilters(
            listOf(
                FilterOption("active", "Activos"),
                FilterOption("inactive", "Inactivos"),
                FilterOption("featured", "Destacados"),
                FilterOption("new", "Nuevos"),
                FilterOption("popular", "Populares"),
                FilterOption("sale", "En oferta")
            )
        )
    }

    private val _uiState = MutableStateFlow(EntrepreneurshipProductsUiState())
    val uiState: StateFlow<EntrepreneurshipProductsUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipProductsActionState>(EntrepreneurshipProductsActionState.Idle)
    val actionState: StateFlow<EntrepreneurshipProductsActionState> = _actionState.asStateFlow()

    fun loadMoreProducts(entrepreneurshipId: UUID) {
        if (!_uiState.value.hasMoreItems) {
            return
        }

        _actionState.value = EntrepreneurshipProductsActionState.Loading
        Log.i("EntrepreneurshipProductsViewModel", "Loading more products for entrepreneurship $entrepreneurshipId")

        viewModelScope.launch {
            try {
                val products = getAllEntrepreneurshipProductsUseCase(
                    entrepreneurshipId = entrepreneurshipId,
                    nameContains = _uiState.value.searchQuery,
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
                _actionState.value = EntrepreneurshipProductsActionState.Idle
            } catch (e: Exception) {
                Log.i("EntrepreneurshipProductsViewModel", "Error al cargar los productos: ${e.message}")
                _actionState.value =
                    EntrepreneurshipProductsActionState.Error("Error al cargar productos: ${e.message}")
            }
        }
    }

    fun updateSearchQuery(query: String?, entrepreneurshipId: UUID) {
        Log.i("EntrepreneurshipProductsViewModel", "Updating search query")
        _uiState.value = _uiState.value.copy(
            searchQuery = query?: "",
            page = 0,
            products = emptyList(),
            hasMoreItems = true
        )
        // Cargar productos con el nuevo término de búsqueda
        loadMoreProducts(entrepreneurshipId)
    }

    override fun onSearchQueryChanged(query: String) {
        TODO("Not yet implemented")
    }

    override fun onFilterChanged(filterId: String) {
        TODO("Not yet implemented")
    }

    override fun onAdvancedFiltersToggled() {
        TODO("Not yet implemented")
    }
}