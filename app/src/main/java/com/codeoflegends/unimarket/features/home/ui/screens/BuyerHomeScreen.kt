package com.codeoflegends.unimarket.features.home.ui.screens

import android.util.Log
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
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val authState by manager.authViewModel.authState.collectAsState()
    val products by productViewModel.products.collectAsState()

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

        // LOG VISUAL DEL ROL
        Text(
            text = "Authorities: ${authState.authorities}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
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
                    val isShopper = authState.authorities.contains("Shopper", ignoreCase = true)
                    ProductItem(
                        product = product,
                        onEditClick = {}, // No acción para editar si es shopper
                        onViewClick = {
                            Log.d("BuyerHomeScreen", "Authorities: ${authState.authorities}, isBuyer: $isShopper")
                            manager.navController.currentBackStackEntry?.savedStateHandle?.set("lastIsBuyer", isShopper)
                            if (isShopper) {
                                manager.navController.navigate(
                                    Routes.ProductBuyerView.createRoute(product.id!!.toString())
                                )
                            } else {
                                manager.navController.navigate(
                                    Routes.ProductView.createRoute(product.id!!.toString())
                                )
                            }
                        },
                        isShopper = isShopper
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

        // Mostrar el último valor de isBuyer si existe
        val lastIsBuyer = manager.navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>("lastIsBuyer")
        if (lastIsBuyer != null) {
            Text(
                text = "Último isBuyer: $lastIsBuyer",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
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