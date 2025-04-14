package com.codeoflegends.unimarket.features.auth.di

import com.codeoflegends.unimarket.features.auth.data.repositories.impl.AuthRepositoryImpl
import com.codeoflegends.unimarket.features.auth.data.repositories.impl.TokenRepositoryImpl
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import dagger.Binds
import dagger.Module
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
}