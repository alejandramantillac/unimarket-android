package com.codeoflegends.unimarket.features.home.di

import com.codeoflegends.unimarket.core.annotation.MainRetrofit
import com.codeoflegends.unimarket.features.home.data.datasource.BannerService
import com.codeoflegends.unimarket.features.home.data.repository.impl.BannerRepositoryImpl
import com.codeoflegends.unimarket.features.home.data.repository.interfaces.BannerRepository
import com.codeoflegends.unimarket.features.home.data.useCase.GetBannersUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BannerModule {
    @Binds
    @Singleton
    abstract fun bindBannerRepository(
        impl: BannerRepositoryImpl
    ): BannerRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetBannersUseCase(
            repository: BannerRepository
        ): GetBannersUseCase = GetBannersUseCase(repository)

        @Provides
        @Singleton
        fun provideBannerService(@MainRetrofit retrofit: Retrofit): BannerService {
            return retrofit.create(BannerService::class.java)
        }
    }
}