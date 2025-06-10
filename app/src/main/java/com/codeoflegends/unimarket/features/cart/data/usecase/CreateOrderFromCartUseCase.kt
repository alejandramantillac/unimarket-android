package com.codeoflegends.unimarket.features.cart.data.usecase

import android.util.Log
import com.codeoflegends.unimarket.features.cart.data.model.Cart
import com.codeoflegends.unimarket.features.cart.data.repositories.interfaces.CartRepository
import com.codeoflegends.unimarket.features.order.data.dto.create.CreateOrderDetailDto
import com.codeoflegends.unimarket.features.order.data.dto.create.CreateOrderDto
import com.codeoflegends.unimarket.features.order.data.dto.create.StatusDto
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import com.codeoflegends.unimarket.features.order.data.usecase.GetOrderStatusesUseCase
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.roundToInt

class CreateOrderFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val getOrderStatusesUseCase: GetOrderStatusesUseCase
) {
    suspend operator fun invoke(cart: Cart): Result<Order> {
        return try {
            if (cart.items.isEmpty()) {
                throw Exception("Cart is empty")
            }

            if (cart.userCreated == null) {
                throw Exception("User not authenticated")
            }

            // Get PENDING status ID
            val pendingStatusId = getOrderStatusesUseCase()
                .getOrThrow()
                .find { it.name == "pendiente" }
                ?.id ?: throw Exception("pendiente status not found")

            // Get first product to get entrepreneurship ID
            val firstProductId = cart.items.firstOrNull()?.variant?.productId
                ?: throw Exception("Cart is empty")
            
            val product = productRepository.getProduct(firstProductId)
                .getOrThrow()
            
            val entrepreneurshipId = product.entrepreneurship.id
                ?: throw Exception("Product's entrepreneurship has no ID")

            val subtotalDouble = cart.items.sumOf { item ->
                item.variant.price * item.quantity
            }
            
            val subtotalInt = (subtotalDouble * 100).roundToInt()
            
            val orderDto = CreateOrderDto(
                status = pendingStatusId.toString(),
                subtotal = subtotalInt,
                discount = 0,
                total = subtotalInt,
                user_created = cart.userCreated.id,
                entrepreneurship = entrepreneurshipId,
                order_details = cart.items.map { item ->
                    CreateOrderDetailDto(
                        amount = item.quantity,
                        unit_price = (item.variant.price * 100).roundToInt(),
                        product_variant = item.variant.id
                    )
                }
            )

            Log.d("CreateOrder", "Creating order with data:")
            Log.d("CreateOrder", "Status: $pendingStatusId")
            Log.d("CreateOrder", "Subtotal: ${orderDto.subtotal}")
            Log.d("CreateOrder", "Total: ${orderDto.total}")
            Log.d("CreateOrder", "User: ${orderDto.user_created}")
            Log.d("CreateOrder", "Entrepreneurship: $entrepreneurshipId")
            Log.d("CreateOrder", "Details count: ${orderDto.order_details.size}")

            orderRepository.createOrder(orderDto)
        } catch (e: Exception) {
            Log.e("CreateOrder", "Error creating order", e)
            Result.failure(e)
        }
    }
} 