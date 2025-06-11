package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.Comment
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.ui.components.LoadingOverlay
import com.codeoflegends.unimarket.core.ui.components.RatingStars
import com.codeoflegends.unimarket.core.ui.components.TagType
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipBanner
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipDetails
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipDetailsActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipDetailsViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipReviewActionState

@Composable
fun EntrepreneurshipDetailPage(
    viewModel: EntrepreneurshipDetailsViewModel = hiltViewModel(),
    basicState: EntrepreneurshipBasicUiState,
    manager: NavigationManager
) {
    val actionState by viewModel.actionState.collectAsState()
    val reviewState by viewModel.reviewState.collectAsState()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(basicState.id) {
        viewModel.loadEntrepreneurshipDetails(basicState.id)
    }

    // TODO: Considerar el caso cuando un tag no se tenga Mapeado
    val entrepreneurshipTags = state.tags.mapNotNull { tag ->
        TagType.entries.find { it.displayName.lowercase() == tag.name.lowercase() }
    }

    if (actionState is EntrepreneurshipDetailsActionState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        InfiniteScrollList(
            items = state.reviews,
            onLoadMore = { viewModel.loadMoreReviews(basicState.id) },
            isLoading = reviewState is EntrepreneurshipReviewActionState.Loading,
            itemContent = { review ->
                Comment(comment = review)
            },
            headerContent = {
                Column {
                    EntrepreneurshipBanner(
                        name = basicState.name,
                        profileUrl = basicState.customization.profileImg,
                        bannerUrl = basicState.customization.bannerImg,
                        slogan = state.slogan
                    )

                    Row(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                        RatingStars(rating = state.averageRating)
                        Text(
                            text = "(${state.totalReviews} Reseñas)",
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    EntrepreneurshipDetails(
                        description = state.description,
                        entrepreneurshipTags = entrepreneurshipTags
                    )
                }
            },
            titleContent = {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Valoraciones y reseñas",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Las calificaciones y reseñas vienen de clientes que han comprado en tu emprendimiento.",
                        style = androidx.compose.material3.MaterialTheme.typography.titleSmall
                    )
                }
            }
        )
    }
}
