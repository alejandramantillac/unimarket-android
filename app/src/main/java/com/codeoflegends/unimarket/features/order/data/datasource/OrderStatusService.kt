package com.codeoflegends.unimarket.features.order.data.datasource

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderStatusDto
import retrofit2.http.GET

interface OrderStatusService {
    @GET("/items/OrderStatus")
    suspend fun getAllOrderStatuses(): DirectusDto<List<OrderStatusDto>>
} 