package com.codeoflegends.unimarket.features.order.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.usecase.GetAllOrdersUseCase
import com.codeoflegends.unimarket.features.order.data.usecase.GetOrderUseCase
import com.codeoflegends.unimarket.features.order.data.usecase.UpdateOrderStatusUseCase
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderListUiState())
    val uiState: StateFlow<OrderListUiState> = _uiState.asStateFlow()

    private val _selectedOrder = MutableStateFlow<Order?>(null)
    val selectedOrder: StateFlow<Order?> = _selectedOrder.asStateFlow()

    private val _actionState = MutableStateFlow<OrderActionState>(OrderActionState.Idle)
    val actionState: StateFlow<OrderActionState> = _actionState.asStateFlow()

    init {
        loadOrders()
    }

    fun loadOrders() {
        _actionState.value = OrderActionState.Loading
        viewModelScope.launch {
            try {
                val orderList = getAllOrdersUseCase()
                _uiState.value = OrderListUiState(
                    orders = orderList.map { order ->
                        OrderItemUiState(
                            id = order.id.toString(),
                            clientName = order.userCreated.firstName,
                            clientPhoto = order.userCreated.profile.profilePicture,
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

    companion object {
        private const val TAG = "OrderListViewModel"
    }
}