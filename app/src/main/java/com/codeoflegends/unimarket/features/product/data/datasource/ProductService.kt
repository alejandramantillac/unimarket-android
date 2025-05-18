package com.codeoflegends.unimarket.features.product.data.datasource

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.product.data.dto.create.NewProductDto
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductDetailDto
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductListDto
import com.codeoflegends.unimarket.features.product.data.dto.update.UpdateProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
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

    @POST("/items/Product")
    suspend fun createProduct(
        @Body product: NewProductDto
    )

    @PATCH("/items/Product/{productId}")
    suspend fun updateProduct(
        @Path("productId") productId: String,
        @Body product: UpdateProductDto
    )
}