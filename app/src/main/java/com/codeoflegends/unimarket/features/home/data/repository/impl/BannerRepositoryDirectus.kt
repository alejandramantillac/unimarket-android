package com.codeoflegends.unimarket.features.home.data.repository.impl

import com.codeoflegends.unimarket.features.home.data.datasource.BannerService
import com.codeoflegends.unimarket.features.home.data.model.Banner
import com.codeoflegends.unimarket.features.home.data.repository.interfaces.BannerRepository
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val service: BannerService
) : BannerRepository {
    override suspend fun getBanners(): Result<List<Banner>> = try {
        Result.success(service.getBanners().data)
    } catch (e: Exception) {
        Result.failure(e)
    }
}