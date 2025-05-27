package com.codeoflegends.unimarket.features.entrepreneurship.service

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory
import retrofit2.http.GET

interface EntrepreneurshipCategoryService {
    @GET("/items/EntrepreneurshipType")
    suspend fun getEntrepreneurshipCategory(
    ): DirectusDto<List<EntrepreneurshipCategory>>
}