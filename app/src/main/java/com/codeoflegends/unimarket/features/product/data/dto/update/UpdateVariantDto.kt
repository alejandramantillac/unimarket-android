package com.codeoflegends.unimarket.features.product.data.dto.update

import java.util.UUID

data class UpdateVariantDto(
    val id: UUID?,
    val name: String,
    val stock: Int,
    val variantImages: List<UpdateVariantImageDto> = emptyList()
)