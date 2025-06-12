package com.codeoflegends.unimarket.features.order.data.dto.create

import java.util.UUID

data class CreateOrderDetailDto(
    val amount: Int,
    val unit_price: Int,
    val product_variant: UUID,
)