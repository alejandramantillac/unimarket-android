package com.codeoflegends.unimarket.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.secureComposable
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.home.ui.screens.BuyerHomeScreen

fun NavGraphBuilder.homeNavigation(
    manager: NavigationManager
) {
    composable(Routes.Home.route) {
        BuyerHomeScreen(manager, Routes.Home.route)
    }

    secureComposable (
        route = Routes.Home.route,
        manager = manager
    ) {
        BuyerHomeScreen(manager, Routes.Home.route)
    }
}