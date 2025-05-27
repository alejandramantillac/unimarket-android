package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.update

import java.util.UUID

data class UpdateEntrepreneurshipCustomizationDto (
    val id: UUID,
    val profileImg: String,
    val bannerImg: String,
    val color1: String,
    val color2: String
)
