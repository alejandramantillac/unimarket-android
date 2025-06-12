package com.codeoflegends.unimarket.features.order.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.order.data.dto.create.CreatedOrderDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDto
import com.codeoflegends.unimarket.features.order.data.dto.update.UpdateOrderDto
import com.codeoflegends.unimarket.features.order.data.model.Delivery
import com.codeoflegends.unimarket.features.order.data.model.Entrepreneurship
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
                profile = UserProfile(
                    profilePicture = dto.userCreated.profile.profilePicture,
                    userRating = dto.userCreated.profile.userRating,
                    partnerRating = dto.userCreated.profile.partnerRating,
                    registrationDate = LocalDateTime.parse(dto.userCreated.profile.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                )
            ),
            payments = dto.payments.map { PaymentsMapper.mapFromDto(it) },
            orderDetails = dto.orderDetails.map { OrderDetailsMapper.mapFromDto(it) },
            delivery = dto.delivery.map { DeliveryMapper.mapFromDto(it) },
            entrepreneurship = Entrepreneurship(
                dto.entrepreneurship.id,
                dto.entrepreneurship.name,
                dto.entrepreneurship.slogan,
                dto.entrepreneurship.email,
                dto.entrepreneurship.phone,
            )
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
            delivery = emptyList(),
            entrepreneurship = Entrepreneurship(
                id = dto.entrepreneurship.toString(),
                name = "",
                slogan = "",
                email = "",
                phone = ""
            )
        )
    }

    fun toUpdateOrderDto(order: Order): UpdateOrderDto{
        return UpdateOrderDto(
            status = OrderStatus (
                id = order.status.id,
                name = order.status.name
            ),
            payments = order.payments?.map { PaymentsMapper.toUpdatePaymentDto(it) } ?: emptyList(),
            delivery = order.delivery.map { DeliveryMapper.toUpdateDeliveryDto(it) }
        )
    }
}