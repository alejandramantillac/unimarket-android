package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Date

@Composable
fun CommentSection(
    comments: List<CommentData>,
    averageRating: Float,
    totalReviews: Int,
    modifier: Modifier = Modifier,
    onAddComment: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Reseñas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RatingStars(rating = averageRating)
                    Text(
                        text = "($totalReviews reseñas)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                // Botón para agregar comentario
                // TODO: Este boton debera tener en cuenta 3 cosas
                // TODO: 1. Si el usuario ya ha dejado un comentario, no se mostrara el boton y mostrara el comentario
                // TODO: 2. Si el usuario es un integrante del emprendimiento, no se mostrara el boton
                // TODO: 3. Si el usuario no ha comprado nada del emprendimiento, el boton se mostrara inhabilitado
                /*
                MainButton(
                    text = "Escribir reseña",
                    onClick = onAddComment
                )
                 */
            }
        }

        // Lista de comentarios
        if (comments.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay reseñas aún. ¡Sé el primero en opinar!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(comments) { comment ->
                    Comment(comment = comment)
                }
            }
        }
    }
}
