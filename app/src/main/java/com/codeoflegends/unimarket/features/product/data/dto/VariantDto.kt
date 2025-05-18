package com.codeoflegends.unimarket.features.product.data.dto

import java.util.UUID

data class VariantDto(
    val id: UUID? = null,
    val name: String,
    val stock: Int,
    val variantImages: List<String> = emptyList()
)