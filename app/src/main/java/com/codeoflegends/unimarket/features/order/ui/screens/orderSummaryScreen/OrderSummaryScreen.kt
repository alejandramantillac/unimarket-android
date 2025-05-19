package com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen

import DeliveryDetails
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.features.order.data.mock.MockOrderDatabase
import com.codeoflegends.unimarket.features.order.ui.components.OrderProductList
import com.codeoflegends.unimarket.features.order.ui.components.OrderStatusHistory
import com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.pages.ClientDetails
import com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.pages.PaymentDetails
import androidx.compose.ui.tooling.preview.Preview
import com.codeoflegends.unimarket.features.order.ui.components.OrderProductItem
import java.util.UUID

@Composable
fun OrderSummaryScreen(onTabSelected: (Int) -> Unit, orderId: UUID?) {
    
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Obtén la orden mock
    val mockOrder = MockOrderDatabase.getMockOrder()

    val tabs = listOf(
        Tab(
            title = "Pago",
            content = { PaymentDetails(payment = mockOrder.payment) }
        ),
        Tab(
            title = "Cliente",
            content = { ClientDetails(client = mockOrder.client) }
        ),
        Tab(
            title = "Entrega",
            content = { DeliveryDetails(delivery = mockOrder.delivery) }
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Título del pedido
        item {
            Text(
                text = "Pedido #${mockOrder.id}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
        }

        // Fecha del pedido
        item {
            Text(
                text = mockOrder.date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Estado del pedido
        item {
            Text(
                text = "Estado: ${mockOrder.status.lastOrNull()?.status ?: "Desconocido"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
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
                products = mockOrder.products,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Subtítulo: Historial de estados
        item {
            Text(
                text = "Historial de estados",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp, vertical = 2.dp)
            )
        }

        // Historial de estados
        item {
            OrderStatusHistory(
                statusHistory = mockOrder.status,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Tab de detalles
        item {
            TabSelector(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                    onTabSelected(index)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderSummaryScreenPreview() {
    OrderSummaryScreen(
        onTabSelected = {},
        orderId = UUID.randomUUID()
    )
}