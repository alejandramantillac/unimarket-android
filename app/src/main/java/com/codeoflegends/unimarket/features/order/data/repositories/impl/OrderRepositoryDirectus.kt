package com.codeoflegends.unimarket.features.order.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.features.order.data.datasource.OrderService
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderListDto
import com.codeoflegends.unimarket.features.order.data.mapper.OrderListMapper
import com.codeoflegends.unimarket.features.order.data.mapper.OrderMapper
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryDirectus @Inject constructor(
    private val orderService: OrderService
) : OrderRepository {
    override suspend fun getAllOrders(entrepreneurship: UUID): Result<List<Order>> {
        return try {
            val response = orderService.getAllOrders(OrderListDto.query(entrepreneurship.toString()).build())
            Result.success(response.data.map { OrderListMapper.fromDto(it) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOrder(orderId: UUID): Result<Order> {
        return try {
            val response = orderService.getOrder(orderId.toString())
            Result.success(OrderMapper.orderDtoToOrder(response.data))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createOrder(orderDto: OrderDto): Result<Order> {
        return try {
            val response = orderService.createOrder(orderDto)
            Result.success(OrderMapper.orderDtoToOrder(response.data))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateOrderStatus(orderId: UUID, newStatus: String): Result<Order> {
        TODO("Not yet implemented")
    }
}

