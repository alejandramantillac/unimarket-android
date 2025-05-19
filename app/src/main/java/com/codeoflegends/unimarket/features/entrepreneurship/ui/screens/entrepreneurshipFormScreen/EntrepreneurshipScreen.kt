package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.NavigationBar
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipDetailPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipMembersPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipOrdersPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipProductsPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipStatisticsPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel

@Composable
fun EntrepreneurshipScreen(
    entrepreneurshipId: String? = null,
    viewModel: EntrepreneurshipViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    LaunchedEffect(entrepreneurshipId) {
        viewModel.loadEntrepreneurship(entrepreneurshipId)
    }

    LaunchedEffect(actionState) {
        when (actionState) {
            is EntrepreneurshipActionState.Error -> {
                // Mostrar error
            }
            is EntrepreneurshipActionState.Success -> {
                // Navegar de vuelta
                manager?.navController?.popBackStack()
            }
            else -> {}
        }
    }

    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            showBackButton = true,
            onBackClick = { manager?.navController?.popBackStack() }
        )
    ) {
        if (actionState is EntrepreneurshipActionState.Loading) {
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
                        onNavigate = { route -> viewModel.onNavigationItemSelected(route) }
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (state.currentRoute) {
                        "home" -> EntrepreneurshipDetailPage(viewModel, state)
                        "products" -> EntrepreneurshipProductsPage(viewModel)
                        "people" -> EntrepreneurshipMembersPage(viewModel)
                        "orders" -> EntrepreneurshipOrdersPage(viewModel)
                        "metrics" -> EntrepreneurshipStatisticsPage(viewModel)
                    }
                }
            }
        }
    }
} 