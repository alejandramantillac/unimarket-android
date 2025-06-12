package com.codeoflegends.unimarket.features.order.di

import com.codeoflegends.unimarket.features.order.data.repositories.impl.OrderRepositoryDirectus
import com.codeoflegends.unimarket.features.order.data.repositories.impl.OrderStatusRepositoryDirectus
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderStatusRepository
import com.codeoflegends.unimarket.features.order.data.usecase.GetAllOrdersUseCase
import com.codeoflegends.unimarket.features.order.data.usecase.GetOrderUseCase
import com.codeoflegends.unimarket.features.order.data.usecase.GetOrderStatusesUseCase
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import com.codeoflegends.unimarket.features.product.data.usecase.GetAllProductsUseCase
import com.codeoflegends.unimarket.features.product.data.usecase.GetProductUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OrderModule {
    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryDirectus
    ): OrderRepository

    @Binds
    @Singleton
    abstract fun bindOrderStatusRepository(
        orderStatusRepositoryImpl: OrderStatusRepositoryDirectus
    ): OrderStatusRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetOrderUseCase(repository: OrderRepository): GetOrderUseCase =
            GetOrderUseCase(repository)

        @Provides
        @Singleton
        fun provideGetAllOrdersUseCase(repository: OrderRepository): GetAllOrdersUseCase =
            GetAllOrdersUseCase(repository)

        @Provides
        @Singleton
        fun provideGetOrderStatusesUseCase(repository: OrderStatusRepository): GetOrderStatusesUseCase =
            GetOrderStatusesUseCase(repository)
    }
}
