package com.codeoflegends.unimarket.features.product.data.repositories.interfaces

import com.codeoflegends.unimarket.features.product.data.model.ProductCategory

interface ProductCategoryRepository {
    suspend fun getAllCategories(): Result<List<ProductCategory>>
}