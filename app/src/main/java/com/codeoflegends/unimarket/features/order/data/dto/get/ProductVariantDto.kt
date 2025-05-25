package com.codeoflegends.unimarket.features.order.data.dto.get

import com.codeoflegends.unimarket.features.product.data.dto.get.VariantImageDto
import java.util.UUID

class ProductVariantDto(
    val id: UUID,
    val name: String,
    val stock: Int,
    val variantImages: List<VariantImageDto>,
    val product: ProductDto
)