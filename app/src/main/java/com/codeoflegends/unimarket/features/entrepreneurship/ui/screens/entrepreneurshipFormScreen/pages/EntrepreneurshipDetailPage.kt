package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.core.ui.components.CommentSection
import com.codeoflegends.unimarket.core.ui.components.RatingStars
import com.codeoflegends.unimarket.core.ui.components.TagSection
import com.codeoflegends.unimarket.core.ui.components.TagType
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel

@Composable
fun EntrepreneurshipDetailPage(viewModel: EntrepreneurshipViewModel) {
    // Datos de ejemplo
    val bannerUrl = "https://picsum.photos/800/400" // URL de ejemplo para el banner
    val logoUrl = "https://picsum.photos/200" // URL de ejemplo para el logo
    val emprendimientoName = "Tech Solutions"
    val eslogan = "Innovación y calidad a tu alcance"
    val rating = 4f
    val numReviews = 128
    val description = "Somos una empresa dedicada a brindar soluciones tecnológicas innovadoras. " +
            "Nuestro compromiso es ofrecer productos y servicios de la más alta calidad, " +
            "satisfaciendo las necesidades de nuestros clientes con profesionalismo y eficiencia."

    val comments = listOf(
        CommentData(),
        CommentData(),
        CommentData(),
        CommentData(),
        CommentData(),
        CommentData(),
        CommentData(),
        CommentData(),
        CommentData(),
        CommentData()
    )

    val selectedTags = remember {
        listOf(
            TagType.ELECTRONICS,
            TagType.CLOTHING,
            TagType.FOOD,
            TagType.BOOKS,
            TagType.SPORTS,
            TagType.BEAUTY,
            TagType.FOOD,
            TagType.BOOKS,
            TagType.FOOD,
            TagType.BOOKS,
            TagType.SPORTS,
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Sección del Banner y Logo
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // Banner con capa de opacidad
                Image(
                    painter = rememberAsyncImagePainter(bannerUrl),
                    contentDescription = "Banner del emprendimiento",
                    modifier = Modifier.fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                // Capa negra con opacidad
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                )

                // Grupo sobre el banner
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(16.dp)
                        .align(Alignment.BottomStart),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
                ) {
                    // Logo y nombre en fila
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Logo
                        Image(
                            painter = rememberAsyncImagePainter(logoUrl),
                            contentDescription = "Logo del emprendimiento",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        // Nombre y eslogan
                        Column {
                            Text(
                                text = emprendimientoName,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = eslogan,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }

        // Sección de Descripción y Rating
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Descripción
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                TagSection(selectedTags)
            }
        }

        // Sección de comentarios
        item {
            CommentSection(
                comments = comments,
                averageRating = rating,
                totalReviews = 124,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp),
            )
        }
    }
}