package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.CollaboratorDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object CollaboratorMapper {
    fun mapToCollaborator(dto: CollaboratorDto): Collaborator {
        return Collaborator(
            id = dto.id,
            role = dto.role,
            user = User(
                id = UUID.fromString(dto.user[0].id),
                firstName = dto.user[0].firstName,
                lastName = dto.user[0].lastName,
                email = dto.user[0].email,
                profile = UserProfile(
                    profilePicture = dto.user[0].profile.profilePicture,
                    userRating = dto.user[0].profile.userRating,
                    partnerRating = dto.user[0].profile.partnerRating,
                    registrationDate = LocalDateTime.parse(
                        dto.user[0].profile.registrationDate,
                        DateTimeFormatter.ISO_DATE_TIME
                    )
                )
            )

        )
    }
}