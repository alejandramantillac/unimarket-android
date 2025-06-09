package com.codeoflegends.unimarket.features.cart.data.usecase

import android.util.Log
import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.data.dto.UserProfileDto
import com.codeoflegends.unimarket.features.cart.data.model.Cart
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDetailDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderStatusDto
import com.codeoflegends.unimarket.features.order.data.dto.get.ProductDto
import com.codeoflegends.unimarket.features.order.data.dto.get.ProductVariantDto
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import com.codeoflegends.unimarket.features.product.data.dto.get.VariantImageDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

class CreateOrderFromCartUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(cart: Cart): Result<Order> {
        if (cart.items.isEmpty()) {
            Log.d("CreateOrder", "Cart is empty")
            return Result.failure(IllegalStateException("El carrito está vacío"))
        }

        if (cart.userCreated == null) {
            Log.d("CreateOrder", "User not authenticated")
            return Result.failure(IllegalStateException("Debes iniciar sesión para realizar la compra"))
        }

        Log.d("CreateOrder", "Creating order for user: ${cart.userCreated.id}")
        Log.d("CreateOrder", "Cart items count: ${cart.items.size}")
        Log.d("CreateOrder", "Cart subtotal: ${cart.subtotal}")

        val orderDto = OrderDto(
            id = UUID.randomUUID(),
            status = OrderStatusDto(
                id = UUID.fromString("d47ec182-2b06-4fc4-ba8f-692d53a985c6"), // PENDING status ID
                name = "PENDING"
            ),
            date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            subtotal = cart.subtotal.toInt(),
            discount = 0,
            total = cart.subtotal.toInt(),
            userCreated = UserDto(
                id = cart.userCreated.id.toString(),
                firstName = cart.userCreated.firstName ?: "",
                lastName = cart.userCreated.lastName ?: "",
                email = cart.userCreated.email ?: "",
                profile = cart.userCreated.profile?.let { profile ->
                    UserProfileDto(
                        id = cart.userCreated.id.toString(),
                        profilePicture = profile.profilePicture.orEmpty(),
                        userRating = profile.userRating ?: 0f,
                        partnerRating = profile.partnerRating ?: 0f,
                        registrationDate = profile.registrationDate?.format(DateTimeFormatter.ISO_DATE_TIME) 
                            ?: LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                    )
                }
            ),
            payments = emptyList(),
            orderDetails = cart.items.map { cartItem ->
                OrderDetailDto(
                    id = UUID.randomUUID(),
                    amount = cartItem.quantity,
                    unitPrice = cartItem.variant.price.toInt(),
                    productVariant = ProductVariantDto(
                        id = cartItem.variant.id,
                        name = cartItem.variant.name,
                        stock = cartItem.variant.stock,
                        variantImages = cartItem.variant.variantImages.map { 
                            VariantImageDto(
                                id = it.id ?: UUID.randomUUID(),
                                imageUrl = it.imageUrl
                            )
                        },
                        product = ProductDto(
                            id = cartItem.variant.productId,
                            name = cartItem.variant.productName
                        )
                    )
                )
            },
            delivery = emptyList()
        )

        Log.d("CreateOrder", "Order DTO created, attempting to save")
        return orderRepository.createOrder(orderDto)
    }
} 