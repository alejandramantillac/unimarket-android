package com.codeoflegends.unimarket.features.product.data.datasource

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.product.data.dto.SimpleProduct
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ProductService {
    @GET("/items/Product")
    suspend fun getAllProducts(@QueryMap query: Map<String, String>): DirectusDto<List<SimpleProduct>>
}