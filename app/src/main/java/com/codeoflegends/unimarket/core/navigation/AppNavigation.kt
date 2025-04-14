package com.codeoflegends.unimarket.core.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.secureComposable
import com.codeoflegends.unimarket.features.auth.navigation.authNavigation
import com.codeoflegends.unimarket.features.auth.ui.viewModel.AuthViewModel
import com.codeoflegends.unimarket.features.auth.ui.components.RequirePermission

@Composable
fun AppNavigation(
    manager: NavigationManager,
    startDestination: String,
) {
    NavHost(
        navController = manager.navController,
        startDestination = startDestination
    ) {

        // Auth routes
        authNavigation(manager)
    }
}

class NavigationManager(
    val navController: NavHostController,
    val authViewModel: AuthViewModel
)