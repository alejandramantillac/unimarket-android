package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl.EntrepreneurshipCategoryRepositoryImpl
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipCategoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetCategoryUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class EntrepreneurshipCategoryModule {

    @Binds
    @Singleton
    abstract fun bindEntrepreneurshipRepository(
        entrepreneurshipImpl: EntrepreneurshipCategoryRepositoryImpl
    ): EntrepreneurshipCategoryRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetEntrepreneurshipCategory(repository: EntrepreneurshipCategoryRepository): GetCategoryUseCase =
            GetCategoryUseCase(repository)


    }
}
