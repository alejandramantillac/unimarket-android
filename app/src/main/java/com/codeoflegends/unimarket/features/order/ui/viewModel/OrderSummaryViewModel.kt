package com.codeoflegends.unimarket.features.order.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.order.data.dto.get.DeliveryStatusDto
import com.codeoflegends.unimarket.features.order.data.model.OrderStatus
import com.codeoflegends.unimarket.features.order.data.usecase.GetOrderUseCase
import com.codeoflegends.unimarket.features.order.data.usecase.UpdateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class OrderSummaryActionState {
    object Idle : OrderSummaryActionState()
    object Success : OrderSummaryActionState()
    data class Error(val message: String) : OrderSummaryActionState()
    object Loading : OrderSummaryActionState()
}

@HiltViewModel
class OrderSummaryViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderSummaryUiState())
    val uiState: StateFlow<OrderSummaryUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<OrderSummaryActionState>(OrderSummaryActionState.Idle)
    val actionState: StateFlow<OrderSummaryActionState> = _actionState.asStateFlow()

    private fun validateAndGetUUID(orderId: String?): UUID? {
        if (orderId.isNullOrEmpty()) {
            _actionState.value = OrderSummaryActionState.Error("ID de orden inválido")
            return null
        }

        return try {
            UUID.fromString(orderId)
        } catch (e: Exception) {
            _actionState.value = OrderSummaryActionState.Error("ID de orden inválido")
            null
        }
    }

    fun loadOrder(orderId: String?) {
        val uuid = validateAndGetUUID(orderId) ?: return

        viewModelScope.launch {
            _actionState.value = OrderSummaryActionState.Loading
            try {
                val order = getOrderUseCase(UUID.fromString(orderId))

                _uiState.value = OrderSummaryUiState(
                    id = order.id ?: UUID.randomUUID(),
                    status = order.status.toString(),
                    products = order.orderDetails.map { it },
                    payment = order.payments.firstOrNull(),
                    client = order.userCreated,
                    delivery = order.delivery.firstOrNull(),
                    order = order
                )
                _actionState.value = OrderSummaryActionState.Success
            }catch (e: Exception) {
                _actionState.value = OrderSummaryActionState.Error("Error al cargar el emprendimiento: ${e.message}")
            }
        }
    }

    fun updateOrderStatus(newStatus: String) {
        val currentOrder = _uiState.value.order ?: return

        viewModelScope.launch {
            _actionState.value = OrderSummaryActionState.Loading
            try {
                val updatedOrderStatus = OrderStatus(
                    id = currentOrder.status.id,
                    name = newStatus
                )
                val updatedOrder = currentOrder.copy(status = updatedOrderStatus)
                updateOrderUseCase(updatedOrder)
                _uiState.value = _uiState.value.copy(order = updatedOrder)
                _actionState.value = OrderSummaryActionState.Success
            } catch (e: Exception) {
                _actionState.value = OrderSummaryActionState.Error("Error al actualizar el estado de la orden: ${e.message}")
            }
        }
    }

    fun updateDeliveryStatus(newStatusId: String) {
        val currentOrder = _uiState.value.order ?: return
        val currentDelivery = currentOrder.delivery.firstOrNull() ?: return

        viewModelScope.launch {
            _actionState.value = OrderSummaryActionState.Loading
            try {
                val updatedDeliveryStatus = DeliveryStatusDto(
                    id = UUID.fromString(newStatusId),
                    name = "Entregado"
                )
                val updatedDelivery = currentDelivery.copy(status = updatedDeliveryStatus)
                val updatedOrder = currentOrder.copy(delivery = listOf(updatedDelivery))
                updateOrderUseCase(updatedOrder)
                _uiState.value = _uiState.value.copy(order = updatedOrder)
                _actionState.value = OrderSummaryActionState.Success
                Log.d(TAG, "Estado del delivery actualizado correctamente: $updatedDeliveryStatus")
            } catch (e: Exception) {
                _actionState.value = OrderSummaryActionState.Error("Error al actualizar el estado del delivery: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "OrderSummaryViewModel"
    }
}