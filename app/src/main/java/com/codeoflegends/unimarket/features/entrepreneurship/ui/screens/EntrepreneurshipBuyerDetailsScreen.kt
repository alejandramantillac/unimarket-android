package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.ui.components.Comment
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.ui.components.TagSection
import com.codeoflegends.unimarket.core.ui.components.TagType
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipDescription
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipHeader
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipHeaderData
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipMembersPreview
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipProductsPreview
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipRating
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicDetailsActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBuyerDetailsViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipReviewActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipReviewsActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipReviewsViewModel
import java.util.*

@Composable
fun EntrepreneurshipBuyerDetailsScreen(
    viewModel: EntrepreneurshipBuyerDetailsViewModel = hiltViewModel(),
    reviewsViewModel: EntrepreneurshipReviewsViewModel = hiltViewModel(),
    entrepreneurshipId: UUID,
    onProductClick: () -> Unit = {},
) {
    // UI states
    val entrepreneurship by viewModel.entrepreneurshipUiState.collectAsState()
    val partners by viewModel.partnersUiState.collectAsState()
    val products by viewModel.productsUiState.collectAsState()
    val reviews by reviewsViewModel.reviewsUiState.collectAsState()

    // Actions states
    val actionState by viewModel.actionState.collectAsState()
    val reviewState by reviewsViewModel.reviewsActionState.collectAsState()

    LaunchedEffect(entrepreneurshipId) {
        reviewsViewModel.loadReviewDetails(entrepreneurshipId)
        viewModel.loadEntrepreneurshipDetails(entrepreneurshipId)
    }

    when (actionState) {
        is EntrepreneurshipBasicDetailsActionState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is EntrepreneurshipBasicDetailsActionState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error al cargar los detalles del entrepreneurship")
            }
        }
        else -> {
            InfiniteScrollList(
                items = reviews.reviews,
                onLoadMore = { reviewsViewModel.loadMoreReviews(entrepreneurshipId) },
                isLoading = reviewState is EntrepreneurshipReviewsActionState.Loading,
                itemContent = { review ->
                    Comment(comment = review)
                },
                titleContent = {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Valoraciones y reseñas",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Las calificaciones y reseñas vienen de clientes que han comprado en este emprendimiento.",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                },
                emptyContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay datos disponibles")
                    }
                },
                headerContent = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        EntrepreneurshipHeader(
                            headerData = EntrepreneurshipHeaderData(
                                bannerUrl = entrepreneurship.customization.bannerImg,
                                logoUrl = entrepreneurship.customization.profileImg,
                                name = entrepreneurship.name,
                                slogan = entrepreneurship.slogan
                            )
                        )

                        EntrepreneurshipRating(
                            rating = reviews.averageRating,
                            reviewsCount = reviews.totalReviews
                        )

                        EntrepreneurshipDescription(entrepreneurship.description)

                        TagSection(
                            tags = entrepreneurship.tags.mapNotNull { category ->
                                TagType.entries.find { it.displayName.lowercase() == category.name.lowercase() }
                            },
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                        EntrepreneurshipMembersPreview(partners = partners.partners)

                        EntrepreneurshipProductsPreview(
                            products = products.products,
                            onProductClick = {}
                        )
                    }
                }
            )
        }
    }


}
