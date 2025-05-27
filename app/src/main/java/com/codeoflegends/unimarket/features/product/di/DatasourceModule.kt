package com.codeoflegends.unimarket.features.product.di

import com.codeoflegends.unimarket.core.annotation.MainRetrofit
import com.codeoflegends.unimarket.features.product.data.datasource.ProductCategoryService
import com.codeoflegends.unimarket.features.product.data.datasource.ProductService
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
    fun provideProductService(@MainRetrofit retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductCategoryService(@MainRetrofit retrofit: Retrofit): ProductCategoryService {
        return retrofit.create(ProductCategoryService::class.java)
    }
}
