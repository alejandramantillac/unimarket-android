package com.codeoflegends.unimarket.features.order.ui.screens.orderListScreen

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
                // Lista de órdenes
                uiState.orders.forEach { order ->
                    ListItemComponent(
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
                            manager?.navController?.navigate(
                                Routes.OrderSummary.createRoute(order.id)
                            )
                        }
                    )
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }
    }
}