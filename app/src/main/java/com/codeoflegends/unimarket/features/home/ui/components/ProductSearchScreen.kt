package com.codeoflegends.unimarket.features.home.ui.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.home.ui.viewModel.ProductSectionViewModel
import com.codeoflegends.unimarket.core.ui.components.Filter
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.features.home.ui.viewModel.state.HomeActionState

@Composable
fun ProductSearchScreen(manager: NavigationManager, viewModel: ProductSectionViewModel = hiltViewModel()) {

    val state by viewModel.uiState.collectAsState()
    val page by viewModel.actionState.collectAsState()

    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            showBackButton = true,
            onBackClick = {
                manager.navController.navigateUp()
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(top = 60.dp)
        ) {
            Filter(viewModel = viewModel)
            InfiniteScrollList(
                items = state.products,
                isLoading = page is HomeActionState.Loading,
                onLoadMore = { viewModel.loadMoreProducts() },
                itemContent = { product ->
                    ProductCard(product, orientation = Orientation.Vertical, onClick = {
                        manager.navController.navigate(
                            Routes.ProductBuyerView.createRoute(product.id.toString())
                        )
                    })
                }
            )
        }
    }
} 