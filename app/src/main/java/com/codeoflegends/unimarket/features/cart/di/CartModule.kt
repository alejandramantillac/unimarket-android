package com.codeoflegends.unimarket.features.cart.di

import com.codeoflegends.unimarket.features.cart.data.repositories.impl.CartRepositoryImpl
import com.codeoflegends.unimarket.features.cart.data.repositories.interfaces.CartRepository
import com.codeoflegends.unimarket.features.cart.data.usecase.*
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import com.codeoflegends.unimarket.features.order.data.usecase.GetOrderStatusesUseCase
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CartModule {
    
    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetCartUseCase(repository: CartRepository): GetCartUseCase =
            GetCartUseCase(repository)

        @Provides
        @Singleton
        fun provideAddToCartUseCase(repository: CartRepository): AddToCartUseCase =
            AddToCartUseCase(repository)

        @Provides
        @Singleton
        fun provideRemoveFromCartUseCase(repository: CartRepository): RemoveFromCartUseCase =
            RemoveFromCartUseCase(repository)

        @Provides
        @Singleton
        fun provideUpdateCartItemQuantityUseCase(repository: CartRepository): UpdateCartItemQuantityUseCase =
            UpdateCartItemQuantityUseCase(repository)

        @Provides
        @Singleton
        fun provideClearCartUseCase(repository: CartRepository): ClearCartUseCase =
            ClearCartUseCase(repository)

        @Provides
        @Singleton
        fun provideObserveCartUseCase(repository: CartRepository): ObserveCartUseCase =
            ObserveCartUseCase(repository)

        @Provides
        @Singleton
        fun provideCreateOrderFromCartUseCase(
            cartRepository: CartRepository,
            orderRepository: OrderRepository,
            productRepository: ProductRepository,
            getOrderStatusesUseCase: GetOrderStatusesUseCase
        ): CreateOrderFromCartUseCase =
            CreateOrderFromCartUseCase(
                cartRepository,
                orderRepository,
                productRepository,
                getOrderStatusesUseCase
            )
    }
} 