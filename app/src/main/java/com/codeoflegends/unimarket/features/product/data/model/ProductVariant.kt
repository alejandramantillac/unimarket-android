package com.codeoflegends.unimarket.features.product.data.model

import java.util.UUID

data class ProductVariant(
    val id: UUID? = null,
    val name: String,
    val stock: Int,
    val variantImages: List<VariantImage> = emptyList()
)