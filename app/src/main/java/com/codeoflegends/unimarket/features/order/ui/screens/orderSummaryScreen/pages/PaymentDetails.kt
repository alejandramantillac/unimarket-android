package com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.Payment

@Composable
fun PaymentDetails(order: Order, payment: Payment, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Metodo de pago
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Método de pago",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = payment.paymentMethod.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Subtotal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal",
                )
                Text(
                    text = order.subtotal.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }

            // Descuentos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Descuentos",
                )
                Text(
                    text = order.discount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                )
                Text(
                    text = order.total.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }

            // Evidencias de pago
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Evidencias de pago",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                payment.paymentEvidences.forEach { evidence ->
                    Image(
                        painter = rememberAsyncImagePainter(model = evidence.url),
                        contentDescription = "Evidencia de pago",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Botón de confirmar pago
            Spacer(modifier = Modifier.height(16.dp))
            MainButton(
                text = "Confirmar Pago Recibido",
                onClick = { /* Acción al confirmar pago */ },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}