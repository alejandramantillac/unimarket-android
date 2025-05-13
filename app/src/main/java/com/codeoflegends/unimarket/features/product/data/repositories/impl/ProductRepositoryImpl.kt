package com.codeoflegends.unimarket.features.product.data.repositories.impl

import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import com.codeoflegends.unimarket.features.product.model.Product

class ProductRepositoryImpl : IProductRepository {
    override suspend fun createProduct(product: Product): Result<Unit> {
        // Aquí irá la lógica para crear producto en Directus
        return Result.success(Unit)
    }

    override suspend fun updateProduct(product: Product): Result<Unit> {
        // Aquí irá la lógica para actualizar producto en Directus
        return Result.success(Unit)
    }

    override suspend fun deleteProduct(productId: String): Result<Unit> {
        // Aquí irá la lógica para eliminar producto en Directus
        return Result.success(Unit)
    }

    override suspend fun getProduct(productId: String): Result<Product> {
        // Aquí irá la lógica para obtener producto de Directus
        return Result.failure(NotImplementedError())
    }
} 