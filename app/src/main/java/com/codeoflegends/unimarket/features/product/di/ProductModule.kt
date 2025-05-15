package com.codeoflegends.unimarket.features.product.di

import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import com.codeoflegends.unimarket.features.product.data.repositories.impl.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.codeoflegends.unimarket.features.product.data.usecase.CreateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.UpdateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): IProductRepository
    
    companion object {
        @Provides
        @Singleton
        fun provideCreateProductUseCase(repository: IProductRepository): CreateProductUseCase =
            CreateProductUseCase(repository)

        @Provides
        @Singleton
        fun provideUpdateProductUseCase(repository: IProductRepository): UpdateProductUseCase =
            UpdateProductUseCase(repository)

        @Provides
        @Singleton
        fun provideDeleteProductUseCase(repository: IProductRepository): DeleteProductUseCase =
            DeleteProductUseCase(repository)

        @Provides
        @Singleton
        fun provideGetProductUseCase(repository: IProductRepository): GetProductUseCase =
            GetProductUseCase(repository)
            
        @Provides
        @Singleton
        fun provideGetAllProductsUseCase(repository: IProductRepository): GetAllProductsUseCase =
            GetAllProductsUseCase(repository)
    }
} 