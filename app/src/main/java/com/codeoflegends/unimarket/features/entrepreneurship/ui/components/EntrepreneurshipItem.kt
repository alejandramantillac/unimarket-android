package com.codeoflegends.unimarket.features.entrepreneurship.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.RatingStars
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.core.utils.DirectusImageUrl
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun EntrepreneurshipItem(
    entrepreneurship: Entrepreneurship,
    onClick: () -> Unit
) {
    // Construir URL completa de la imagen
    val imageUrl = DirectusImageUrl.buildFullUrl(entrepreneurship.customization.profileImg)
    
    // Calcular número de ventas (órdenes con status "completado")
    val deliveredOrders = entrepreneurship.orders.count { order ->
        order.status.name.equals("completado", ignoreCase = true)
    }
    
    // Calcular rating promedio de las reseñas
    val averageRating = if (entrepreneurship.reviews.isNotEmpty()) {
        entrepreneurship.reviews.map { it.rating }.average().toFloat()
    } else {
        0f
    }
    
    val reviewCount = entrepreneurship.reviews.size
    
    // Log para debugging
    android.util.Log.d("EntrepreneurshipItem", "=== Cargando emprendimiento: ${entrepreneurship.name} ===")
    android.util.Log.d("EntrepreneurshipItem", "profileImg original: '${entrepreneurship.customization.profileImg}'")
    android.util.Log.d("EntrepreneurshipItem", "profileImg URL completa: '$imageUrl'")
    android.util.Log.d("EntrepreneurshipItem", "Total órdenes: ${entrepreneurship.orders.size}, Entregadas: $deliveredOrders")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            // Primera fila: Imagen y detalles principales
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Imagen del emprendimiento
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = "Imagen del emprendimiento",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        },
                        error = {
                            android.util.Log.e("EntrepreneurshipItem", "Error cargando imagen: $imageUrl")
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Storefront,
                                    contentDescription = "Sin imagen",
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    )
                }

                // Columna de detalles
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Nombre y descripción
                    Column {
                        Text(
                            text = entrepreneurship.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = entrepreneurship.slogan,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                modifier = Modifier.padding(top = 8.dp),
            ) {
                RatingStars(averageRating)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (reviewCount == 0) {
                        "sin reseñas"
                    } else {
                        "($reviewCount reseña${if (reviewCount != 1) "s" else ""})"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Segunda fila: Estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Productos
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Productos",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${entrepreneurship.products.count()}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Separador vertical
                Divider(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                // Ventas
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Ventas",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$deliveredOrders",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

