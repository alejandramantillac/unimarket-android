package com.codeoflegends.unimarket.features.auth.di

import com.codeoflegends.unimarket.features.auth.data.repositories.impl.AuthRepositoryImpl
import com.codeoflegends.unimarket.features.auth.data.repositories.impl.RoleRepositoryImpl
import com.codeoflegends.unimarket.features.auth.data.repositories.impl.TokenRepositoryImpl
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.RoleRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.impl.VerificationRepositoryImpl
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.VerificationRepository
import com.codeoflegends.unimarket.core.factory.ApiServiceFactory
import com.codeoflegends.unimarket.features.auth.data.service.VerificationService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindRoleRepository(
        roleRepositoryImpl: RoleRepositoryImpl
    ): RoleRepository

    @Binds
    @Singleton
    abstract fun bindVerificationRepository(
        impl: VerificationRepositoryImpl
    ): VerificationRepository
}

@Module
@InstallIn(SingletonComponent::class)
object VerificationServiceModule {
    @Provides
    @Singleton
    fun provideVerificationService(
        apiServiceFactory: ApiServiceFactory
    ): VerificationService {
        return apiServiceFactory.create()
    }
}