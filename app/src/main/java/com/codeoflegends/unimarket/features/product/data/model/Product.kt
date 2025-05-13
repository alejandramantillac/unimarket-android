package com.codeoflegends.unimarket.features.product.model

data class Product(
    val id: String? = null,
    val business: String,
    val category: String,
    val name: String,
    val description: String,
    val sku: String,
    val price: Double,
    val quantity: Int,
    val lowStockAlert: Int,
    val published: Boolean
) 