package com.codeoflegends.unimarket.features.product.data.dto.get

import java.util.UUID

data class VariantDto(
    val id: UUID,
    val name: String,
    val stock: Int,
    val variantImages: List<VariantImageDto> = emptyList()
)