package com.codeoflegends.unimarket.features.home.data.datasource

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.home.data.model.Banner
import retrofit2.http.GET

interface BannerService {
    @GET("/items/Banner")
    suspend fun getBanners(): DirectusDto<List<Banner>>
}