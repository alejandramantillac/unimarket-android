package com.codeoflegends.unimarket.features.order.data.model

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.features.order.data.dto.get.DeliveryDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDetailDto
import com.codeoflegends.unimarket.features.order.data.dto.get.PaymentDto
import java.time.LocalDateTime
import java.util.UUID

data class Order (
    val id: UUID,
    val status: OrderStatus,
    val date: String,
    val subtotal: Int,
    val discount: Int,
    val total: Int,
    val userCreated: User,
    val payments: List<Payment>,
    val orderDetails: List<OrderDetail>,
    val delivery: List<Delivery>,
    val entrepreneurship: Entrepreneurship
)

