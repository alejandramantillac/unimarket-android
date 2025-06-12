package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.core.ui.state.ToastHandler
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.NavigationBar
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.NavigationItem
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screen.PartnerScreen
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages.EntrepreneurshipDetailPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages.EntrepreneurshipMembersPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages.EntrepreneurshipProductsPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipSellerActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipSellerViewModel
import com.codeoflegends.unimarket.features.order.ui.screens.orderListScreen.OrderListScreen
import java.util.UUID

@Composable
fun EntrepreneurshipSellerScreen(
    entrepreneurshipId: UUID,
    viewModel: EntrepreneurshipSellerViewModel = hiltViewModel(),
    manager: NavigationManager
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()
    val currentRoute by viewModel.currentRoute.collectAsState()

    LaunchedEffect(entrepreneurshipId) {
        viewModel.loadEntrepreneurship(entrepreneurshipId)
    }

    // Manejo simple de errores
    LaunchedEffect(actionState) {
        if (actionState is EntrepreneurshipSellerActionState.Error) {
            ToastHandler.handleError(
                message = (actionState as EntrepreneurshipSellerActionState.Error).message
            )
        }
    }

    val items = listOf(
        NavigationItem(
            route = "home",
            icon = Icons.Default.Home,
            label = "Inicio"
        ),
        NavigationItem(
            route = "products",
            icon = Icons.Default.ShoppingCart,
            label = "Productos"
        ),
        NavigationItem(
            route = "people",
            icon = Icons.Default.People,
            label = "Personas"
        ),
        NavigationItem(
            route = "orders",
            icon = Icons.Default.List,
            label = "Pedidos"
        ),
    )

    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            showBackButton = true,
            onBackClick = { manager.navController.popBackStack() }
        )
    ) {
        if (actionState is EntrepreneurshipSellerActionState.Error) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Error al cargar el emprendimiento",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        } else {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = { route -> viewModel.onNavigationItemSelected(route) },
                        items = items
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (currentRoute) {
                        "home" -> EntrepreneurshipDetailPage(
                            entrepreneurshipViewModel = viewModel,
                        )
                        "products" -> EntrepreneurshipProductsPage(
                            entrepreneurshipViewModel = viewModel,
                            manager = manager
                        )
                        "people" -> PartnerScreen(
                            entrepreneurshipId = state.id,
                            manager = manager
                        )
                        "orders" -> OrderListScreen(manager = manager, basicState = state)
                        //"metrics" -> EntrepreneurshipStatisticsPage(basicState = state)
                    }
                }
            }
        }
    }
}