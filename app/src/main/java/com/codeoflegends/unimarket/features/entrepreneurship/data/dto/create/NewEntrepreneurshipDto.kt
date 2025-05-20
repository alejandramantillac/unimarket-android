package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import java.util.UUID

data class NewEntrepreneurshipDto(
    val name: String,
    val slogan: String,
    val description: String,
    val email: String,
    val phone: String,
    val category: Int,
    val socialNetworks: List<SocialNetwork>,
    val customization: NewEntrepreneurshipCustomizationDto,
    val userFounder: UUID
)