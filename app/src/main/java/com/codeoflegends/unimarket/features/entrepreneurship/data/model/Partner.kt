package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import java.time.LocalDateTime
import java.util.UUID

data class Partner(
    val id: UUID,
    val name: String,
    val role: String,
    val email: String,
    val entrepreneurshipId: UUID,
    val status: String,
    val dateCreated: LocalDateTime,
    val dateUpdated: LocalDateTime?
) 