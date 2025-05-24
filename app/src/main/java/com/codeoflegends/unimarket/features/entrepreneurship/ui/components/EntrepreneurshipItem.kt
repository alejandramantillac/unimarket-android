package com.codeoflegends.unimarket.features.entrepreneurship.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship

@Composable
fun EntrepreneurshipItem(entrepreneurship: Entrepreneurship) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Imagen o ícono del emprendimiento
            Icon(
                imageVector = Icons.Default.Storefront,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entrepreneurship.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = entrepreneurship.slogan,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Estrellas vacías + rating numérico + reseñas
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.StarBorder,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))/*
                    Text(
                        text = "${entrepreneurship.rating} (${entrepreneurship.reviews} reseñas)",
                        style = MaterialTheme.typography.bodySmall
                    )
                    */
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column {
                        Text("Productos", style = MaterialTheme.typography.labelSmall)
                        Text("${entrepreneurship.products.count()}", style = MaterialTheme.typography.bodyMedium)
                    }
                    Column {
                        Text("Ventas", style = MaterialTheme.typography.labelSmall)
                        Text("${entrepreneurship.orders.size}", style = MaterialTheme.typography.bodyMedium)
                    }

                }
            }
        }
    }
}

