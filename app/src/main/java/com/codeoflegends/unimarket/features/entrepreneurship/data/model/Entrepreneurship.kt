package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipReviewDto
import java.time.LocalDateTime
import java.util.UUID

data class Entrepreneurship(
    val id: UUID? = null,
    val name: String,
    val slogan: String,
    val description: String,
    val creationDate: LocalDateTime,
    val customization: EntrepreneurshipCustomization,
    val email: String,
    val phone: String,
    val subscription: UUID? = null,
    val category: Int,
    val deletedAt: String? = null,
    val partners: List<UUID> = emptyList(),
    val products: List<UUID> = emptyList(),
    val collaborations: List<UUID> = emptyList(),
    val orders: List<UUID> = emptyList(),
    val socialNetworks: List<SocialNetwork> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val reviews: List<EntrepreneurshipReview> = emptyList()
)

data class EntrepreneurshipCustomization(
    val id: UUID? = null,
    val profileImg: String,
    val bannerImg: String,
    val color1: String,
    val color2: String,
)

data class EntrepreneurshipCreate(
    val name: String,
    val slogan: String,
    val description: String,
    val email: String,
    val phone: String,
    val category: Int,
    val customization: EntrepreneurshipCustomization,
)