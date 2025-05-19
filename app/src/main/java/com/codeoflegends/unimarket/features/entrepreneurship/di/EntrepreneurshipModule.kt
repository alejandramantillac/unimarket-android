package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl.EntrepreneurshipRepositoryDirectus
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.CreateEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.UpdateEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.DeleteEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetAllEntrepreneurshipsUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class EntrepreneurshipModule {

    @Binds
    @Singleton
    abstract fun bindEntrepreneurshipRepository(
        entrepreneurshipRepositoryImpl: EntrepreneurshipRepositoryDirectus
    ): EntrepreneurshipRepository
    
    companion object {
        @Provides
        @Singleton
        fun provideCreateEntrepreneurshipUseCase(repository: EntrepreneurshipRepository): CreateEntrepreneurshipUseCase =
            CreateEntrepreneurshipUseCase(repository)

        @Provides
        @Singleton
        fun provideUpdateEntrepreneurshipUseCase(repository: EntrepreneurshipRepository): UpdateEntrepreneurshipUseCase =
            UpdateEntrepreneurshipUseCase(repository)

        @Provides
        @Singleton
        fun provideDeleteEntrepreneurshipUseCase(repository: EntrepreneurshipRepository): DeleteEntrepreneurshipUseCase =
            DeleteEntrepreneurshipUseCase(repository)

        @Provides
        @Singleton
        fun provideGetEntrepreneurshipUseCase(repository: EntrepreneurshipRepository): GetEntrepreneurshipUseCase =
            GetEntrepreneurshipUseCase(repository)
            
        @Provides
        @Singleton
        fun provideGetAllEntrepreneurshipsUseCase(repository: EntrepreneurshipRepository): GetAllEntrepreneurshipsUseCase =
            GetAllEntrepreneurshipsUseCase(repository)
    }
} 