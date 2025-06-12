package com.codeoflegends.unimarket.features.order.ui.screens.common.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.ui.components.MainButton

@Composable
fun ClientDetails(client: User, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
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
                    painter = rememberAsyncImagePainter(model = client.profile.profilePicture),
                    contentDescription = "Foto del cliente",
                    modifier = Modifier.size(64.dp)
                )
                Column {
                    Text(text = client.firstName, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Cliente desde: ${client.profile.registrationDate}",
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
        }
    }
}