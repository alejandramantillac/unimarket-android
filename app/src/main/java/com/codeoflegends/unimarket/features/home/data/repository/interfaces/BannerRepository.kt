package com.codeoflegends.unimarket.features.home.data.repository.interfaces

import com.codeoflegends.unimarket.features.home.data.model.Banner

interface BannerRepository {
    suspend fun getBanners(): Result<List<Banner>>
}