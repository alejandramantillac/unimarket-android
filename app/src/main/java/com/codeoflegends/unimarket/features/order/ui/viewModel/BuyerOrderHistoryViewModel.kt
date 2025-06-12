package com.codeoflegends.unimarket.features.order.ui.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.viewModel.FilterOption
import com.codeoflegends.unimarket.core.ui.viewModel.FilterViewModel
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.usecase.GetAllOrdersByBuyerUseCase
import com.codeoflegends.unimarket.features.order.data.usecase.GetAllOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class HistoryOrderActionState {
    object Idle : HistoryOrderActionState()
    object Success : HistoryOrderActionState()
    data class Error(val message: String) : HistoryOrderActionState()
    object Loading : HistoryOrderActionState()
}

@HiltViewModel
class BuyerOrderHistoryViewModel @Inject constructor(
    private val getAllBuyerOrdersUseCase: GetAllOrdersByBuyerUseCase,
) : FilterViewModel() {

    init {
        setFilters(
            listOf(
                FilterOption(id = "pendiente", label = "Pendiente", field = "status", operator = "eq", value = "Pendiente"),
                FilterOption(id = "confirmado", label = "confirmado", field = "status", operator = "eq", value = "confirmado"),
            )
        )
    }

    val buyerId: MutableStateFlow<UUID?> = MutableStateFlow(null)

    private val _uiState = MutableStateFlow(OrderListUiState())
    val uiState: StateFlow<OrderListUiState> = _uiState.asStateFlow()

    private val _selectedOrder = MutableStateFlow<Order?>(null)
    val selectedOrder: StateFlow<Order?> = _selectedOrder.asStateFlow()

    private val _actionState = MutableStateFlow<HistoryOrderActionState>(HistoryOrderActionState.Idle)
    val actionState: StateFlow<HistoryOrderActionState> = _actionState.asStateFlow()

    fun loadOrders() {
        _actionState.value = HistoryOrderActionState.Loading
        viewModelScope.launch {
            try {
                val orderList = getAllBuyerOrdersUseCase()
                orderList.forEach { order ->
                    Log.d("BuyerOrderHistoryViewModel", "Order ID: ${order.id}, OrderDetails: ${order.orderDetails}")
                }
                val filteredOrders = applyFilters(orderList)
                _uiState.value = OrderListUiState(
                    orders = filteredOrders.map { order ->
                        OrderItemUiState(
                            id = order.id,
                            clientName = order.userCreated.firstName,
                            clientPhoto = order.userCreated.profile.profilePicture,
                            productCount = order.orderDetails.sumOf { it.amount },
                            totalPrice = order.total,
                            date = order.date.toString(),
                            status = order.status.name
                        )
                    }
                )
                _actionState.value = HistoryOrderActionState.Idle
            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar pedidos: ${e.message}")
                _actionState.value = HistoryOrderActionState.Error("Error al cargar pedidos: ${e.message}")
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
        private const val TAG = "BuyerOrderListViewModel"
    }
}