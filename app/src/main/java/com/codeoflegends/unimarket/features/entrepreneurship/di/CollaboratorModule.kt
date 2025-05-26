package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.features.entrepreneurship.service.CollaboratorApi
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.CollaboratorRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl.CollaboratorRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CollaboratorModule {

    @Provides
    @Singleton
    fun provideCollaboratorApi(retrofit: Retrofit): CollaboratorApi {
        return retrofit.create(CollaboratorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCollaboratorRepository(api: CollaboratorApi): CollaboratorRepository {
        return CollaboratorRepositoryImpl(api)
    }
} 