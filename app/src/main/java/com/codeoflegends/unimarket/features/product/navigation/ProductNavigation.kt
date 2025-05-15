package com.codeoflegends.unimarket.features.product.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.ProductFormScreen

fun NavGraphBuilder.productNavigation(
    manager: NavigationManager
) {
    // Ruta para crear un nuevo producto
    composable(Routes.ProductFormCreate.route) {
        ProductFormScreen(manager = manager)
    }
    
    // Ruta para editar un producto existente con ID
    composable(
        route = Routes.ProductFormEdit.route,
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
