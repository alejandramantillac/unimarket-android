package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

data class EntrepreneurshipReviewDto(
    val id: UUID,
    val userCreated: UserDto,
    val dateCreated: String,
    val rating: Float,
    val comment: String
) {
    companion object {
        fun query(page: Int, limit: Int): DirectusQuery {
            return DirectusQuery()
                .join("user_created")
                .join("user_created.profile")
                .paginate(
                    limit = limit,
                    page = page,
                )
        }
    }
}