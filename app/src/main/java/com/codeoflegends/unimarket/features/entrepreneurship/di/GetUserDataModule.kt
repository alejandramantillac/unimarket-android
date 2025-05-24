package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl.GetUserDataImpl
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IUserGetDataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetCategoryUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetUserDataUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class getUserDataModule {

    @Binds
    @Singleton
    abstract fun bindGetUserDataRepository(
        getUserDataImpl: GetUserDataImpl
    ): IUserGetDataRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetUserData(repository: IUserGetDataRepository): GetUserDataUseCase =
            GetUserDataUseCase(repository)
    }
}