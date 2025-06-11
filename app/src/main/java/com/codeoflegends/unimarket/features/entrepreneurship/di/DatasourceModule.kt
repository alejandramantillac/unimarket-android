package com.codeoflegends.unimarket.features.entrepreneurship.di

import com.codeoflegends.unimarket.core.annotation.MainRetrofit
import com.codeoflegends.unimarket.features.entrepreneurship.service.CollaboratorApi
import com.codeoflegends.unimarket.features.entrepreneurship.service.EntrepreneurshipCategoryService
import com.codeoflegends.unimarket.features.entrepreneurship.service.EntrepreneurshipReviewService
import com.codeoflegends.unimarket.features.entrepreneurship.service.EntrepreneurshipService
import com.codeoflegends.unimarket.features.entrepreneurship.service.UserGetDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatasourceModule {

    @Provides
    @Singleton
    fun provideEntrepreneurshipService(@MainRetrofit retrofit: Retrofit): EntrepreneurshipService {
        return retrofit.create(EntrepreneurshipService::class.java)
    }

    @Provides
    @Singleton
    fun provideEntrepreneurshipReviewService(@MainRetrofit retrofit: Retrofit): EntrepreneurshipReviewService {
        return retrofit.create(EntrepreneurshipReviewService::class.java)
    }

    @Provides
    @Singleton
    fun provideEntrepreneurshipCategoryService(@MainRetrofit retrofit: Retrofit): EntrepreneurshipCategoryService {
        return retrofit.create(EntrepreneurshipCategoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetUserDataService(@MainRetrofit retrofit: Retrofit): UserGetDataService {
        return retrofit.create(UserGetDataService::class.java)
    }

    @Provides
    @Singleton
    fun provideCollaboratorApi(@MainRetrofit retrofit: Retrofit): CollaboratorApi {
        return retrofit.create(CollaboratorApi::class.java)
    }


}
