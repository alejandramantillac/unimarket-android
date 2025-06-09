package com.codeoflegends.unimarket.features.home.ui.screens.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.features.cart.ui.screens.CartScreen
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.NavigationBar
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.NavigationItem
import com.codeoflegends.unimarket.features.home.ui.screens.homeScreen.pages.Home
import com.codeoflegends.unimarket.features.home.ui.screens.homeScreen.pages.Profile
import com.codeoflegends.unimarket.features.home.ui.viewModel.HomeActionState
import com.codeoflegends.unimarket.features.home.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    manager: NavigationManager,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val items = listOf(
        NavigationItem(
            route = "profile",
            icon = Icons.Default.Person,
            label = "Mi perfil"
        ),
        NavigationItem(
            route = "home",
            icon = Icons.Default.Home,
            label = "Inicio"
        ),
        NavigationItem(
            route = "cart",
            icon = Icons.Default.ShoppingCart,
            label = "Carrito"
        ),
    )

    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    MainLayout {
        if (actionState is HomeActionState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        currentRoute = state.currentRoute,
                        onNavigate = { route -> viewModel.onNavigationItemSelected(route) },
                        items
                    )
                },
                modifier = Modifier.padding(top = 20.dp),
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (state.currentRoute) {
                        "home" -> Home(manager, viewModel)
                        "profile" -> Profile()
                        "cart" -> CartScreen(
                            cartViewModel = hiltViewModel(),
                            onOrderCreated = {}
                        )
                    }
                }
            }
        }
    }
}