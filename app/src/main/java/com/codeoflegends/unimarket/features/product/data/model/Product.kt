package com.codeoflegends.unimarket.features.product.data.model

import java.util.UUID

data class Product(
    val id: UUID? = null,
    val entrepreneurship: Entrepreneurship,
    val imageUrl: String? = null,
    val category: ProductCategory,
    val name: String,
    val description: String,
    val price: Double,
    val stockAlert: Int,
    val published: Boolean,
    val variants: List<ProductVariant> = emptyList(),
    val specifications: List<ProductSpecification> = emptyList(),
    val reviews: List<Review> = emptyList()
)

