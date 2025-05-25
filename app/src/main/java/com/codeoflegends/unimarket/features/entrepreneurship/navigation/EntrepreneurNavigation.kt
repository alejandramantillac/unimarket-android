package com.codeoflegends.unimarket.features.entrepreneurship.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.EntrepreneurshipFormScreen
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.EntrepreneurshipScreen
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages.EntrepreneurshipProfileScreen
//import com.codeoflegends.unimarket.features.profile.ui.screens.EditProfileScreen

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

    composable(Routes.EntrepreneurProfile.route) {
        EntrepreneurshipProfileScreen(manager)
    }

    composable(
        Routes.ManageEntrepreneurship.route,
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val entrepreneurshipId = backStackEntry.arguments?.getString("id")
        EntrepreneurshipScreen(entrepreneurshipId = entrepreneurshipId, manager = manager)
    }


    composable(
        Routes.EditProfile.route,
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val entrepreneurshipId = backStackEntry.arguments?.getString("id")
        //EditProfileScreen(viewModel = hiltViewModel(), entrepreneurshipId = entrepreneurshipId ?: "")
    }
}
