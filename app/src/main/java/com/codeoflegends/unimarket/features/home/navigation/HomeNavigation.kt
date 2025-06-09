package com.codeoflegends.unimarket.features.home.navigation

import androidx.navigation.NavGraphBuilder
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.secureComposable
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.home.ui.screens.homeScreen.HomeScreen
import com.codeoflegends.unimarket.features.home.ui.components.ProductSearchScreen
import com.codeoflegends.unimarket.features.product.navigation.productNavigation

fun NavGraphBuilder.homeNavigation(
    manager: NavigationManager
) {
    secureComposable (
        route = Routes.Home.route,
        manager = manager
    ) {
        HomeScreen(manager)
    }

    secureComposable (
        route = Routes.HomeSearch.route,
        manager = manager
    ) {
        ProductSearchScreen(manager)
    }

    productNavigation(manager)
}