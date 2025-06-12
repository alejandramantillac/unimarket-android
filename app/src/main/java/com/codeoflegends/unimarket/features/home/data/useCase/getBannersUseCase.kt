package com.codeoflegends.unimarket.features.home.data.useCase

import com.codeoflegends.unimarket.features.home.data.model.Banner
import com.codeoflegends.unimarket.features.home.data.repository.interfaces.BannerRepository
import javax.inject.Inject

class GetBannersUseCase @Inject constructor(
    private val repository: BannerRepository
) {
    suspend operator fun invoke(): List<Banner> {
        return repository.getBanners().getOrThrow()
    }
}