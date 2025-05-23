package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue  // Añadir esta importación
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.ui.components.Comment
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.ui.components.TagType
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipBanner
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipDetails
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipDetailsActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipDetailsViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel

@Composable
fun EntrepreneurshipDetailPage(
    viewModel: EntrepreneurshipDetailsViewModel = hiltViewModel(),
    basicState: EntrepreneurshipBasicUiState
) {
    val actionState by viewModel.actionState.collectAsState()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(basicState.id) {
        viewModel.loadEntrepreneurshipDetails(basicState.id)
    }

    // TODO: Considerar el caso cuando un tag no se tenga Mapeado
    val entrepreneurshipTags = state.tags.mapNotNull { tag ->
        TagType.entries.find { it.displayName.lowercase() == tag.name.lowercase() }
    }

    val entrepreneurshipReviews = state.reviews
    val totalReviews = entrepreneurshipReviews.count()
    val averageReviewRating = if (totalReviews > 0) {
        entrepreneurshipReviews.map { it.rating }.average().toFloat()
    } else {
        0f
    }

    InfiniteScrollList(
        items = entrepreneurshipReviews,
        onLoadMore = { viewModel.loadMoreReviews(basicState.id) },
        isLoading = actionState is EntrepreneurshipDetailsActionState.Loading,
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

                EntrepreneurshipDetails(
                    description = state.description,
                    entrepreneurshipTags = entrepreneurshipTags
                )
            }
        }
    )

    /*
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {
            EntrepreneurshipBanner(
                name = state.name,
                profileUrl = state.customization.profileImg,
                bannerUrl = state.customization.bannerImg,
                slogan = state.slogan
            )
        }

        item {
            EntrepreneurshipDetails(
                description = state.description,
                entrepreneurshipTags = entrepreneurshipTags
            )
        }

        /**
         * CommentSection(
         *             comments = entrepreneurshipReviews,
         *             emptyContent = "No tienes reseñas por ahora. Una gran oportunidad para impresionar a tus primeros clientes.",
         *             header = {
         *                 Text(
         *                     text = "Valoraciones y reseñas",
         *                     style = MaterialTheme.typography.titleLarge,
         *                     fontWeight = FontWeight.Bold
         *                 )
         *             }
         *         )
         */
        // Sección de comentarios
        /*
        item {
            CommentSection(
                comments = entrepreneurshipReviews,
                emptyContent = "No tienes reseñas por ahora. Una gran oportunidad para impresionar a tus primeros clientes.",
                header = {
                    Text(
                        text = "Valoraciones y reseñas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.padding(16.dp)
            )
        }
         */

    }

     */
}