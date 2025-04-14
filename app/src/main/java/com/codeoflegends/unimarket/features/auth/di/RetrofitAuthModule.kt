package com.codeoflegends.unimarket.features.auth.di

import com.codeoflegends.unimarket.config.AuthInterceptor
import com.codeoflegends.unimarket.config.TokenAuthenticator
import com.codeoflegends.unimarket.features.auth.data.interceptor.AuthInterceptorImpl
import com.codeoflegends.unimarket.features.auth.data.interceptor.TokenAuthenticatorImpl
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import com.codeoflegends.unimarket.features.auth.data.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitAuthModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenRepository: TokenRepository): AuthInterceptor {
        return AuthInterceptorImpl(tokenRepository)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenRepository: TokenRepository,
        authService: AuthService
    ): TokenAuthenticator {
        return TokenAuthenticatorImpl(tokenRepository, authService)
    }
}