package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import java.util.UUID

data class CollaboratorDto(
    val id: UUID,
    val name: String,
    val role: String,
    val email: String,
    val entrepreneurshipId: UUID
) 