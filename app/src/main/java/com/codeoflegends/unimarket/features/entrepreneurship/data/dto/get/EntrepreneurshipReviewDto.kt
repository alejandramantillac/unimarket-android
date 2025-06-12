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
        fun query(
            page: Int,
            limit: Int,
            entrepreneurshipId: UUID? = null,
            authorId: UUID? = null
        ): DirectusQuery {
            val directusQuery = DirectusQuery()
                .join("user_created")
                .join("user_created.profile")
                .paginate(
                    limit = limit,
                    page = page,
                )

            if (entrepreneurshipId != null) {
                directusQuery.filter("entrepreneurship", "eq", entrepreneurshipId.toString())
            }

            if (authorId != null) {
                directusQuery.filter("user_created", "eq", authorId.toString())
            }

            return directusQuery
        }
    }
}

