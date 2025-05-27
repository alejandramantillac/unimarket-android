package com.codeoflegends.unimarket.features.product.data.model

import java.util.UUID

data class ProductSpecification(
    val id: UUID? = null,
    val key: String,
    val value: String,
)