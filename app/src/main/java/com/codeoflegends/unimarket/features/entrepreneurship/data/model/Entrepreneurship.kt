package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import java.time.LocalDateTime
import java.util.UUID

data class Entrepreneurship(
    val id: UUID,
    val name: String,
    val slogan: String,
    val description: String,
    val creationDate: LocalDateTime,
    val customization: UUID?,
    val email: String,
    val phone: String,
    val subscription: UUID,
    val status: String,
    val category: Int,
    val socialNetworks: List<SocialNetwork>,
    val userFounder: UUID,
    val imageUrl: String? = null

)
