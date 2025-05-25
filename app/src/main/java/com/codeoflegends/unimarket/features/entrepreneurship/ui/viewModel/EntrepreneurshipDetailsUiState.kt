package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Tag

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