package com.codeoflegends.unimarket.features.product.data.repositories

import com.codeoflegends.unimarket.features.product.data.model.Product

interface IProductRepository {
    suspend fun createProduct(product: Product): Result<Unit>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(productId: String): Result<Unit>
    suspend fun getProduct(productId: String): Result<Product>
} 