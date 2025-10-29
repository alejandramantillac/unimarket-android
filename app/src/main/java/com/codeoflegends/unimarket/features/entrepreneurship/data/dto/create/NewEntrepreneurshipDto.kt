package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class NewEntrepreneurshipDto(
    val name: String,
    val slogan: String,
    val description: String,
    val email: String,
    val phone: String,
    val category: Int,
    @SerializedName("user_founder") val userFounder: UUID,
    val customization: NewEntrepreneurshipCustomizationDto
)