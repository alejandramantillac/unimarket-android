package com.codeoflegends.unimarket.features.order.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.features.order.data.mock.MockOrderDatabase
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductListDto
import com.codeoflegends.unimarket.features.product.data.mapper.ProductMapper
import com.codeoflegends.unimarket.features.product.data.mock.MockProductDatabase
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(): OrderRepository {

    override suspend fun getOrder(orderId: UUID): Result<Order> = try {
        val order = MockOrderDatabase.getOrder(orderId)
        if (order != null) Result.success(order)
        else Result.failure(Exception("Orden no encontrada"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllOrders(): Result<List<Order>> = try {
        val orders = MockOrderDatabase.getAllOrders()
        Result.success(orders)
    } catch (e: Exception) {
        Result.failure(e)
    }


}