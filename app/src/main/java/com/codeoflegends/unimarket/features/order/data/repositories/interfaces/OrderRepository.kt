package com.codeoflegends.unimarket.features.order.data.repositories.interfaces

import com.codeoflegends.unimarket.features.order.data.dto.create.CreateOrderDto
import com.codeoflegends.unimarket.features.order.data.model.Order
import java.util.UUID

interface OrderRepository {
    suspend fun getOrder(orderId: UUID): Result<Order>

    suspend fun getAllOrders(entrepreneurship: UUID): Result<List<Order>>

    suspend fun createOrder(orderDto: CreateOrderDto): Result<Order>
    
    suspend fun updateOrderStatus(orderId: UUID, newStatus: String): Result<Order>
}