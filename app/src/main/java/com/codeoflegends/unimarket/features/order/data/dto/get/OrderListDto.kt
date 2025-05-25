package com.codeoflegends.unimarket.features.order.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.time.LocalDateTime

class OrderListDto (
    val status: OrderStatusDto,
    val date: LocalDateTime,
    val subtotal: Int,
    val discount: Int,
    val total: Int,
    val userCreated: UserDto,
){
    companion object {
        fun query(entrepreneurship: String): DirectusQuery {
            return DirectusQuery()
                .join("user_created")
                .join("status")
                .filter("entrepreneurship", "eq", entrepreneurship)
        }
    }
}