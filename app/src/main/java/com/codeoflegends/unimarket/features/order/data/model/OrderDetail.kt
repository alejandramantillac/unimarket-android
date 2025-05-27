package com.codeoflegends.unimarket.features.order.data.model

import com.codeoflegends.unimarket.features.order.data.dto.get.ProductVariantDto
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import java.util.UUID

class OrderDetail (
    val id: UUID,
    val amount: Int,
    val unitPrice: Int,
    val productVariant: ProductVariant,
)