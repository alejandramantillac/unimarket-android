package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.PartnerDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

fun PartnerDto.toDomain(): Partner {
    return Partner(
        id = id,
        role = role,
        user = User(
            id = UUID.fromString(user.firstOrNull() ?: ""),
            firstName = "",
            lastName = "",
            email = "",
            profile = UserProfile(
                profilePicture = "",
                userRating = 0f,
                partnerRating = 0f,
                registrationDate = LocalDateTime.now()
            )
        ),
        entrepreneurshipId = entrepreneurship
    )
}

fun Partner.toDto(): PartnerDto {
    return PartnerDto(
        id = id,
        role = role,
        user = listOf(user.id.toString()),
        entrepreneurship = entrepreneurshipId
    )
} 