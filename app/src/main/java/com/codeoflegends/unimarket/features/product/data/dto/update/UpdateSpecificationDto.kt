package com.codeoflegends.unimarket.features.product.data.dto.update

import java.util.UUID

data class UpdateSpecificationDto(
    val id: UUID?,
    val key: String,
    val value: String,
)