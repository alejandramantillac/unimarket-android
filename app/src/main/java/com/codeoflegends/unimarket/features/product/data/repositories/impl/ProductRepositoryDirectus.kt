package com.codeoflegends.unimarket.features.product.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.features.product.data.datasource.ProductService
import com.codeoflegends.unimarket.features.product.data.dto.SimpleProduct
import com.codeoflegends.unimarket.features.product.data.mock.MockProductDatabase
import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import com.codeoflegends.unimarket.features.product.data.model.Product
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID

@Singleton
class ProductRepositoryDirectus @Inject constructor(
    private val productService: ProductService
) : IProductRepository {

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

    override suspend fun deleteProduct(productId: UUID): Result<Unit> = try {
        MockProductDatabase.deleteProduct(productId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getProduct(productId: UUID): Result<Product> = try {
        val product = MockProductDatabase.getProduct(productId)
        if (product != null) Result.success(product)
        else Result.failure(Exception("Producto no encontrado"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllProducts(): Result<List<SimpleProduct>> = try {
        val products = productService.getAllProducts(SimpleProduct.query().build()).data
        Result.success(products)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error fetching products: ${e.message}")
        Result.failure(e)
    }
}

private fun SimpleProduct.toProduct() {

}
