package com.codeoflegends.unimarket.features.product.data.datasource

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.product.data.dto.get.CategoryDto
import retrofit2.http.GET

interface ProductCategoryService {
    @GET("/items/ProductCategory")
    suspend fun getAllCategories(): DirectusDto<List<CategoryDto>>
}