package com.codeoflegends.unimarket.features.product.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.ProductFormScreen

fun NavGraphBuilder.productNavigation() {
    composable(Routes.ProductForm.route) {
        ProductFormScreen()
    }
}
