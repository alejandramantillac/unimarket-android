package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl.EntrepreneurshipReviewRepositoryDirectus
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipReviewRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipRatingUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.DeleteEntrepreneurshipReviewUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetAllEntrepreneurshipReviewsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EntrepreneurshipReviewModule {

    @Binds
    @Singleton
    abstract fun bindEntrepreneurshipReviewRepository(
        entrepreneurshipReviewImpl: EntrepreneurshipReviewRepositoryDirectus
    ): EntrepreneurshipReviewRepository

    companion object {
        /*
        @Provides
        @Singleton
        fun provideCreateEntrepreneurshipReviewUseCase(repository: EntrepreneurshipReviewRepository): CreateEntrepreneurshipReviewUseCase =
            CreateEntrepreneurshipReviewUseCase(repository)
         */
        @Provides
        @Singleton
        fun provideDeleteEntrepreneurshipReviewUseCase(repository: EntrepreneurshipReviewRepository): DeleteEntrepreneurshipReviewUseCase =
            DeleteEntrepreneurshipReviewUseCase(repository)

        @Provides
        @Singleton
        fun provideGetAllEntrepreneurshipReviewsUseCase(repository: EntrepreneurshipReviewRepository): GetAllEntrepreneurshipReviewsUseCase =
            GetAllEntrepreneurshipReviewsUseCase(repository)

        @Provides
        @Singleton
        fun provideGetEntrepreneurshipRatingUseCase(repository: EntrepreneurshipReviewRepository): GetEntrepreneurshipRatingUseCase =
            GetEntrepreneurshipRatingUseCase(repository)
    }
}