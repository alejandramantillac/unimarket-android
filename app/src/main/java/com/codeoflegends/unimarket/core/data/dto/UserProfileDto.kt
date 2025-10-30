package com.codeoflegends.unimarket.core.data.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class UserProfileDto(
    val id: String,
    @SerializedName("profile_picture")
    val profilePicture: String? = null,
    @SerializedName("user_rating")
    val userRating: Float = 0f,
    @SerializedName("partner_rating")
    val partnerRating: Float = 0f,
    @SerializedName("registration_date")
    val registrationDate: String? = null
)