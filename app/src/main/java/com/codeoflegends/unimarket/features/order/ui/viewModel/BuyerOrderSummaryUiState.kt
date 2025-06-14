package com.codeoflegends.unimarket.features.order.ui.viewModel

import com.codeoflegends.unimarket.features.order.data.model.Delivery
import com.codeoflegends.unimarket.features.order.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.OrderDetail
import com.codeoflegends.unimarket.features.order.data.model.Payment
import java.util.UUID

class BuyerOrderSummaryUiState (
    val id: UUID? = null,
    val date: String = "",
    val status: String = "",
    val products: List<OrderDetail> = emptyList(),
    val payment: Payment? = null,
    val entrepreneurship: Entrepreneurship ?= null,
    val delivery: Delivery? = null,
    val order: Order? = null,
)