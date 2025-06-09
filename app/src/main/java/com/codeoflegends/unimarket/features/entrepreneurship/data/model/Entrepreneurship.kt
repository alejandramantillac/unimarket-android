package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipReviewDto
import com.codeoflegends.unimarket.features.product.data.model.Product
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
    val partners: List<EntrepreneurshipPartner> = emptyList(),
    val products: List<ProductPreview> = emptyList(),
    val collaborations: List<UUID> = emptyList(),
    val orders: List<UUID> = emptyList(),
    val socialNetworks: List<SocialNetwork> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val reviews: List<EntrepreneurshipReview> = emptyList()
)

data class EntrepreneurshipPartner(
    val id: UUID = UUID.randomUUID(),
    val role: String = "",
    val user: User = User(
        id = UUID.randomUUID(),
        firstName = "",
        lastName = "",
        email = "",
        profile = UserProfile(
            profilePicture = "",
            userRating = 0f,
            partnerRating = 0f,
            registrationDate = LocalDateTime.now()
        )
    )
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