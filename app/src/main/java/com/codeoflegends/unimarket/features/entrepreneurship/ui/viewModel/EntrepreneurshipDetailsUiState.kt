package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipPartner
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Tag
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.time.LocalDateTime
import java.util.UUID

data class EntrepreneurshipDetailsUiState(
    val slogan: String = "",
    val description: String = "",
    val tags: List<Tag> = emptyList(),
    val reviews: List<CommentData> = emptyList(),
    val page: Int = 0,
    val limit: Int = 5,
    val hasMoreItems: Boolean = false,
    val averageRating: Float = 0f,
    val totalReviews: Int = 0
)

data class EntrepreneurshipBasicUiState(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val slogan: String = "",
    val description: String = "",
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val customization: EntrepreneurshipCustomization = EntrepreneurshipCustomization(
        profileImg = "",
        bannerImg = "",
        color1 = "",
        color2 = ""
    ),
    val email: String = "",
    val phone: String = "",
    val tags: List<Tag> = emptyList(),
)

data class EntrepreneurshipPartnersUiState(
    val partners: List<EntrepreneurshipPartner> = emptyList(),
    val page: Int = 0,
    val limit: Int = 5,
    val hasMoreItems: Boolean = false
)

data class EntrepreneurshipReviewsUiState(
    val reviews: List<CommentData> = emptyList(),
    val page: Int = 0,
    val limit: Int = 5,
    val hasMoreItems: Boolean = false,
    val averageRating: Float = 0f,
    val totalReviews: Int = 0
)
/*
data class EntrepreneurshipUiState(
    val basicData: EntrepreneurshipBasicUiState,
    val products: EntrepreneurshipProductsUiState,
    val partners: EntrepreneurshipPartnersUiState,
    val reviews: EntrepreneurshipReviewsUiState,
)
*/