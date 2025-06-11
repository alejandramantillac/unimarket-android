package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import com.codeoflegends.unimarket.core.data.model.User
import java.util.UUID

data class Collaborator(
    val id: UUID = UUID.randomUUID(),
    val role: String,
    val user: User
)