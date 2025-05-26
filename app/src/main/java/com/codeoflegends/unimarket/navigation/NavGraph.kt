package com.codeoflegends.unimarket.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: EntrepreneurshipViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "entrepreneurship_list"
    ) {
        // ... existing routes ...
    }
} 