package com.codeoflegends.unimarket.features.order.data.repositories.interfaces

import com.codeoflegends.unimarket.features.order.data.model.Order
import java.util.UUID

interface OrderRepository {
    suspend fun getOrder(orderId: UUID): Result<Order>

    suspend fun getAllOrders(entrepreneurship: UUID): Result<List<Order>>

    suspend fun getAllOrdersByBuyer(buyerId: UUID): Result<List<Order>>

    suspend fun updateOrder(order: Order): Result<Unit>
}