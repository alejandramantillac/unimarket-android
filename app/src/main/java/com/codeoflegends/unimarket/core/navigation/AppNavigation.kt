package com.codeoflegends.unimarket.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeoflegends.unimarket.features.auth.navigation.authNavigation
import com.codeoflegends.unimarket.features.auth.ui.viewModel.AuthViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.navigation.entrepreneurshipNavigation
import com.codeoflegends.unimarket.features.entrepreneurship.navigation.entrepreneurNavigation
import com.codeoflegends.unimarket.features.home.navigation.homeNavigation
import com.codeoflegends.unimarket.features.product.navigation.productNavigation

@Composable
fun AppNavigation(
    manager: NavigationManager,
    startDestination: String,
) {
    NavHost(
        navController = manager.navController,
        startDestination = startDestination
    ) {
        authNavigation(manager)
        homeNavigation(manager)
        productNavigation(manager)
        entrepreneurshipNavigation(manager)
        entrepreneurNavigation(manager)
   }
}

class NavigationManager(
    val navController: NavHostController,
    val authViewModel: AuthViewModel
)