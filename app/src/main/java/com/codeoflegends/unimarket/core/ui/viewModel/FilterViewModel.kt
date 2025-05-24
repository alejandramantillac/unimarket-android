package com.codeoflegends.unimarket.core.ui.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FilterOption(
    val id: String,
    val label: String,
    val isSelected: Boolean = false
)

data class FilterState(
    val resultMessage: String = "",
    val searchQuery: String = "",
    val filters: List<FilterOption> = emptyList(),
    val showAdvancedFilters: Boolean = false
)

abstract class FilterViewModel : ViewModel() {
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _filterState.update { it.copy(searchQuery = query) }
        onSearchQueryChanged(query)
    }

    fun toggleFilter(filterId: String) {
        _filterState.update { currentState ->
            val updatedFilters = currentState.filters.map { filter ->
                if (filter.id == filterId) {
                    filter.copy(isSelected = !filter.isSelected)
                } else {
                    filter
                }
            }
            currentState.copy(filters = updatedFilters)
        }
        onFilterChanged(filterId)
    }

    fun toggleAdvancedFilters() {
        _filterState.update { it.copy(showAdvancedFilters = !it.showAdvancedFilters) }
        onAdvancedFiltersToggled()
    }

    fun setFilters(filters: List<FilterOption>) {
        _filterState.update { it.copy(filters = filters) }
    }

    // Métodos abstractos que deben ser implementados por las clases hijas
    protected abstract fun onSearchQueryChanged(query: String)
    protected abstract fun onFilterChanged(filterId: String)
    protected abstract fun onAdvancedFiltersToggled()
} 