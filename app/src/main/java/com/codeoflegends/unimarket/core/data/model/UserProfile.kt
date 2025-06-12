package com.codeoflegends.unimarket.core.data.model

import java.time.LocalDateTime

data class UserProfile(
    val profilePicture: String = "",
    val userRating: Float = 0f,
    val partnerRating: Float = 0f,
    val registrationDate: LocalDateTime = LocalDateTime.now()
)