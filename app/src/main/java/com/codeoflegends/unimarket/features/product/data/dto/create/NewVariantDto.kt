package com.codeoflegends.unimarket.features.product.data.dto.create

data class NewVariantDto(
    val name: String,
    val stock: Int,
    val variantImages: List<NewVariantImageDto> = emptyList()
)