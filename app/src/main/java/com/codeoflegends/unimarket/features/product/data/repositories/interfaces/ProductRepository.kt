package com.codeoflegends.unimarket.features.product.data.repositories.interfaces

import com.codeoflegends.unimarket.features.product.data.model.Product
import java.util.UUID

interface ProductRepository {
    suspend fun createProduct(product: Product): Result<Unit>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(productId: UUID): Result<Unit>
    suspend fun getProduct(productId: UUID): Result<Product>
    suspend fun getAllProducts(): Result<List<Product>>
    suspend fun getAllProductsByEntrepreneurship(entrepreneurshipId: UUID, nameContains: String, page: Int, limit: Int): Result<List<Product>>
}