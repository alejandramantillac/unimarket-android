package com.codeoflegends.unimarket.features.home.ui.screens.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.core.ui.components.SecondaryButton
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.home.ui.components.BannerCarousel
import com.codeoflegends.unimarket.features.home.ui.components.EntrepreneurshipSection
import com.codeoflegends.unimarket.features.home.ui.components.ProductCategoriesSection
import com.codeoflegends.unimarket.features.home.ui.components.ProductSearchBar
import com.codeoflegends.unimarket.features.home.ui.components.ProductSection
import com.codeoflegends.unimarket.features.home.ui.viewModel.HomeActionState
import com.codeoflegends.unimarket.features.home.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    manager: NavigationManager,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val actionState by viewModel.actionState.collectAsState()

    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            barOptions = {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = { manager.navController.navigate(Routes.ManageHistory.route) }) {
                        Icon(
                            imageVector = Icons.Filled.ReceiptLong,
                            contentDescription = "Pedidos",
                            tint = Color.White,
                        )
                    }
                    IconButton(onClick = { manager.navController.navigate(Routes.Cart.route) }) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = Color.White,
                        )
                    }
                }
            }
        )
    ) {
        if (actionState is HomeActionState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Scaffold(
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        val state by viewModel.uiState.collectAsState()

                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                // Espacio superior
                                item {
                                    Spacer(modifier = Modifier.height(40.dp))
                                }

                                // Barra de búsqueda prominente
                                item {
                                    ProductSearchBar(
                                        onClick = {
                                            manager.navController.navigate(Routes.HomeSearch.route)
                                        }
                                    )
                                }

                                // Carrusel de banners
                                if (state.banners.isNotEmpty()) {
                                    item {
                                        BannerCarousel(banners = state.banners)
                                    }
                                }

                                item {
                                    Column {
                                        //Texto de bienvenida
                                        Text(
                                            text = "¡Hola! ¿Qué buscas?",
                                            style = MaterialTheme.typography.headlineMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                                        )

                                        EntrepreneurshipSection(
                                            title = "Tu mercado universitario",
                                            fixedFilters = listOf(),
                                            manager = manager,
                                        )
                                    }
                                }
                            }
                        }
                    }
                    
                }
            }
        }
    }
}