package com.codeoflegends.unimarket.features.order.data.repositories.interfaces

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.util.UUID

interface OrderRepository {
    suspend fun getOrder(orderId: UUID): Result<Order>

    suspend fun getAllOrders(entrepreneurship: UUID): Result<List<Order>>

}