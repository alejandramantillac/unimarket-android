package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.net.Uri
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SubscriptionPlan
import java.util.Date
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
    val id: String? = null,
    val selectedTab: Int = 0,
    val selectedCategory: String = "",
    val entrepreneurshipName: String = "",
    val entrepreneurshipSlogan: String = "",
    val entrepreneurshipDescription: String = "",
    val entrepreneurshipYear: String = "",
    val selectedYear: String = "",
    val entrepreneurshipCustomization: String? = null,
    val entrepreneurshipEmail: String = "",
    val entrepreneurshipPhone: String = "",
    val entrepreneurshipSubscription: String = "",
    val entrepreneurshipStatus: String = "",
    val entrepreneurshipCategory: String = "",
    val entrepreneurshipSocialNetworks: List<SocialNetwork> = emptyList(),
    val entrepreneurshipUserFounder: String = "",
    val categoryOptions: List<String> = emptyList(),
    val yearOptions: List<String> = emptyList(),
    val entrepreneurshipImageUri: Uri? = null,
    val statusOptions: List<String> = emptyList(),
    val subscriptionOptions: List<SubscriptionPlan> = emptyList()

)



