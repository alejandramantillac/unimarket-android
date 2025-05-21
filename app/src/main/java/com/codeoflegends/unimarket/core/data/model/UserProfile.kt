package com.codeoflegends.unimarket.core.data.model

import java.time.LocalDateTime

data class UserProfile(
    val profilePicture: String,
    val userRating: Float,
    val partnerRating: Float,
    val registrationDate: LocalDateTime
)