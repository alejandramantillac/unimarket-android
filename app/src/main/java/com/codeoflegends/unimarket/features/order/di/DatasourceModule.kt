package com.codeoflegends.unimarket.features.order.di

import com.codeoflegends.unimarket.core.annotation.MainRetrofit
import com.codeoflegends.unimarket.features.order.data.datasource.OrderService
import com.codeoflegends.unimarket.features.order.data.datasource.OrderStatusService
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
    fun provideOrderService(@MainRetrofit retrofit: Retrofit): OrderService {
        return retrofit.create(OrderService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderStatusService(@MainRetrofit retrofit: Retrofit): OrderStatusService {
        return retrofit.create(OrderStatusService::class.java)
    }
}