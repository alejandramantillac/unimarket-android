package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.UserDto

data class EntrepreneurshipReviewDto(
    val id: String,
    val userCreated: UserDto,
    val dateCreated: String,
    val rating: Float,
    val comment: String
)