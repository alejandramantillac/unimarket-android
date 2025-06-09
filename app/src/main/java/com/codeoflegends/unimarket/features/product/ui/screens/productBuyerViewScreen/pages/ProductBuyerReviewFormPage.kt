package com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.RatingStars

@Composable
fun ProductBuyerReviewFormPage(onSubmit: (Float, String) -> Unit) {
    var rating by remember { mutableStateOf(0f) }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "¿Qué tal estuvo el producto?",
            style = MaterialTheme.typography.titleMedium
        )
        
        RatingStars(
            rating = rating,
            showRating = true,
            onRatingChanged = { rating = it }
        )
        
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Escribe una opinión") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )
        
        Button(
            onClick = { onSubmit(rating, comment) },
            enabled = rating > 0 && comment.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Calificación")
        }
    }
} 