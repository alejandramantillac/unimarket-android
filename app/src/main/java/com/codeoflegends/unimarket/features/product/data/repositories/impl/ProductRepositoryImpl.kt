package com.codeoflegends.unimarket.features.product.data.repositories.impl

import com.codeoflegends.unimarket.features.product.data.mock.MockProductDatabase
import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import com.codeoflegends.unimarket.features.product.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor() : IProductRepository {

    override suspend fun createProduct(product: Product): Result<Unit> = try {
        MockProductDatabase.addProduct(product)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateProduct(product: Product): Result<Unit> = try {
        MockProductDatabase.updateProduct(product)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteProduct(productId: String): Result<Unit> = try {
        MockProductDatabase.deleteProduct(productId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getProduct(productId: String): Result<Product> = try {
        val product = MockProductDatabase.getProduct(productId)
        if (product != null) Result.success(product)
        else Result.failure(Exception("Producto no encontrado"))
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun getAllProducts(): Result<List<Product>> = try {
        val products = MockProductDatabase.getAllProducts()
        Result.success(products)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getCategories(): Result<List<String>> = try {
        // Categorías simuladas
        Result.success(listOf("Electrónica", "Ropa", "Alimentos", "Libros", "Deportes", "Hogar"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getBusinesses(): Result<List<String>> = try {
        // Negocios simulados
        Result.success(listOf("Tienda de Ropa", "Electrónica XYZ", "Supermercado ABC", "Librería Online"))
    } catch (e: Exception) {
        Result.failure(e)
    }
} 