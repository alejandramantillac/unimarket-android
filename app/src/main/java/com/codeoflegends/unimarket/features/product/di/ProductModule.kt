package com.codeoflegends.unimarket.features.product.di

import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.codeoflegends.unimarket.features.product.data.usecase.CreateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.UpdateProductUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.DeleteProductUseCase
import com.codeoflegends.unimarket.features.product.data.repositories.impl.ProductRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {
    @Provides
    @Singleton
    fun provideProductRepository(): IProductRepository = ProductRepositoryImpl()

    @Provides
    fun provideCreateProductUseCase(repository: IProductRepository): CreateProductUseCase =
        CreateProductUseCase(repository)

    @Provides
    fun provideUpdateProductUseCase(repository: IProductRepository): UpdateProductUseCase =
        UpdateProductUseCase(repository)

    @Provides
    fun provideDeleteProductUseCase(repository: IProductRepository): DeleteProductUseCase =
        DeleteProductUseCase(repository)
} 