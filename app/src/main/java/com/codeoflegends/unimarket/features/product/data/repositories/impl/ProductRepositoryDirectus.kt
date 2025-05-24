package com.codeoflegends.unimarket.features.product.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.core.dto.DeleteDto
import com.codeoflegends.unimarket.features.product.data.datasource.ProductService
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductDetailDto
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductListDto
import com.codeoflegends.unimarket.features.product.data.mapper.ProductMapper
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID

@Singleton
class ProductRepositoryDirectus @Inject constructor(
    private val productService: ProductService
) : ProductRepository {

    override suspend fun createProduct(product: Product): Result<Unit> = try {
        val productDto = ProductMapper.toNewProductDto(product)
        productService.createProduct(productDto)
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error creating product: ${e.message}")
        Result.failure(e)
    }

    override suspend fun updateProduct(product: Product): Result<Unit> = try {
        val productDto = ProductMapper.toUpdateProductDto(product)
        Log.d("ProductRepositoryDirectus", "Updating product: $productDto")
        productService.updateProduct(product.id.toString(), productDto)
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error updating product: ${e.message}")
        Result.failure(e)
    }

    override suspend fun deleteProduct(productId: UUID): Result<Unit> = try {
        val isoTimestamp = Instant.now().atOffset(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT)

        productService.deleteProduct(productId.toString(), DeleteDto(
            deletedAt = isoTimestamp
        ))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getProduct(productId: UUID): Result<Product> = try {
        val productDto = productService.getProduct(productId.toString(), ProductDetailDto.query().build()).data
        val product = ProductMapper.detailDtoToProduct(productDto)
        Result.success(product)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllProducts(): Result<List<Product>> = try {
        val productsDto = productService.getAllProducts(ProductListDto.query().build()).data
        val products = ProductMapper.listDtoToProductList(productsDto)
        Result.success(products)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error fetching products: ${e.message}")
        Result.failure(e)
    }

    override suspend fun getAllProductsByEntrepreneurship(
        entrepreneurshipId: UUID,
        nameContains: String,
        page: Int,
        limit: Int
    ): Result<List<Product>> = try {
        val productsDto = productService.getAllProducts(
            ProductListDto.queryByEntrepreneurship(
                entrepreneurshipId = entrepreneurshipId.toString(),
                nameContains = nameContains,
                page = page,
                limit = limit
            ).build()
        ).data
        val products = ProductMapper.listDtoToProductList(productsDto)
        Result.success(products)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error fetching products by entrepreneurship: ${e.message}")
        Result.failure(e)
    }
}
