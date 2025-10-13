package com.codeoflegends.unimarket.features.order.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderListDto
import com.codeoflegends.unimarket.features.order.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.OrderDetail
import com.codeoflegends.unimarket.features.order.data.model.OrderStatus
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.model.VariantImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object OrderListMapper {
    fun fromDto(dto: OrderListDto): Order {
        return Order(
            id = dto.id,
            status = OrderStatus(
                id = dto.status.id,
                name = dto.status.name
            ),
            date = dto.date.toString(),
            subtotal = dto.subtotal,
            discount = dto.discount,
            total = dto.total,
            userCreated = User(
                id = UUID.fromString(dto.userCreated.id),
                firstName = dto.userCreated.firstName,
                lastName = dto.userCreated.lastName,
                email = dto.userCreated.email,
                profile = dto.userCreated.profile?.let { p ->
                    UserProfile(
                        profilePicture = p.profilePicture,
                        userRating = p.userRating,
                        partnerRating = p.partnerRating,
                        registrationDate = try {
                            LocalDateTime.parse(p.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                        } catch (e: Exception) {
                            LocalDateTime.now()
                        }
                    )
                } ?: UserProfile()
            ),
            payments = emptyList(),
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
            delivery = emptyList(),
            entrepreneurship = Entrepreneurship(
                dto.entrepreneurship.id,
                dto.entrepreneurship.name,
                dto.entrepreneurship.slogan,
                dto.entrepreneurship.email,
                dto.entrepreneurship.phone,
            )
        )
    }
}