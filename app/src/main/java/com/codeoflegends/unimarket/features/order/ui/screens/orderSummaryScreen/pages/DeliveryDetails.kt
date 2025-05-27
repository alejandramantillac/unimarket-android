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
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.order.data.model.Delivery

@Composable
fun DeliveryDetails(delivery: Delivery, modifier: Modifier = Modifier) {
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
            // Título: Dirección de entrega
            Text(
                text = "Dirección de entrega",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            )
            Divider(color = Color.LightGray, thickness = 1.dp)


            // Ubicación de entrega
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📍",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Ubicación de entrega",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = delivery.deliveryAddress,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            // Estado de entrega
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📅",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Estado de entrega",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = delivery.status.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            // Botón: Marcar como entregado
            Spacer(modifier = Modifier.height(16.dp))
            MainButton(
                text = "Marcar como entregado",
                onClick = { /* Acción al marcar como entregado */ },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}