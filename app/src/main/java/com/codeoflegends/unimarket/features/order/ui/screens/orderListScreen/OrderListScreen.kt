package com.codeoflegends.unimarket.features.order.ui.screens.orderListScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ListItemComponent
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.order.data.mock.MockOrderDatabase
import com.codeoflegends.unimarket.features.order.ui.viewModel.OrderActionState
import com.codeoflegends.unimarket.features.order.ui.viewModel.OrderListViewModel
import java.util.UUID

@Composable
fun OrderListScreen(
    viewModel: OrderListViewModel = hiltViewModel(),
    manager: NavigationManager? = null,
    onOrderClick: (UUID) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

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
        /*MainButton(
            text = "Ver análisis completo",
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )*/

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

        // Lista de órdenes
        when (actionState) {
            is OrderActionState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is OrderActionState.Error -> {
                Text(
                    text = (actionState as OrderActionState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                uiState.orders.forEach { order ->
                    ListItemComponent(
                        id = order.id,
                        image = rememberImagePainter(data = order.clientPhoto),
                        title = order.clientName,
                        subtitle = "${order.productCount} productos ~ $${order.totalPrice}",
                        rightInfo = order.date,
                        tag1 = order.status,
                        tag1Color = when (order.status) {
                            "Pendiente" -> Color.Yellow
                            "Completado" -> Color.Green
                            else -> Color.Gray
                        },
                        modifier = Modifier.clickable {
                            Log.d("OrderListScreen", "Orden seleccionada: ${order.id}")
                            manager?.navController?.navigate(
                                Routes.OrderSummary.createRoute(order.id.toString())
                            )
                        }
                    )
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }
    }
}