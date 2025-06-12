package com.codeoflegends.unimarket.features.order.data.repositories.interfaces

import com.codeoflegends.unimarket.features.order.data.model.OrderStatus

interface OrderStatusRepository {
    suspend fun getAllOrderStatuses(): Result<List<OrderStatus>>
} 