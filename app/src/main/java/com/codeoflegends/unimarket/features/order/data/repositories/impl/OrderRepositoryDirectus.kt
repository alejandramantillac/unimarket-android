package com.codeoflegends.unimarket.features.order.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.features.order.data.datasource.OrderService
import com.codeoflegends.unimarket.features.order.data.dto.create.CreateOrderDto
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
        Log.d("OrderRepositoryDirectus", "Fetching orders for buyer: $buyerId")
        val ordersDto = orderService.getAllOrders(
            OrderListDto.query().build()
        ).data

        Log.d("OrderRepositoryDirectus", "Total orders from API: ${ordersDto.size}")
        ordersDto.forEach { order ->
            Log.d("OrderRepositoryDirectus", "Order ${order.id} - User: ${order.userCreated.id} - Name: ${order.userCreated.firstName}")
        }

        val filteredOrders = ordersDto.filter { it.userCreated.id == buyerId.toString() }
        Log.d("OrderRepositoryDirectus", "Filtered orders for buyer: ${filteredOrders.size}")
        
        val mappedOrders = filteredOrders.map { OrderListMapper.fromDto(it) }
        Log.d("OrderRepositoryDirectus", "Successfully mapped orders: ${mappedOrders.size}")

        Result.success(mappedOrders)
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

    override suspend fun createOrder(orderDto: CreateOrderDto): Result<Order> {
        return try {
            Log.d("OrderRepository", "Creating order with data:")
            Log.d("OrderRepository", "Status: ${orderDto.status}")
            Log.d("OrderRepository", "Subtotal: ${orderDto.subtotal}")
            Log.d("OrderRepository", "Total: ${orderDto.total}")
            Log.d("OrderRepository", "User: ${orderDto.user_created}")
            Log.d("OrderRepository", "Details count: ${orderDto.order_details.size}")

            // Log each order detail
            orderDto.order_details.forEachIndexed { index, detail ->
                Log.d("OrderRepository", "Detail $index:")
                Log.d("OrderRepository", "  Amount: ${detail.amount}")
                Log.d("OrderRepository", "  Unit Price: ${detail.unit_price}")
                Log.d("OrderRepository", "  Product Variant: ${detail.product_variant}")
            }

            try {
                val response = orderService.createOrder(orderDto)
                Log.d("OrderRepository", "Order created successfully with ID: ${response.data.id}")
                Result.success(OrderMapper.orderDtoToOrder(response.data))
            } catch (e: retrofit2.HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("OrderRepository", "HTTP Error ${e.code()}: $errorBody")
                Log.e("OrderRepository", "Full error response: ${e.response()}")
                Result.failure(Exception("Error al crear la orden: ${e.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e("OrderRepository", "Error creating order: ${e.message}")
            Log.e("OrderRepository", "Stack trace:", e)
            Result.failure(e)
        }
    }
}

