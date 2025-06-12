package com.codeoflegends.unimarket.features.order.navigation

import BuyerOrderHistoryScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.order.ui.screens.buyerOrderSummaryScreen.BuyerOrderSummaryScreen
import com.codeoflegends.unimarket.features.order.ui.screens.orderListScreen.OrderListScreen
import com.codeoflegends.unimarket.features.order.ui.screens.orderSummaryScreen.OrderSummaryScreen
import com.codeoflegends.unimarket.features.order.ui.viewModel.BuyerOrderSummaryViewModel

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

    // Ruta para ManageHistory
    composable(
        route = Routes.ManageHistory.route
    ) {
        // Pantalla que muestra la lista de pedidos del comprador
        BuyerOrderHistoryScreen(manager = manager)
    }

    //Ruta para BuyerOrderSummaryScreen
    composable(
        route = Routes.OrderHistory.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val orderId = backStackEntry.arguments?.getString("id")
        BuyerOrderSummaryScreen(
            orderId = orderId,
        )
    }
}

