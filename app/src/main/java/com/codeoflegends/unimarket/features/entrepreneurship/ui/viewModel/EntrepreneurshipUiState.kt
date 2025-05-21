package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.net.Uri
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SubscriptionPlan
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipReview
import java.util.UUID

data class EntrepreneurshipUiState(
    val id: UUID? = null,
    val name: String = "",
    val slogan: String = "",
    val description: String = "",
    val email: String = "",
    val phone: String = "",
    val tags: List<Tag> = emptyList(),
    val customization: EntrepreneurshipCustomization = EntrepreneurshipCustomization(
        profileImg = "",
        bannerImg = "",
        color1 = "",
        color2 = ""
    ),
    val reviews: List<EntrepreneurshipReview> = emptyList(),
    val entrepreneurship: Entrepreneurship? = null,
    // Aqui comienzaa la chorrera de camqpos que idk pa que se agregaron
    val currentRoute: String = "home",
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



