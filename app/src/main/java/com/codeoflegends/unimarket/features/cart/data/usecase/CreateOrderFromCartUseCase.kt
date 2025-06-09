package com.codeoflegends.unimarket.features.cart.data.usecase

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
            return Result.failure(IllegalStateException("Cart is empty"))
        }

        if (cart.userCreated == null) {
            return Result.failure(IllegalStateException("User not authenticated"))
        }

        val orderDto = OrderDto(
            id = UUID.randomUUID(),
            status = OrderStatusDto(
                id = UUID.fromString("d5128a08-9c70-4c45-b9c5-2ce1d8c5681e"), // PENDING status ID
                name = "PENDING"
            ),
            date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            subtotal = cart.subtotal.toInt(),
            discount = 0,
            total = cart.subtotal.toInt(),
            userCreated = UserDto(
                id = cart.userCreated.id.toString(),
                firstName = cart.userCreated.firstName,
                lastName = cart.userCreated.lastName,
                email = cart.userCreated.email,
                profile = UserProfileDto(
                    id = cart.userCreated.id.toString(),
                    profilePicture = cart.userCreated.profile.profilePicture,
                    userRating = cart.userCreated.profile.userRating,
                    partnerRating = cart.userCreated.profile.partnerRating,
                    registrationDate = cart.userCreated.profile.registrationDate.format(DateTimeFormatter.ISO_DATE_TIME),
                    id = cart.userCreated.id.toString()
                )
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

        return orderRepository.createOrder(orderDto)
    }
} 