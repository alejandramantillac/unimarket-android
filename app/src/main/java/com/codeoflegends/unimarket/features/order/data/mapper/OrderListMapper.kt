package com.codeoflegends.unimarket.features.order.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderListDto
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.OrderList
import com.codeoflegends.unimarket.features.order.data.model.OrderStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object OrderListMapper {
    fun fromDto(dto: OrderListDto): Order {
        return Order(
            status = OrderStatus(
                id = dto.status.id,
                name = dto.status.name
            ),
            date = dto.date,
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
            payments = emptyList(),
            orderDetails = emptyList(),
            delivery = emptyList()
        )
    }
}