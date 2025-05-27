package com.codeoflegends.unimarket.features.product.data.dto.get

import java.util.UUID

data class SpecificationDto(
    val id: UUID,
    val key: String,
    val value: String,
)