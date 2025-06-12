package com.codeoflegends.unimarket.features.product.data.repositories.interfaces

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.product.data.model.NewProduct
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.Review
import java.util.UUID

interface ProductRepository {
    suspend fun createProduct(product: NewProduct): Result<Unit>
    suspend fun updateProduct(product: NewProduct): Result<Unit>
    suspend fun deleteProduct(productId: UUID): Result<Unit>
    suspend fun getProduct(productId: UUID): Result<Product>
    suspend fun getAllProducts(): Result<List<Product>>
    suspend fun getAllProductsByQuery(
        entrepreneurshipId: UUID,
        nameContains: String,
        filters: List<DirectusQuery.Filter>,
        limit: Int,
        page: Int
    ): Result<List<Product>>
    suspend fun createReview(productId: UUID, rating: Int, comment: String): Result<Unit>
    suspend fun getProductReviews(productId: UUID, page: Int, limit: Int): Result<List<Review>>
    suspend fun getAllProductsByQuery(
        nameContains: String,
        filters: List<DirectusQuery.Filter>,
        limit: Int,
        page: Int
    ): Result<List<Product>>
}