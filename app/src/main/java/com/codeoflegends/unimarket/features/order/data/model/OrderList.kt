package com.codeoflegends.unimarket.features.order.data.model

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderStatusDto
import java.time.LocalDateTime

class OrderList (
    val status: OrderStatus,
    val date: LocalDateTime,
    val subtotal: Int,
    val discount: Int,
    val total: Int,
    val userCreated: User,
)