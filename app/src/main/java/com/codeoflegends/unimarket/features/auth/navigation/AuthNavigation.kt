package com.codeoflegends.unimarket.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.secureComposable
import com.codeoflegends.unimarket.features.auth.ui.screens.ForgotPasswordScreen
import com.codeoflegends.unimarket.features.auth.ui.screens.LoginScreen
import com.codeoflegends.unimarket.features.auth.ui.screens.RegisterScreen
import com.codeoflegends.unimarket.features.auth.ui.screens.RoleSelectionScreen
import com.codeoflegends.unimarket.features.auth.ui.screens.UniversityVerificationScreen

fun NavGraphBuilder.authNavigation(
    manager: NavigationManager
) {

    composable(Routes.Login.route) {
        LoginScreen(manager, Routes.Home.route)
    }

    composable(Routes.Register.route) {
        // Todo: Agregar la ruta de confirmacion de contraseña
        RegisterScreen(manager)
    }

    composable(Routes.ForgotPassword.route) {
        ForgotPasswordScreen(manager)
    }

    secureComposable(
        manager = manager,
        route = Routes.RoleSelection.route
    ) {
        RoleSelectionScreen(manager)
    }

    secureComposable(
        manager = manager,
        route = Routes.UniversityVerification.route
    ) {
        UniversityVerificationScreen(manager)
    }
}