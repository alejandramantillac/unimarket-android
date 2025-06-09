package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.ui.components.TagSection
import com.codeoflegends.unimarket.core.ui.components.TagType
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipDescription
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipHeader
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipHeaderData
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipMembersPreview
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipRating
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicDetailsActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBuyerDetailsViewModel
import java.util.*

@Composable
fun EntrepreneurshipDetailsScreen(
    viewModel: EntrepreneurshipBuyerDetailsViewModel = hiltViewModel(),
    entrepreneurshipId: UUID,
    onProductClick: () -> Unit = {},
) {
    // UI states
    val entrepreneurship by viewModel.entrepreneurshipUiState.collectAsState()
    val partners by viewModel.partnersUiState.collectAsState()
    val reviews by viewModel.reviewsUiState.collectAsState()

    // Actions states
    val actionState by viewModel.actionState.collectAsState()

    LaunchedEffect(entrepreneurshipId) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                item {
                    EntrepreneurshipHeader(
                        headerData = EntrepreneurshipHeaderData(
                            bannerUrl = entrepreneurship.customization.bannerImg,
                            logoUrl = entrepreneurship.customization.profileImg,
                            name = entrepreneurship.name,
                            slogan = entrepreneurship.slogan
                        )
                    )
                }

                item {
                    EntrepreneurshipRating(reviews.averageRating)
                }

                item {
                    EntrepreneurshipDescription(entrepreneurship.description)
                }

                item {
                    TagSection(
                        tags = entrepreneurship.tags.mapNotNull { category ->
                            TagType.entries.find { it.displayName.lowercase() == category.name.lowercase() }
                        },
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }

                item {
                    EntrepreneurshipMembersPreview(partners = partners.partners)
                }

                /*
                TODO: Seleccionar 5 productos porque los originales son puramente UUIDs
                // Productos destacados
                item {
                    EntrepreneurshipProductsPreview(
                        entrepreneurshipProducts = entrepreneurship,
                        onProductClick = {}
                    )
                }
                */
                /*
                // Valoraciones y reseñas
                item {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "Valoraciones y reseñas",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Las calificaciones y reseñas vienen de clientes que han comprado en este entrepreneurship.",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        resenas.forEach { resena ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF1A1A1A)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = resena.usuario,
                                            color = Color.White,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = resena.fecha,
                                            color = Color.Gray,
                                            fontSize = 12.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Row {
                                        repeat(5) { index ->
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                                tint = if (index < resena.calificacion) {
                                                    Color(0xFF9C27B0)
                                                } else {
                                                    Color.Gray
                                                },
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = resena.comentario,
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                */
            }
        }
    }


}
