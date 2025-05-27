package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import java.util.UUID

data class Collaborator(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val role: String,
    val email: String,
    val entrepreneurshipId: UUID
) 