package com.codeoflegends.unimarket.features.product.data.model

data class Product(
    val id: String? = null,
    val business: String,
    val category: String,
    val name: String,
    val description: String,
    val price: Double,
    val lowStockAlert: Int,
    val published: Boolean
) 