package com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen

import DeliveryDetails
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.features.order.ui.components.OrderProductList
import com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.pages.ClientDetails
import com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.pages.PaymentDetails
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.order.ui.viewModel.OrderSummaryViewModel
import java.util.UUID

@Composable
fun OrderSummaryScreen(
    orderId: String?,
    viewModel: OrderSummaryViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {
    // Cargar la orden desde el ViewModel
    LaunchedEffect(orderId) {
        viewModel.loadOrder(orderId)
    }

    // Observar el estado de la UI
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.id == null) {
        // Mostrar mensaje si la orden no existe
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Orden no encontrada",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        return
    }

    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf(
        Tab(
            title = "Pago",
            content = {
                uiState.payment?.let { payment ->
                    uiState.order?.let { order ->
                        PaymentDetails(payment = payment, order = order)
                    } ?: Text("Información de la orden no disponible")
                } ?: Text("Información de pago no disponible")
            }
        ),
        Tab(
            title = "Cliente",
            content = {
                uiState.client?.let {
                    ClientDetails(client = it)
                } ?: Text("Información del cliente no disponible")
            }
        ),
        Tab(
            title = "Entrega",
            content = {
                uiState.delivery?.let {
                    DeliveryDetails(delivery = it)
                } ?: Text("Información de entrega no disponible")
            }
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Título del pedido
        item {
            Text(
                text = "Pedido #${uiState.id}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
        }

        // Fecha del pedido
        item {
            Text(
                text = uiState.date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Subtítulo: Productos
        item {
            Text(
                text = "Productos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp, vertical = 2.dp)
            )
        }

        // Lista de productos
        item {
            OrderProductList(
                products = uiState.products,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Tab de detalles
        item {
            TabSelector(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )
        }
    }
}