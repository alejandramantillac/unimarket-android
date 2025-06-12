package com.codeoflegends.unimarket.features.order.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.time.LocalDateTime
import java.util.UUID

class OrderListDto (
    val id: UUID,
    val status: OrderStatusDto,
    val date: String,
    val subtotal: Int,
    val discount: Int,
    val total: Int,
    val userCreated: UserDto,
    val orderDetails: List<OrderDetailDto> = emptyList(),
    val entrepreneurship: EntrepreneurshipDto,
){
    companion object {
        fun query(entrepreneurship: String? = null): DirectusQuery {

            val query = DirectusQuery()
                .join("user_created")
                .join("status")
                .join("user_created")
                .join("user_created.profile")
                .join("payments")
                .join("payments.payment_method")
                .join("payments.payment_evidences")
                .join("status")
                .join("order_details")
                .join("order_details.product_variant")
                .join("order_details.product_variant.product")
                .join("order_details.product_variant.variant_images")
                .join("delivery")
                .join("delivery.status")
                .join("delivery.partner.user_profile")
                .join("delivery.partner.user_profile.profile")
                .join("entrepreneurship")

            entrepreneurship?.let{
                query.filter("entrepreneurship", "eq", it)
            }
            return query
        }
    }
}