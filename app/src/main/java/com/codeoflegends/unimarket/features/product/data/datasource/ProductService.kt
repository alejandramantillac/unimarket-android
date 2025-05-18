package com.codeoflegends.unimarket.features.product.data.datasource

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.product.data.dto.ProductDetailDto
import com.codeoflegends.unimarket.features.product.data.dto.ProductListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductService {
    @GET("/items/Product")
    suspend fun getAllProducts(
        @QueryMap query: Map<String, String>
    ): DirectusDto<List<ProductListDto>>

    @GET("/items/Product/{productId}")
    suspend fun getProduct(
        @Path("productId") productId: String,
        @QueryMap query: Map<String, String>
    ): DirectusDto<ProductDetailDto>
}