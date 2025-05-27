package com.codeoflegends.unimarket.features.product.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.ProductFormScreen
import com.codeoflegends.unimarket.features.product.ui.screens.productViewScreen.ProductViewScreen
import androidx.hilt.navigation.compose.hiltViewModel

fun NavGraphBuilder.productNavigation(
    manager: NavigationManager
) {
    composable(Routes.ManageProduct.route,
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("id")
        ProductFormScreen(productId = productId, manager = manager)
    }
    
    composable(
        route = Routes.ProductView.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("id")
        ProductViewScreen(
            productId = productId,
            manager = manager
        )
    }
}
