package com.codeoflegends.unimarket.features.order.ui.screens.buyerOrderSummaryScreen

import DeliveryDetails
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.features.order.ui.components.OrderProductList
import com.codeoflegends.unimarket.features.order.ui.screens.common.pages.EntrepreneurshipDetails
import com.codeoflegends.unimarket.features.order.ui.screens.common.pages.PaymentDetails
import com.codeoflegends.unimarket.features.order.ui.viewModel.BuyerOrderSummaryViewModel

@Composable
fun BuyerOrderSummaryScreen(
    orderId: String?,
    viewModel: BuyerOrderSummaryViewModel = hiltViewModel(),
) {

    LaunchedEffect(orderId) {
        viewModel.loadOrder(orderId)
    }

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.id == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pedido no encontrado",
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
                    } ?: Text("Información del pedido no disponible")
                } ?: Text("Información de pago no disponible")
            }
        ),
        Tab(
            title = "Vendedor",
            content = {
                uiState.entrepreneurship?.let {
                    EntrepreneurshipDetails(entrepreneurship = it)
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