package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.ui.components.Comment
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.ui.components.TagType
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EditEntrepreneurshipDialog
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipDescription
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipHeader
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipHeaderData
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipRating
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipReviewsActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipReviewsViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipSellerActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipSellerViewModel
import java.util.*

@Composable
fun EntrepreneurshipDetailPage(
    entrepreneurshipViewModel: EntrepreneurshipSellerViewModel,
    reviewsViewModel: EntrepreneurshipReviewsViewModel = hiltViewModel(),
) {
    // UI State
    val entrepreneurshipState by entrepreneurshipViewModel.entrepreneurshipUiState.collectAsState()
    val reviewState by reviewsViewModel.reviewsUiState.collectAsState()

    // Action State
    val actionState by entrepreneurshipViewModel.actionState.collectAsState()
    val reviewActionState by reviewsViewModel.reviewsActionState.collectAsState()
    val isEditDialogOpen by entrepreneurshipViewModel.isEditDialogOpen.collectAsState()

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }

    val entrepreneurshipTags = entrepreneurshipState.tags.mapNotNull { tag ->
        TagType.entries.find { it.displayName.lowercase() == tag.name.lowercase() }
    }

    LaunchedEffect(entrepreneurshipState.id) {
        if (entrepreneurshipState.id != UUID.randomUUID()) {
            reviewsViewModel.loadReviewDetails(entrepreneurshipState.id)
        }
    }

    // Show success message when update is successful
    LaunchedEffect(actionState) {
        if (actionState is EntrepreneurshipSellerActionState.UpdateSuccess) {
            snackbarHostState.showSnackbar("Emprendimiento actualizado exitosamente")
        } else if (actionState is EntrepreneurshipSellerActionState.Error) {
            snackbarHostState.showSnackbar((actionState as EntrepreneurshipSellerActionState.Error).message)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (actionState is EntrepreneurshipSellerActionState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
        InfiniteScrollList(
            items = reviewState.reviews,
            onLoadMore = { reviewsViewModel.loadMoreReviews(entrepreneurshipState.id) },
            isLoading = reviewActionState is EntrepreneurshipReviewsActionState.Loading,
            itemContent = { review ->
                Comment(comment = review)
            },
            headerContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    EntrepreneurshipHeader(
                        headerData = EntrepreneurshipHeaderData(
                            bannerUrl = entrepreneurshipState.customization.bannerImg,
                            logoUrl = entrepreneurshipState.customization.profileImg,
                            name = entrepreneurshipState.name,
                            slogan = entrepreneurshipState.slogan
                        )
                    )

                    EntrepreneurshipRating(
                        rating = reviewState.averageRating,
                        reviewsCount = reviewState.totalReviews
                    )

                    EntrepreneurshipDescription(entrepreneurshipState.description)
                }
            },
            titleContent = {
                Column(
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Valoraciones y reseñas",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Las calificaciones y reseñas vienen de clientes que han comprado en tu emprendimiento.",
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
        )
        }

        // Floating Action Button for editing
        FloatingActionButton(
            onClick = { entrepreneurshipViewModel.openEditDialog() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF6200EE)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar emprendimiento",
                tint = Color.White
            )
        }

        // Edit Dialog
        EditEntrepreneurshipDialog(
            isOpen = isEditDialogOpen,
            currentName = entrepreneurshipState.name,
            currentSlogan = entrepreneurshipState.slogan,
            currentDescription = entrepreneurshipState.description,
            currentEmail = entrepreneurshipState.email,
            currentPhone = entrepreneurshipState.phone,
            onDismiss = { entrepreneurshipViewModel.closeEditDialog() },
            onConfirm = { name, slogan, description, email, phone ->
                entrepreneurshipViewModel.updateEntrepreneurship(
                    name = name,
                    slogan = slogan,
                    description = description,
                    email = email,
                    phone = phone
                )
            },
            isUpdating = actionState is EntrepreneurshipSellerActionState.Updating
        )

        // Snackbar Host
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        )
    }
}
