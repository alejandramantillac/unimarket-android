package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.update

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork

data class UpdateEntrepreneurshipDto(
    val name: String,
    val slogan: String,
    val description: String,
    val email: String,
    val phone: String,
    val category: Int,
    val socialNetworks: List<SocialNetwork>,
    val customization: UpdateEntrepreneurshipCustomizationDto,
)