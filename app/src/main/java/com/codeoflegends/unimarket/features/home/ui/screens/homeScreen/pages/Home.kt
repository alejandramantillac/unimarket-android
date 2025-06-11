package com.codeoflegends.unimarket.features.home.ui.screens.homeScreen.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.home.ui.components.ProductCategoriesSection
import com.codeoflegends.unimarket.features.home.ui.components.ProductSearchBar
import com.codeoflegends.unimarket.features.home.ui.components.ProductSection
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.home.data.model.Banner
import com.codeoflegends.unimarket.features.home.ui.components.BannerCarousel
import com.codeoflegends.unimarket.features.home.ui.components.EntrepreneurshipSection
import com.codeoflegends.unimarket.features.home.ui.viewModel.HomeViewModel
import com.codeoflegends.unimarket.features.product.data.model.Product

@Composable
fun Home(manager: NavigationManager, homeViewModel: HomeViewModel) {

}