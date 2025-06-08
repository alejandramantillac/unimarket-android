package com.codeoflegends.unimarket.core.data.dto

data class UserProfileDto(
    val id: String,
    val profilePicture: String,
    val userRating: Float,
    val partnerRating: Float,
    val registrationDate: String
)