package com.codeoflegends.unimarket.features.product.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

data class ProductReviewDto(
    val id: String,
    val user_profile: String,
    val product: String,
    val rating: Int,
    val comment: String,
    val creation_date: String?
) {
    companion object {
        fun query(productId: UUID, page: Int, limit: Int): DirectusQuery {
            return DirectusQuery()
                .filter("product", "eq", productId.toString())
                .paginate(limit = limit, page = page)
        }
    }
}

data class UserCreatedDto(
    val id: String,
    val profile: ProfileDto?
)

data class ProfileDto(
    val id: String,
    val name: String,
    val lastname: String
) 