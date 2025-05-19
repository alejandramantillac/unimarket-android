package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import com.codeoflegends.unimarket.core.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import java.util.UUID

data class EntrepreneurshipUiState(
    val id: UUID? = null,
    val name: String = "",
    val slogan: String = "",
    val description: String = "",
    val email: String = "",
    val phone: String = "",
    val profileImg: String = "",
    val bannerImg: String = "",
    val color1: String = "",
    val color2: String = "",
    val tags: List<Tag> = emptyList(),
    val entrepreneurship: Entrepreneurship? = null,
    val currentRoute: String = "home"
)