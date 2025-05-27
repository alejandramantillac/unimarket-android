package com.codeoflegends.unimarket.features.order.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.order.ui.screens.orderListScreen.OrderListScreen
import com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.OrderSummaryScreen

fun NavGraphBuilder.orderNavigation(
    manager: NavigationManager
) {
    // Ruta para OrderListScreen
    composable(
        route = "orderList"
    ) {
        OrderListScreen(manager = manager)
    }

    // Ruta para OrderSummaryScreen
    composable(
        route = Routes.OrderSummary.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val orderId = backStackEntry.arguments?.getString("id")
        OrderSummaryScreen(
            orderId = orderId,
            manager = manager
        )
    }

    // Ruta para ManageOrder
    composable(
        route = Routes.ManageOrder.route
    ) {
        // Pantalla que muestra la lista de órdenes
        OrderListScreen(manager = manager)
    }
}