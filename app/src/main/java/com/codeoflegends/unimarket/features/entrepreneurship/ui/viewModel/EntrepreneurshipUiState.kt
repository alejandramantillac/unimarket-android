package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.net.Uri
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SubscriptionPlan
import java.util.Date

data class EntrepreneurshipUiState(
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



