package com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.Comment
import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.features.product.data.model.Review
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun ProductBuyerReviewsPage(reviews: List<Review>, onRateClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Texto temporal para depuración
            Text("Cantidad de reviews: ${reviews.size}")
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
                        val commentData = CommentData(
                            userId = review.userProfileId,
                            userName = "Usuario", // TODO: Obtener el nombre del usuario
                            userImageUrl = "",
                            rating = review.rating.toFloat(),
                            comment = review.comment,
                            date = review.creationDate
                        )
                        Comment(comment = commentData)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
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