package com.codeoflegends.unimarket.features.product.data.dto.create

data class NewReviewDto(
    val product: String,
    val rating: Int,
    val comment: String,
    val user_profile: String
) 