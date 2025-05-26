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
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicViewModel

@Composable
fun EntrepreneurshipScreen(
    entrepreneurshipId: String? = null,
    viewModel: EntrepreneurshipBasicViewModel = hiltViewModel(),
    manager: NavigationManager
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    LaunchedEffect(entrepreneurshipId) {
        viewModel.loadEntrepreneurship(entrepreneurshipId)
    }

    /*
    TODO: Gestionar el estado de la acción que no sea success
    LaunchedEffect(actionState) {
        when (actionState) {
            is EntrepreneurshipBasicActionState.Error -> {
                // Mostrar error
            }
            is EntrepreneurshipBasicActionState.Success -> {
                // Navegar de vuelta
                manager?.navController?.popBackStack()
            }
            else -> {}
        }
    }
     */

    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            showBackButton = true,
            onBackClick = { manager?.navController?.popBackStack() }
        )
    ) {
        if (actionState is EntrepreneurshipBasicActionState.Loading) {
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
                        "home" -> EntrepreneurshipDetailPage(basicState = state)
                        "products" -> EntrepreneurshipProductsPage(basicState = state, manager = manager)
                        "people" -> EntrepreneurshipMembersPage(basicState = state)
                        "orders" -> EntrepreneurshipOrdersPage(basicState = state)
                        //"metrics" -> EntrepreneurshipStatisticsPage(basicState = state)
                    }
                }
            }
        }
    }
} 