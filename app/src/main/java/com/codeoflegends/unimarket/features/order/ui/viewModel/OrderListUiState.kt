package com.codeoflegends.unimarket.features.order.ui.viewModel

import com.codeoflegends.unimarket.features.order.data.model.Order
import java.util.UUID

data class OrderListUiState(
    val orders: List<OrderItemUiState> = emptyList(),
)

data class OrderItemUiState(
    val id: UUID,
    val clientName: String,
    val clientPhoto: String?,
    val productCount: Int,
    val totalPrice: Int,
    val date: String,
    val status: String
)