package com.codeoflegends.unimarket.features.order.ui.screens.orderListScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.codeoflegends.unimarket.core.ui.components.ListItemComponent
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.order.data.mock.MockOrderDatabase
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.pages.ClientDetails
import com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.pages.PaymentDetails
import java.util.UUID

@Composable
fun OrderListScreen(
    orders: List<Order>,
    onAnalysisClick: () -> Unit,
    onOrderClick: (UUID) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Título: Pedidos
        Text(
            text = "Pedidos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Card: Clientes recurrentes
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Clientes recurrentes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "75%", // Cambiar por el valor real
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Porcentaje de clientes que han comprado más de una vez",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Card: Promedio por orden
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Promedio por orden",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$50.00", // Cambiar por el valor real
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Botón: Ver análisis completo
        MainButton(
            text = "Ver análisis completo",
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Barra de búsqueda
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Tabs de filtro
        var selectedTab by remember { mutableStateOf(0) }
        val tabs = listOf(
            com.codeoflegends.unimarket.core.ui.components.Tab(
                title = "Todos",
                content = { }
            ),
            com.codeoflegends.unimarket.core.ui.components.Tab(
                title = "Pendiente",
                content = { }
            ),
            com.codeoflegends.unimarket.core.ui.components.Tab(
                title = "Completado",
                content = { }
            )
        )

        com.codeoflegends.unimarket.core.ui.components.TabSelector(
            tabs = tabs,
            selectedTabIndex = selectedTab,
            onTabSelected = { index -> selectedTab = index }
        )

        Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            orders.forEach { order ->
                ListItemComponent(
                    image = rememberImagePainter(data = order.client.photo), // Carga la imagen desde la URL
                    title = order.client.name,
                    subtitle = "${order.products.size} productos ~ $${order.payment.products.sumOf { it.quantity * it.product.price }}",
                    rightInfo = order.date,
                    tag1 = order.status.lastOrNull()?.status,
                    tag1Color = when (order.status.lastOrNull()?.status) {
                        "Pendiente" -> Color.Yellow
                        "Completado" -> Color.Green
                        else -> Color.Gray
                    },
                    modifier = Modifier.clickable { onOrderClick(order.id) }
                )
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OrderListScreenPreview() {
    val exampleOrders = listOf(MockOrderDatabase.getMockOrder())

    OrderListScreen(
        orders = exampleOrders,
        onAnalysisClick = { /* Acción de análisis */ },
        onOrderClick = { }
    )
}

