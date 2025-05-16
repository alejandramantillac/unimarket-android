package com.codeoflegends.unimarket.features.product.data.repositories

import com.codeoflegends.unimarket.features.product.data.model.Product
import java.util.UUID

interface IProductRepository {
    suspend fun createProduct(product: Product): Result<Unit>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(productId: UUID): Result<Unit>
    suspend fun getProduct(productId: UUID): Result<Product>
} 