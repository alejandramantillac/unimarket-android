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
            id = UUID.fromString(user.id),
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            profile = UserProfile(
                profilePicture = user.profile.profilePicture,
                userRating = user.profile.userRating,
                partnerRating = user.profile.partnerRating,
                registrationDate = LocalDateTime.parse(user.profile.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
            )
        ),
        entrepreneurshipId = entrepreneurship
    )
}

fun Partner.toDto(): PartnerDto {
    return PartnerDto(
        id = id,
        role = role,
        user = com.codeoflegends.unimarket.core.data.dto.UserDto(
            id = user.id.toString(),
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            profile = com.codeoflegends.unimarket.core.data.dto.UserProfileDto(
                profilePicture = user.profile.profilePicture,
                userRating = user.profile.userRating,
                partnerRating = user.profile.partnerRating,
                registrationDate = user.profile.registrationDate.format(DateTimeFormatter.ISO_DATE_TIME)
            )
        ),
        entrepreneurship = entrepreneurshipId
    )
} 