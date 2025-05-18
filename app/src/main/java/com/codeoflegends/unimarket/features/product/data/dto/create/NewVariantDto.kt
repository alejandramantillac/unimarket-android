package com.codeoflegends.unimarket.features.product.data.dto.create

import com.codeoflegends.unimarket.features.product.data.dto.get.VariantImageDto

data class NewVariantDto(
    val name: String,
    val stock: Int,
    val variantImages: List<VariantImageDto> = emptyList()
)