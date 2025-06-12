package com.codeoflegends.unimarket.features.order.ui.screens.common.pages

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
import com.codeoflegends.unimarket.features.order.data.model.Entrepreneurship

@Composable
fun EntrepreneurshipDetails(entrepreneurship: Entrepreneurship, modifier: Modifier = Modifier) {
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
            // Foto y datos del emprendimiento
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    Text(text = entrepreneurship.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = entrepreneurship.slogan,
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
                Text(text = "Email:", style = MaterialTheme.typography.bodyMedium)
                Text(text = entrepreneurship.email, style = MaterialTheme.typography.bodySmall)
            }

            // Teléfono
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Teléfono:", style = MaterialTheme.typography.bodyMedium)
                Text(text = entrepreneurship.phone, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}