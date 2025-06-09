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
import com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen.ProductBuyerViewScreen
import com.codeoflegends.unimarket.features.cart.ui.screens.CartScreen
import com.codeoflegends.unimarket.features.cart.ui.viewmodel.CartViewModel

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

    composable(
        route = Routes.ProductBuyerView.route,
        arguments = listOf(
            navArgument("id") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("id")
        ProductBuyerViewScreen(
            productId = productId,
            manager = manager
        )
    }

    composable(route = Routes.Cart.route) {
        val cartViewModel: CartViewModel = hiltViewModel()
        CartScreen(
            cartViewModel = cartViewModel,
            onOrderCreated = { orderId ->
                manager.navController.navigate(Routes.OrderSummary.createRoute(orderId.toString()))
            },
            onBackClick = { manager.navController.popBackStack() }
        )
    }
}
