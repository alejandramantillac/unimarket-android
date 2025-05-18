package com.codeoflegends.unimarket.features.product.data.model

import java.util.UUID

data class ProductVariant(
    val id: UUID? = null,
    val name: String,
    val stock: Int,
    val variantImages: List<VariantImage> = emptyList()
)

data class VariantImage(
    val id: UUID? = null,
    val imageUrl: String,
)

data class ProductSpecification(
    val id: UUID? = null,
    val key: String,
    val value: String,
)

data class Product(
    val id: UUID? = null,
    val entrepreneurship: Entrepreneurship,
    val category: Category,
    val name: String,
    val description: String,
    val price: Double,
    val stockAlert: Int,
    val published: Boolean,
    val variants: List<ProductVariant> = emptyList(),
    val specifications: List<ProductSpecification> = emptyList(),
    val reviews: List<Review> = emptyList()
)

data class Category(
    val name: String,
    val description: String,
)