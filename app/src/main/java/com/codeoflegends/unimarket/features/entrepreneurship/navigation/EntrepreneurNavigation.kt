package com.codeoflegends.unimarket.features.entrepreneurship.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.ProductFormScreen

fun NavGraphBuilder.entrepreneurNavigation(
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
}
