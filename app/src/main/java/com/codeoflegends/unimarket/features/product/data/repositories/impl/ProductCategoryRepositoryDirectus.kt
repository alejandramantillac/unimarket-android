package com.codeoflegends.unimarket.features.product.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.features.product.data.datasource.ProductCategoryService
import com.codeoflegends.unimarket.features.product.data.datasource.ProductService
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductListDto
import com.codeoflegends.unimarket.features.product.data.mapper.ProductCategoryMapper
import com.codeoflegends.unimarket.features.product.data.mapper.ProductMapper
import com.codeoflegends.unimarket.features.product.data.model.ProductCategory
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductCategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductCategoryRepositoryDirectus @Inject constructor(
    private val productCategoryService: ProductCategoryService
) : ProductCategoryRepository {
    override suspend fun getAllCategories(): Result<List<ProductCategory>> = try {
        val categoriesDto = productCategoryService.getAllCategories().data
        val products = categoriesDto.map { ProductCategoryMapper.fromDto(it) }
        Result.success(products)
    } catch (e: Exception) {
        Log.e("ProductCategoryRepositoryDirectus", "Error fetching categories: ${e.message}")
        Result.failure(e)
    }
}
