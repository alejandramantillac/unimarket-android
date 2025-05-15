package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl.EntrepreneurshipImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.CreateEntrepreneushipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.UpdateEntrepreneushipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.DeleteEntrepreneushipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetAllEntrepreneuship
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneushipUseCase


@Module
@InstallIn(SingletonComponent::class)
abstract class EntrepreneurshipModule {

    @Binds
    @Singleton
    abstract fun bindEntrepreneurshipRepository(
        entrepreneurshipImpl: EntrepreneurshipImpl
    ): IEntrepreneurshipRepository

    companion object {
        @Provides
        @Singleton
        fun provideCreateEntrepreneurshipUseCase(repository: IEntrepreneurshipRepository): CreateEntrepreneushipUseCase =
            CreateEntrepreneushipUseCase(repository)

        @Provides
        @Singleton
        fun provideUpdateEntrepreneurshipUseCase(repository: IEntrepreneurshipRepository): UpdateEntrepreneushipUseCase =
            UpdateEntrepreneushipUseCase(repository)

        @Provides
        @Singleton
        fun provideDeleteEntrepreneurshipUseCase(repository: IEntrepreneurshipRepository): DeleteEntrepreneushipUseCase =
            DeleteEntrepreneushipUseCase(repository)

        @Provides
        @Singleton
        fun provideGetAllEntrepreneurshipsUseCase(repository: IEntrepreneurshipRepository): GetAllEntrepreneuship =
            GetAllEntrepreneuship(repository)
    }
}