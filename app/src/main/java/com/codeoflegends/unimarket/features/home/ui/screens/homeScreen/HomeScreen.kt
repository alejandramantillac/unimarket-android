package com.codeoflegends.unimarket.features.home.ui.screens.homeScreen

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.MainLayout
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
                modifier = Modifier.padding(top = 20.dp),
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
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                // Barra de búsqueda prominente
                                ProductSearchBar(
                                    onClick = {
                                        manager.navController.navigate(Routes.HomeSearch.route)
                                    }
                                )

                                // Carrusel de banners
                                if (state.banners.isNotEmpty()) {
                                    BannerCarousel(banners = state.banners)
                                }

                                //Texto de bienvenida
                                Text(
                                    text = "¡Bienvenido de vuelta!",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                                )

                                ProductCategoriesSection()

                                ProductSection(
                                    title = "Para vestir a la moda",
                                    fixedFilters = listOf(DirectusQuery.Filter("category", "eq", "Moda")),
                                    manager = manager,
                                )

                                EntrepreneurshipSection(
                                    title = "Nuestros mejores aliados",
                                    fixedFilters = listOf(),
                                    manager = manager,
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .width(290.dp)
                            .padding(30.dp)
                            .align(Alignment.BottomCenter),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            MainButton(
                                text = "Ver mi carrito",
                                onClick = {
                                    manager.navController.navigate(Routes.Cart.route)
                                },
                                modifier = Modifier.width(150.dp).height(40.dp)
                            )
                            MainButton(
                                text = "",
                                leftIcon = Icons.Default.Settings,
                                onClick = {
                                    manager.navController.navigate(Routes.Settings.route)
                                },
                                modifier = Modifier.width(60.dp).height(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}