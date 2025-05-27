package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import com.codeoflegends.unimarket.core.data.model.User
import java.time.LocalDateTime
import java.util.UUID

data class EntrepreneurshipReview(
    val id: UUID,
    val userCreated: User,
    val dateCreated: LocalDateTime,
    val rating: Float,
    val comment: String
)