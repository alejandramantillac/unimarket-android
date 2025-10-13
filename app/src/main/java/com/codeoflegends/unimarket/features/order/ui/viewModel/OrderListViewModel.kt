package com.codeoflegends.unimarket.features.order.ui.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.viewModel.FilterOption
import com.codeoflegends.unimarket.core.ui.viewModel.FilterViewModel
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.usecase.GetAllOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class OrderActionState {
    object Idle : OrderActionState()
    object Success : OrderActionState()
    data class Error(val message: String) : OrderActionState()
    object Loading : OrderActionState()
}

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
) : FilterViewModel() {

    init {
        setFilters(
            listOf(
                FilterOption(id = "pendiente", label = "Pendiente", field = "status", operator = "eq", value = "pendiente"),
                FilterOption(id = "confirmado", label = "Confirmado", field = "status", operator = "eq", value = "confirmado"),
            )
        )
    }

    val entrepreneurshipId: MutableStateFlow<UUID?> = MutableStateFlow(null)

    private val _uiState = MutableStateFlow(OrderListUiState())
    val uiState: StateFlow<OrderListUiState> = _uiState.asStateFlow()

    private val _selectedOrder = MutableStateFlow<Order?>(null)
    val selectedOrder: StateFlow<Order?> = _selectedOrder.asStateFlow()

    private val _actionState = MutableStateFlow<OrderActionState>(OrderActionState.Idle)
    val actionState: StateFlow<OrderActionState> = _actionState.asStateFlow()

    fun loadOrders() {
        _actionState.value = OrderActionState.Loading
        viewModelScope.launch {
            try {
                val orderList = getAllOrdersUseCase(entrepreneurshipId.value!!)
                orderList.forEach { order ->
                    Log.d("OrderListViewModel", "Order ID: ${order.id}, OrderDetails: ${order.orderDetails}")
                }
                val filteredOrders = applyFilters(orderList)
                _uiState.value = OrderListUiState(
                    orders = filteredOrders.map { order ->
                        OrderItemUiState(
                            id = order.id,
                            clientName = order.userCreated.firstName,
                            clientPhoto = order.userCreated.profile.profilePicture.orEmpty(),
                            productCount = order.orderDetails.sumOf { it.amount },
                            totalPrice = order.total,
                            date = order.date.toString(),
                            status = order.status.name
                        )
                    }
                )
                _actionState.value = OrderActionState.Idle
            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar órdenes: ${e.message}")
                _actionState.value = OrderActionState.Error("Error al cargar órdenes: ${e.message}")
            }
        }
    }

    private fun applyFilters(orderList: List<Order>): List<Order> {
        val activeFilters = filterToQuery()
        return orderList.filter { order ->
            activeFilters.all { filter ->
                when (filter.field) {
                    "clientName" -> order.userCreated.firstName.contains(filter.value.toString(), ignoreCase = true)
                    "status" -> order.status.name == filter.value
                    else -> true
                }
            }
        }.filter { order ->
            filterState.value.searchQuery.isEmpty() ||
                    order.userCreated.firstName.contains(filterState.value.searchQuery, ignoreCase = true)
        }
    }

    fun resetQueryParameters() {
        _uiState.value = OrderListUiState(orders = emptyList())
    }

    override fun onSearchQueryChanged(query: String) {
        resetQueryParameters()
        loadOrders()
    }

    override fun onFilterChanged(filterId: String) {
        resetQueryParameters()
        loadOrders()
    }

    override fun onAdvancedFiltersToggled() {
        // Implementar lógica adicional si es necesario
    }

    companion object {
        private const val TAG = "OrderListViewModel"
    }
}