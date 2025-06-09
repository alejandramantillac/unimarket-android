package com.codeoflegends.unimarket.features.product.di

import com.codeoflegends.unimarket.features.product.data.repositories.impl.ProductCategoryRepositoryDirectus
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import com.codeoflegends.unimarket.features.product.data.repositories.impl.ProductRepositoryDirectus
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductCategoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.codeoflegends.unimarket.features.product.data.usecase.CreateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.UpdateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsByQueryUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.RateProductUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryDirectus
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindProductCategoryRepository(
        productCategoryRepositoryImpl: ProductCategoryRepositoryDirectus
    ): ProductCategoryRepository

    companion object {
        @Provides
        @Singleton
        fun provideCreateProductUseCase(repository: ProductRepository): CreateProductUseCase =
            CreateProductUseCase(repository)

        @Provides
        @Singleton
        fun provideUpdateProductUseCase(repository: ProductRepository): UpdateProductUseCase =
            UpdateProductUseCase(repository)

        @Provides
        @Singleton
        fun provideDeleteProductUseCase(repository: ProductRepository): DeleteProductUseCase =
            DeleteProductUseCase(repository)

        @Provides
        @Singleton
        fun provideGetProductUseCase(repository: ProductRepository): GetProductUseCase =
            GetProductUseCase(repository)
            
        @Provides
        @Singleton
        fun provideGetAllProductsUseCase(repository: ProductRepository): GetAllProductsUseCase =
            GetAllProductsUseCase(repository)

        @Provides
        @Singleton
        fun provideGetAllProductsByQueryUseCase(repository: ProductRepository): GetAllProductsByQueryUseCase =
            GetAllProductsByQueryUseCase(repository)

        @Provides
        @Singleton
        fun provideRateProductUseCase(repository: ProductRepository): RateProductUseCase =
            RateProductUseCase(repository)
    }
} 