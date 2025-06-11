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
) : OrderRepository{

    override suspend fun getAllOrdersByBuyer(buyerId: UUID): Result<List<Order>> = try {
        val ordersDto = orderService.getAllOrders(
            OrderListDto.query().build()
        ).data

        val filteredOrders = ordersDto.filter { it.userCreated.id == buyerId.toString() }
            .map { OrderListMapper.fromDto(it) }

        Result.success(filteredOrders)
    } catch (e: Exception) {
        Log.e("OrderRepositoryDirectus", "Error fetching orders by buyer: ${e.message}")
        Result.failure(e)
    }

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
        if (order != null) {
            Result.success(order)
        } else {
            Result.failure(Exception("El mapeo de OrderDto a Order devolvió nulo"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateOrder(order: Order): Result<Unit> = try {
        val orderDto = OrderMapper.toUpdateOrderDto(order)
        Log.d("OrderRepositoryDirectus", "Updating order: $orderDto")
        orderService.updateOrder(order.id.toString(), orderDto)
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e("OrderRepositoryDirectus", "Error updating order: ${e.message}")
        Result.failure(e)
    }
}

