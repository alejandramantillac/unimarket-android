package com.codeoflegends.unimarket.features.product.data.model

import java.util.UUID

data class ProductVariant(
    val id: UUID? = null,
    val productId: UUID? = null,
    val name: String,
    val stock: Int,
    val variantImages: List<String> = emptyList()
)

data class ProductSpecification(
    val id: UUID? = null,
    val key: String,
    val value: String,
    val product: UUID? = null
)

data class Product(
    val id: UUID? = null,
    val business: String,
    val category: String,
    val name: String,
    val description: String,
    val price: Double,
    val lowStockAlert: Int,
    val published: Boolean,
    val variants: List<ProductVariant> = emptyList(),
    val specifications: List<ProductSpecification> = emptyList()
) 