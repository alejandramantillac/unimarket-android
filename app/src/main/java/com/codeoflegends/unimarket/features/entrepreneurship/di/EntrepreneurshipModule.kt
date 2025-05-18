package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl.EntrepreneurshipRepositoryImpl
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
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
        entrepreneurshipRepositoryImpl: EntrepreneurshipRepositoryImpl
    ): IEntrepreneurshipRepository
    
    companion object {
        @Provides
        @Singleton
        fun provideCreateEntrepreneurshipUseCase(repository: IEntrepreneurshipRepository): CreateEntrepreneurshipUseCase =
            CreateEntrepreneurshipUseCase(repository)

        @Provides
        @Singleton
        fun provideUpdateEntrepreneurshipUseCase(repository: IEntrepreneurshipRepository): UpdateEntrepreneurshipUseCase =
            UpdateEntrepreneurshipUseCase(repository)

        @Provides
        @Singleton
        fun provideDeleteEntrepreneurshipUseCase(repository: IEntrepreneurshipRepository): DeleteEntrepreneurshipUseCase =
            DeleteEntrepreneurshipUseCase(repository)

        @Provides
        @Singleton
        fun provideGetEntrepreneurshipUseCase(repository: IEntrepreneurshipRepository): GetEntrepreneurshipUseCase =
            GetEntrepreneurshipUseCase(repository)
            
        @Provides
        @Singleton
        fun provideGetAllEntrepreneurshipsUseCase(repository: IEntrepreneurshipRepository): GetAllEntrepreneurshipsUseCase =
            GetAllEntrepreneurshipsUseCase(repository)
    }
} 