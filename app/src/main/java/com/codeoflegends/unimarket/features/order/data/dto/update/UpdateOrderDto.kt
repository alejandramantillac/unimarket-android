package com.codeoflegends.unimarket.features.order.data.dto.update

import com.codeoflegends.unimarket.features.order.data.model.OrderStatus

class UpdateOrderDto (
    val status: OrderStatus?,
    val payments: List<Any?>,
    val delivery: List<UpdateDeliveryDto>?,
)