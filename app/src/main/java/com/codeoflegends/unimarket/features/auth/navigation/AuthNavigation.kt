package com.codeoflegends.unimarket.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.features.auth.ui.screens.LoginScreen

fun NavGraphBuilder.authNavigation(
    manager: NavigationManager
) {

    composable(Routes.Login.route) {
    }
}