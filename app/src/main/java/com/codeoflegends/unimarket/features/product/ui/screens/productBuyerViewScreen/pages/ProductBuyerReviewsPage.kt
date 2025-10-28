package com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.Comment
import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.features.product.data.model.Review
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun ProductBuyerReviewsPage(
    reviews: List<Review>, 
    onRateClick: () -> Unit,
    currentUserProfileId: UUID? = null,
    onDeleteReview: (UUID) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Verificar si el usuario actual ya tiene una reseña
            val hasUserReview = currentUserProfileId?.let { userId ->
                reviews.any { it.userProfileId == userId }
            } ?: false
            
            if (reviews.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay reseñas disponibles",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(reviews) { review ->
                        val isCurrentUserReview = currentUserProfileId?.let { userId ->
                            review.userProfileId == userId
                        } ?: false
                        
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = if (isCurrentUserReview) 8.dp else 2.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCurrentUserReview) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            ),
                            shape = RoundedCornerShape(16.dp),
                            border = if (isCurrentUserReview) {
                                BorderStroke(
                                    2.dp, 
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                )
                            } else null
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Badge para indicar que es tu reseña
                                    if (isCurrentUserReview) {
                                        Surface(
                                            color = MaterialTheme.colorScheme.inversePrimary,
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(
                                                text = "Tu reseña",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                    
                                    // Icono de eliminar solo para reseñas del usuario actual
                                    if (isCurrentUserReview) {
                                        IconButton(
                                            onClick = { onDeleteReview(review.id) },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Eliminar reseña",
                                                tint = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                val commentData = CommentData(
                                    userId = review.userProfileId,
                                    userName = if (isCurrentUserReview) "Tú" else "Usuario",
                                    userImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSYFuA8WBBIAvQWabLBD4tskBReFvrl4THCQ&s",
                                    rating = review.rating.toFloat(),
                                    comment = review.comment,
                                    date = review.creationDate
                                )
                                Comment(comment = commentData)
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Solo mostrar el botón si el usuario no tiene reseña
            if (!hasUserReview) {
                Button(
                    onClick = onRateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Calificar producto")
                }
            }
        }
    }
} 