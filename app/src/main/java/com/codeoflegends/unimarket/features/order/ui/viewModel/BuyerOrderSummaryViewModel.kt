package com.codeoflegends.unimarket.features.order.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.order.data.usecase.GetOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class BuyerOrderSummaryActionState {
    object Idle : BuyerOrderSummaryActionState()
    object Success : BuyerOrderSummaryActionState()
    data class Error(val message: String) : BuyerOrderSummaryActionState()
    object Loading : BuyerOrderSummaryActionState()
}

@HiltViewModel
class BuyerOrderSummaryViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BuyerOrderSummaryUiState())
    val uiState: StateFlow<BuyerOrderSummaryUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<BuyerOrderSummaryActionState>(BuyerOrderSummaryActionState.Idle)
    val actionState: StateFlow<BuyerOrderSummaryActionState> = _actionState.asStateFlow()

    private fun validateAndGetUUID(orderId: String?): UUID? {
        if (orderId.isNullOrEmpty()) {
            _actionState.value = BuyerOrderSummaryActionState.Error("ID de orden inválido")
            return null
        }

        return try {
            UUID.fromString(orderId)
        } catch (e: Exception) {
            _actionState.value = BuyerOrderSummaryActionState.Error("ID de orden inválido")
            null
        }
    }

    fun loadOrder(orderId: String?) {
        val uuid = validateAndGetUUID(orderId) ?: return

        viewModelScope.launch {
            _actionState.value = BuyerOrderSummaryActionState.Loading
            try {
                val order = getOrderUseCase(UUID.fromString(orderId))

                _uiState.value = BuyerOrderSummaryUiState(
                    id = order.id ?: UUID.randomUUID(),
                    status = order.status.toString(),
                    products = order.orderDetails.map { it },
                    payment = order.payments.firstOrNull(),
                    entrepreneurship = order.entrepreneurship,
                    delivery = order.delivery.firstOrNull(),
                    order = order
                )
                _actionState.value = BuyerOrderSummaryActionState.Success
            }catch (e: Exception) {
                _actionState.value = BuyerOrderSummaryActionState.Error("Error al cargar el pedido: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "BuyerOrderSummaryViewModel"
    }

}