package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import com.codeoflegends.unimarket.core.data.model.User
import java.util.UUID

data class Partner(
    val id: UUID,
    val role: String,
    val user: User,
    val entrepreneurshipId: UUID
) 