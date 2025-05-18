package com.codeoflegends.unimarket.features.order.data.model

import com.codeoflegends.unimarket.features.order.ui.components.OrderStatus
import java.util.UUID

data class Order(
    val id: UUID = UUID.randomUUID(),
    val products: List<OrderProduct>,
    val totalAmount: Double = products.sumOf { it.totalPrice },
    val date: String,
    val status: List<OrderStatus>,
    val payment: Payment,
    val client: Client,
    val delivery: Delivery
)

data class Payment(
    val method: String,
    val evidenceImage: String,
    val products: List<OrderProduct>,
    val discountRate: Double = 0.1 // Descuento del 10%
) {
    val subtotal: Double
        get() = products.sumOf { it.totalPrice }.let {
        String.format("%.2f", it).toDouble()
    }

    val discounts: Double
        get() = subtotal * discountRate

    val total: Double
        get() = subtotal - discounts
}

data class Client(
    val photo: String,
    val name: String,
    val email: String,
    val phone: String,
    val sinceYear: String
)

data class Delivery(
    val method: String,
    val location: String,
    val dateTime: String
)
