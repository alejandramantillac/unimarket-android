package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import java.util.UUID

data class EntrepreneurshipBasicUiState(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val customization: EntrepreneurshipCustomization = EntrepreneurshipCustomization(
        profileImg = "",
        bannerImg = "",
        color1 = "",
        color2 = ""
    ),
    val currentRoute: String = "home",
)