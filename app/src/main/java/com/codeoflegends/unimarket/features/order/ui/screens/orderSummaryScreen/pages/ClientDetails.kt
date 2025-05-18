package com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.order.data.model.Client

@Composable
fun ClientDetails(client: Client, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)), // Bordes curvos
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Foto y datos del cliente
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = client.photo),
                    contentDescription = "Foto del cliente",
                    modifier = Modifier.size(64.dp)
                )
                Column {
                    Text(text = client.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Cliente desde: ${client.sinceYear}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Línea divisora
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Correo electrónico
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://via.placeholder.com/24"),
                    contentDescription = "Icono de correo",
                    modifier = Modifier.size(24.dp)
                )
                Column {
                    Text(text = "Email", style = MaterialTheme.typography.bodyMedium)
                    Text(text = client.email, style = MaterialTheme.typography.bodySmall)
                }
            }

            // Teléfono
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://via.placeholder.com/24"),
                    contentDescription = "Icono de teléfono",
                    modifier = Modifier.size(24.dp)
                )
                Column {
                    Text(text = "Teléfono", style = MaterialTheme.typography.bodyMedium)
                    Text(text = client.phone, style = MaterialTheme.typography.bodySmall)
                }
            }

            // Botón de contactar
            MainButton(
                text = "Contactar",
                onClick = { /* Acción al contactar */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}