package com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.order.ui.components.OrderProductList
import com.codeoflegends.unimarket.features.order.ui.screens.common.pages.ClientDetails
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.order.ui.viewModel.OrderSummaryViewModel
import com.codeoflegends.unimarket.features.order.ui.viewModel.OrderSummaryActionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSummaryScreen(
    orderId: String?,
    viewModel: OrderSummaryViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {

    Log.d("OrderSummaryScreen", "ID de la orden recibida: $orderId")
    // Cargar la orden desde el ViewModel
    LaunchedEffect(orderId) {
        viewModel.loadOrder(orderId)
    }

    // Observar el estado de la UI
    val uiState by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    // Mostrar loading mientras se carga la orden
    if (actionState is OrderSummaryActionState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Cargando orden...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        return
    }

    // Mostrar mensaje de error si la orden no se pudo cargar
    if (actionState is OrderSummaryActionState.Error) {
        val errorState = actionState as OrderSummaryActionState.Error
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Error al cargar la orden: ${errorState.message}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        return
    }

    // Mostrar mensaje si la orden no existe (solo después de que termine la carga)
    if (uiState.id == null) {
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

    var selectedStatus by remember { mutableStateOf(uiState.status) }
    var expanded by remember { mutableStateOf(false) }
    
    val availableStatuses by viewModel.availableStatuses.collectAsState()
    
    val statusColors = mapOf(
        "pendiente" to Color(0xFFFFA500),
        "confirmado" to Color(0xFF4CAF50),
        "completado" to Color(0xFF2196F3),
        "cancelado" to Color(0xFFF44336)
    )

    LaunchedEffect(uiState.status) {
        selectedStatus = uiState.status
    }

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

        // Estado actual
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = statusColors[selectedStatus] ?: Color.Gray
                )
            ) {
                Text(
                    text = "Estado: ${selectedStatus.uppercase()}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
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

        // Subtítulo: Cliente
        item {
            Text(
                text = "Información del Cliente",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp, vertical = 8.dp)
            )
        }

        // Información del cliente
        item {
            uiState.client?.let {
                ClientDetails(client = it)
            } ?: Text(
                text = "Información del cliente no disponible",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        // Actualizar estado del pedido
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Actualizar Estado del Pedido",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                // Dropdown para seleccionar estado
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedStatus,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Estado") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        availableStatuses.forEach { orderStatus ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .padding(2.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            androidx.compose.foundation.Canvas(modifier = Modifier.size(12.dp)) {
                                                drawCircle(color = statusColors[orderStatus.name] ?: Color.Gray)
                                            }
                                        }
                                        Text(orderStatus.name.uppercase())
                                    }
                                },
                                onClick = {
                                    selectedStatus = orderStatus.name
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Botón de actualizar
                MainButton(
                    text = "Actualizar Estado",
                    onClick = {
                        viewModel.updateOrderStatus(selectedStatus)
                    },
                    leftIcon = Icons.Default.CheckCircle,
                    enabled = selectedStatus != uiState.status && actionState !is OrderSummaryActionState.Loading
                )
            }
        }

        // Espaciado final
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}