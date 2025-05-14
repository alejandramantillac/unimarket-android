package com.codeoflegends.unimarket.features.product.data.repositories.impl

import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import com.codeoflegends.unimarket.features.product.model.Product
import com.codeoflegends.unimarket.features.product.data.mock.MockProductDatabase
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor() : IProductRepository {
    override suspend fun createProduct(product: Product): Result<Unit> {
        return try {
            MockProductDatabase.addProduct(product)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(product: Product): Result<Unit> {
        return try {
            MockProductDatabase.updateProduct(product)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(productId: String): Result<Unit> {
        return try {
            MockProductDatabase.deleteProduct(productId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProduct(productId: String): Result<Product> {
        return try {
            val product = MockProductDatabase.getProduct(productId)
                ?: return Result.failure(IllegalArgumentException("Producto no encontrado con ID: $productId"))
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 