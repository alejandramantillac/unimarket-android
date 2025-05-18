package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.net.Uri
import java.util.Date

data class EntrepreneurshipUiState(
    val id: String? = null,
    val selectedTab: Int = 0,
    val selectedCategory: String? = null,
    val entrepreneurshipName: String = "",
    val entrepreneurshipSlogan: String = "",
    val entrepreneurshipDescription: String = "",
    val entrepreneurshipDate: Date = Date(),
    val entrepreneurshipCustomization: String? = null,
    val entrepreneurshipEmail: String = "",
    val entrepreneurshipPhone: String = "",
    val entrepreneurshipSubscription: String = "",
    val entrepreneurshipStatus: String = "",
    val entrepreneurshipCategory: String = "",
    val entrepreneurshipSocialNetworks: String = "",
    val entrepreneurshipUserFounder: String = "",
    val categoryOptions: List<String> = emptyList(),
    val entrepreneurshipImageUri: Uri? = null,
)


