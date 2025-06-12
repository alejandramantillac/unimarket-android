package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.core.annotation.MainRetrofit
import com.codeoflegends.unimarket.features.entrepreneurship.data.remote.PartnerService
import com.codeoflegends.unimarket.features.entrepreneurship.data.repository.PartnerRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repository.PartnerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PartnerModule {
    @Provides
    @Singleton
    fun providePartnerService(@MainRetrofit retrofit: Retrofit): PartnerService {
        return retrofit.create(PartnerService::class.java)
    }

    @Provides
    @Singleton
    fun providePartnerRepository(service: PartnerService): PartnerRepository {
        return PartnerRepositoryImpl(service)
    }
} 