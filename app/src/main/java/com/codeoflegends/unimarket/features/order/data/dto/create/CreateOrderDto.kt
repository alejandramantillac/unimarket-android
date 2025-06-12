package com.codeoflegends.unimarket.features.order.data.dto.create

import java.util.UUID

data class CreateOrderDto(
    val status:String,
    val subtotal: Int,
    val discount: Int,
    val total: Int,
    val user_created: UUID,
    val entrepreneurship: UUID,
    val order_details: List<CreateOrderDetailDto>
)

data class StatusDto(
    val id: UUID
)

data class CreatedOrderDto(
    val id: UUID,
    val status: UUID,
    val date: String,
    val subtotal: Int,
    val discount: Int,
    val total: Int,
    val user_created: UUID,
    val entrepreneurship: UUID
)