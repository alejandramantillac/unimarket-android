package com.codeoflegends.unimarket.features.order.data.dto.get

import java.util.UUID

data class OrderDetailDto(
    val id: UUID,
    val amount: Int,
    val unitPrice: Int,
    val productVariant: ProductVariantDto,
)