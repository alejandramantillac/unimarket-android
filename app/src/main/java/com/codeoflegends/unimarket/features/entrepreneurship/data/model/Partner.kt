package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import com.codeoflegends.unimarket.core.data.model.User
import java.util.UUID

data class BasicPartner(
    val id: UUID = UUID.randomUUID(),
    val entrepreneurshipId: UUID,
    val userId: UUID,
    val role: String
)

data class DetailedPartner(
    val id: UUID = UUID.randomUUID(),
    val entrepreneurshipId: UUID,
    val user: User,
    val role: String
)