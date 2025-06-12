package com.codeoflegends.unimarket.features.home.ui.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.viewModel.FilterViewModel
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetAllEntrepreneurshipByQuery
import com.codeoflegends.unimarket.features.home.ui.viewModel.state.HomeActionState
import com.codeoflegends.unimarket.features.home.ui.viewModel.state.HomeEntrepreneurshipsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntrepreneurshipSectionViewModel @Inject constructor(
    private val getAllEntrepreneurshipByQuery: GetAllEntrepreneurshipByQuery,
) : FilterViewModel() {

    private val _uiState = MutableStateFlow(HomeEntrepreneurshipsUiState())
    val uiState: StateFlow<HomeEntrepreneurshipsUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<HomeActionState>(HomeActionState.Idle)
    val actionState: StateFlow<HomeActionState> = _actionState.asStateFlow()

    init {
        setFilters(
            emptyList()
        )
    }

    fun loadMoreEntrepreneurships() {
        if (!_uiState.value.hasMoreItems) {
            return
        }
        _actionState.value = HomeActionState.Loading
        viewModelScope.launch {
            try {
                val entrepreneurships = getAllEntrepreneurshipByQuery(
                    nameContains = filterState.value.searchQuery,
                    filters = filterToQuery(),
                    page = _uiState.value.page + 1,
                    limit = _uiState.value.limit
                )
                if (entrepreneurships.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        entrepreneurships = _uiState.value.entrepreneurships.plus(entrepreneurships),
                        page = _uiState.value.page + 1
                    )
                } else {
                    _uiState.value = _uiState.value.copy(hasMoreItems = false)
                }
                _actionState.value = HomeActionState.Idle
            } catch (e: Exception) {
                _actionState.value =
                    HomeActionState.Error("Error al cargar emprendimientos: ${e.message}")
            }
        }
    }

    fun loadMoreEntrepreneurships(filters: List<DirectusQuery.Filter>) {
        if (!_uiState.value.hasMoreItems) {
            return
        }
        _actionState.value = HomeActionState.Loading
        viewModelScope.launch {
            try {
                val entrepreneurships = getAllEntrepreneurshipByQuery(
                    filters = filters,
                    page = _uiState.value.page + 1,
                    limit = _uiState.value.limit
                )
                Log.i("EntrepreneurshipSectionViewModel", "Cargando emprendimientos: $entrepreneurships")
                if (entrepreneurships.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        entrepreneurships = _uiState.value.entrepreneurships.plus(entrepreneurships),
                        page = _uiState.value.page + 1
                    )
                } else {
                    _uiState.value = _uiState.value.copy(hasMoreItems = false)
                }
                _actionState.value = HomeActionState.Idle
            } catch (e: Exception) {
                Log.i("EntrepreneurshipSectionViewModel", "Error al cargar los emprendimientos: ${e.message}")
                _actionState.value =
                    HomeActionState.Error("Error al cargar emprendimientos: ${e.message}")
            }
        }
    }

    fun resetQueryParameters() {
        _uiState.value = _uiState.value.copy(
            page = 0,
            entrepreneurships = emptyList(),
            hasMoreItems = true
        )
    }

    override fun onSearchQueryChanged(query: String) {
        resetQueryParameters()
        loadMoreEntrepreneurships()
    }

    override fun onFilterChanged(filterId: String) {
        resetQueryParameters()
        loadMoreEntrepreneurships()
    }

    override fun onAdvancedFiltersToggled() {
        // Implementar si es necesario
    }
} 