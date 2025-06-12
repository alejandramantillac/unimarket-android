package com.codeoflegends.unimarket.core.data.dto

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class UserProfileDto(
    val id: String,
    val profilePicture: String = "",
    val userRating: Float = 0f,
    val partnerRating: Float = 0f,
    val registrationDate: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
)