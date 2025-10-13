package com.codeoflegends.unimarket.core.data.dto

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class UserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profile: UserProfileDto? = null
){
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("profile")
        }
    }

    fun toDomain(): User {
        return User(
            id = UUID.fromString(id),
            firstName = firstName.orEmpty(),
            lastName = lastName.orEmpty(),
            email = email.orEmpty(),
            profile = profile?.let {
                UserProfile(
                    profilePicture = it.profilePicture.orEmpty(),
                    userRating = it.userRating,
                    partnerRating = it.partnerRating,
                    registrationDate = try {
                        LocalDateTime.parse(it.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                    } catch (e: Exception) {
                        LocalDateTime.now()
                    }
                )
            } ?: UserProfile()
        )
    }
}
