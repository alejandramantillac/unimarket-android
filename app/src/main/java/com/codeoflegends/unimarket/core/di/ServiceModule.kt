package com.codeoflegends.unimarket.core.di

import com.codeoflegends.unimarket.core.annotation.MainRetrofit
import com.codeoflegends.unimarket.core.annotation.UploadRetrofit
import com.codeoflegends.unimarket.core.data.service.ImageUploadService
import com.codeoflegends.unimarket.core.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideUserService(@MainRetrofit retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageUploadService(@UploadRetrofit retrofit: Retrofit): ImageUploadService {
        return retrofit.create(ImageUploadService::class.java)
    }
} 