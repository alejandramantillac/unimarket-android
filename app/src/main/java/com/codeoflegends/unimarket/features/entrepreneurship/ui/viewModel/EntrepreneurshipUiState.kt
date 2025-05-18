package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import java.util.UUID

data class EntrepreneurshipUiState(
    val id: UUID? = null,
    val name: String? = null,
    val slogan: String? = null,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val entrepreneurship: Entrepreneurship? = null,
    val selectedTab: Int = 0,
)