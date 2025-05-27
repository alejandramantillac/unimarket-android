package com.codeoflegends.unimarket.features.product.data.model

import java.util.UUID
import java.time.LocalDateTime

data class Review(
    val id: UUID,
    val productId: UUID,
    val userProfileId: UUID,
    val rating: Int,
    val comment: String,
    val creationDate: LocalDateTime
) 