package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import com.codeoflegends.unimarket.core.data.model.Tag
import java.time.LocalDateTime
import java.util.UUID

data class Entrepreneurship(
    val id: UUID? = null,
    val name: String,
    val slogan: String,
    val description: String,
    val creationDate: String ,
    val customization: EntrepreneurshipCustomization,
    val email: String,
    val phone: String,
    val subscription: UUID? = null,
    val status: String,
    val category: Int,
    val userFounder: UUID,
    val deletedAt: String? = null,
    val partners: List<UUID> = emptyList(),
    val products: List<UUID> = emptyList(),
    val collaborations: List<UUID> = emptyList(),
    val orders: List<UUID> = emptyList(),
    val socialNetworks: List<String> = emptyList(),
    val tags: List<Tag> = emptyList()
)

data class EntrepreneurshipCustomization(
    val id: UUID? = null,
    val profileImg: String,
    val bannerImg: String,
    val color1: String,
    val color2: String,
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