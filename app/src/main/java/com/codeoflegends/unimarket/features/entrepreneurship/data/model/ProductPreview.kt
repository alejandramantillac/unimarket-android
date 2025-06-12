package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import java.util.UUID

data class ProductPreview(
    val id: UUID,
    val name: String,
    val imageUrl: String,
    val price: Double
)