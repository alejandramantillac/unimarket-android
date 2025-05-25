package com.codeoflegends.unimarket.features.order.ui.viewModel

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.features.order.data.model.Delivery
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.Payment
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import java.util.UUID

data class OrderSummaryUiState(
    val id: UUID? = null,
    val date: String = "",
    val status: String = "",
    val products: List<ProductVariant> = emptyList(),
    val payment: Payment? = null,
    val client: User? = null,
    val delivery: Delivery? = null,
    val order: Order? = null,
)