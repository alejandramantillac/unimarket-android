package com.codeoflegends.unimarket.features.auth.di

import com.codeoflegends.unimarket.core.annotation.AuthRetrofit
import com.codeoflegends.unimarket.features.auth.data.repositories.impl.RoleRepositoryImpl
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.RoleRepository
import com.codeoflegends.unimarket.features.auth.data.service.AuthService
import com.codeoflegends.unimarket.features.auth.data.service.JwtDecoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthServiceModule {
    @Provides
    @Singleton
    fun provideJwtDecoder(): JwtDecoder {
        return JwtDecoder()
    }

    @Provides
    @Singleton
    fun provideAuthService(@AuthRetrofit retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}
