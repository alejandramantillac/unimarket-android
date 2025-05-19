package com.codeoflegends.unimarket.features.entrepreneurship.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.EntrepreneurshipScreen

fun NavGraphBuilder.entrepreneurshipNavigation(
    manager: NavigationManager
) {
    composable(Routes.ManageEntrepreneurship.route,
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val entrepreneurshipId = backStackEntry.arguments?.getString("id")
        EntrepreneurshipScreen(entrepreneurshipId = entrepreneurshipId, manager = manager)
    }
} 