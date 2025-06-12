package com.codeoflegends.unimarket.features.order.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.order.data.dto.create.CreatedOrderDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDto
import com.codeoflegends.unimarket.features.order.data.model.Delivery
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.OrderDetail
import com.codeoflegends.unimarket.features.order.data.model.OrderStatus
import com.codeoflegends.unimarket.features.order.data.model.Payment
import com.codeoflegends.unimarket.features.order.data.model.PaymentEvidence
import com.codeoflegends.unimarket.features.order.data.model.PaymentMethod
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.model.VariantImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object OrderMapper {

    fun orderDtoToOrder(dto: OrderDto ): Order{
        return Order(
            id = dto.id,
            status = OrderStatus (
                id = dto.status.id,
                name = dto.status.name
            ),
            date =  dto.date,
            subtotal = dto.subtotal,
            discount = dto.discount,
            total = dto.total,
            userCreated = User(
                id = UUID.fromString(dto.userCreated.id),
                firstName = dto.userCreated.firstName,
                lastName = dto.userCreated.lastName,
                email = dto.userCreated.email,
                profile = dto.userCreated.profile?.let { profileDto ->
                    UserProfile(
                        profilePicture = profileDto.profilePicture,
                        userRating = profileDto.userRating,
                        partnerRating = profileDto.partnerRating,
                        registrationDate = LocalDateTime.parse(profileDto.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                    )
                } ?: UserProfile("", 0f, 0f, LocalDateTime.now())
            ),
            payments = dto.payments.map { paymentDto ->
                Payment(
                    id = paymentDto.id,
                    paymentMethod = PaymentMethod(
                        id = paymentDto.paymentMethod.id,
                        name = paymentDto.paymentMethod.name
                    ),
                    paymentEvidences = paymentDto.paymentEvidences.map { evidence ->
                        PaymentEvidence(
                            id = evidence.id,
                            url = evidence.url
                        )
                    },
                )
            },
            orderDetails = dto.orderDetails.map { detailDto ->
                OrderDetail(
                    id = detailDto.id,
                    amount = detailDto.amount,
                    unitPrice = detailDto.unitPrice,
                    productVariant = ProductVariant(
                        id = detailDto.productVariant.id,
                        name = detailDto.productVariant.name,
                        stock = detailDto.productVariant.stock,
                        variantImages = detailDto.productVariant.variantImages.map {
                            VariantImage(
                                id = it.id,
                                imageUrl = it.imageUrl
                            )
                        }
                    )
                )
            },
            delivery = dto.delivery.map { deliveryDto ->
                Delivery(
                    id = deliveryDto.id,
                    type = deliveryDto.type,
                    deliveryAddress = deliveryDto.deliveryAddress,
                    status = deliveryDto.status
                )
            }
        )
    }

    fun orderDtoToOrder(dto: CreatedOrderDto ): Order{
        return Order(
            id = dto.id,
            status = OrderStatus (
                id = dto.status,
                name = "temp"
            ),
            date =  dto.date,
            subtotal = dto.subtotal,
            discount = dto.discount,
            total = dto.total,
            userCreated = User(
                id = UUID.fromString(dto.user_created.toString()),
                firstName = "",
                lastName = "",
                email = "",
            ),
            payments = emptyList(),
            orderDetails = emptyList(),
            delivery = emptyList()
        )
    }
}