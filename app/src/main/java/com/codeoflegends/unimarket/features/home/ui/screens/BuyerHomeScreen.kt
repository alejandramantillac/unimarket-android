package com.codeoflegends.unimarket.features.home.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType
import com.codeoflegends.unimarket.features.product.ui.components.ProductItem
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductViewModel

@Composable
fun BuyerHomeScreen(
    manager: NavigationManager = NavigationManager(rememberNavController(), viewModel()),
) {
    val authState by manager.authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido a UniMarket!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (authState.state == AuthStateType.AUTHENTICATED) {
                    Text(
                        text = "Usuario: ${authState.userId}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Rol: ${authState.authorities}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "No estás autenticado",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        MainButton(
            onClick = { manager.navController.navigate(Routes.ManageProduct.base) },
            modifier = Modifier.fillMaxWidth(),
            text = "Crear Producto"
        )

        // Lista de productos disponibles
        if (products.isNotEmpty()) {
            Text(
                text = "Productos disponibles",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onEditClick = {
                            manager.navController.navigate(
                                Routes.ManageProduct.createRoute(product.id!!.toString())
                            )
                        },
                        onViewClick = {
                            manager.navController.navigate(
                                Routes.ProductView.createRoute(product.id!!.toString())
                            )
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay productos disponibles",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        MainButton(
            onClick = { manager.navController.navigate(Routes.ManageOrder.route) },
            modifier = Modifier.fillMaxWidth(),
            text = "Ir a ManageOrder"
        )

        MainButton(
            onClick = { manager.authViewModel.logout() },
            modifier = Modifier.fillMaxWidth(),
            text = "Cerrar sesión"
        )
    }
}