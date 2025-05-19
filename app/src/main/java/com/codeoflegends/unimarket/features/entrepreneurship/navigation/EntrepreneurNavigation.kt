package com.codeoflegends.unimarket.features.entrepreneurship.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.EntrepreneurshipFormScreen
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.ProductFormScreen

fun NavGraphBuilder.entrepreneurNavigation(
    manager: NavigationManager
) {

    composable(
        Routes.EntrepreneurshipForm.route,
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            }
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")
        EntrepreneurshipFormScreen()
    }


}
