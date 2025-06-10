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
) : OrderRepository {
    override suspend fun getAllOrders(entrepreneurship: UUID): Result<List<Order>> {
        return try {
            val response = orderService.getAllOrders(OrderListDto.query(entrepreneurship.toString()).build())
            Result.success(response.data.map { OrderListMapper.fromDto(it) })
        } catch (e: Exception) {
            Log.e("OrderRepository", "Error getting all orders", e)
            Result.failure(e)
        }
    }

    override suspend fun getOrder(orderId: UUID): Result<Order> {
        return try {
            val response = orderService.getOrder(orderId.toString())
            Result.success(OrderMapper.orderDtoToOrder(response.data))
        } catch (e: Exception) {
            Log.e("OrderRepository", "Error getting order", e)
            Result.failure(e)
        }
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

    override suspend fun updateOrderStatus(orderId: UUID, newStatus: String): Result<Order> {
        TODO("Not yet implemented")
    }
}

