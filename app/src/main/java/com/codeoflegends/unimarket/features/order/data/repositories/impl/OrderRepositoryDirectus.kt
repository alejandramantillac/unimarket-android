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
    override suspend fun getAllOrders(entrepreneurship: UUID): Result<List<Order>> = try {
        val ordersDto = orderService.getAllOrders(
            OrderListDto.query(entrepreneurship.toString()).build()
        ).data

        val orders = ordersDto.map { OrderListMapper.fromDto(it) }
        Result.success(orders)
    } catch (e: Exception) {
        Log.e("OrderRepositoryDirectus", "Error fetching orders: ${e.message}")
        Result.failure(e)
    }

    override suspend fun getOrder(orderId: UUID): Result<Order> = try {
        val orderDto = orderService.getOrder(
            orderId.toString(),
            OrderDto.query().build()
        ).data

        val order = OrderMapper.orderDtoToOrder(orderDto)
        Result.success(order)
    } catch (e: Exception) {
        Log.e("OrderRepositoryDirectus", "Error fetching order: ${e.message}")
        Result.failure(e)
    }

    override suspend fun createOrder(orderDto: OrderDto): Result<Order> = try {
        val createdOrderDto = orderService.createOrder(orderDto).data
        val order = OrderMapper.orderDtoToOrder(createdOrderDto)
        Result.success(order)
    } catch (e: Exception) {
        Log.e("OrderRepositoryDirectus", "Error creating order: ${e.message}")
        Result.failure(e)
    }

    override suspend fun updateOrderStatus(orderId: UUID, newStatus: String): Result<Order> {
        TODO("Not yet implemented")
    }
}

