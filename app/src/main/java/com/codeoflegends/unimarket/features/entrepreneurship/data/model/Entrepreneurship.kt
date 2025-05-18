package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import java.time.LocalDateTime
import java.util.UUID

data class Entrepreneurship(
    val id: UUID? = null,
    val name: String,
    val slogan: String? = null,
    val description: String? = null,
    val creationDate: LocalDateTime? = null,
    val customization: UUID? = null,
    val email: String? = null,
    val phone: String? = null,
    val subscription: UUID? = null,
    val status: String? = null,
    val category: Int,
    val userFounder: UUID
)

data class EntrepreneurshipCustomization(
    val id: UUID? = null,
    val profileImg: String? = null,
    val bannerImg: String? = null,
    val color1: String? = null,
    val color2: String? = null,
    val entrepreneurship: UUID
)

data class EntrepreneurshipSubscription(
    val id: UUID? = null,
    val subscriptionPlan: UUID? = null,
    val cutoffDate: LocalDateTime? = null,
    val lastPayment: LocalDateTime? = null,
    val entrepreneurship: UUID
)

data class EntrepreneurshipType(
    val id: Int,
    val name: String
)

data class EntrepreneurshipTags(
    val entrepreneurshipId: UUID,
    val tagsId: UUID
)