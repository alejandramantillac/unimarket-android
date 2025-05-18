package com.codeoflegends.unimarket.features.product.data.dto

import java.util.UUID

data class SpecificationDto(
    val id: UUID? = null,
    val key: String,
    val value: String,
)