package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.product.ui.components.ProductItem
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipProductsViewModel
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.Filter
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipQueryProductsActionState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipSellerViewModel

@Composable
fun EntrepreneurshipProductsPage(
    entrepreneurshipViewModel: EntrepreneurshipSellerViewModel,
    viewModel: EntrepreneurshipProductsViewModel = hiltViewModel(),
    manager: NavigationManager,
) {
    val entrepreneurshipUiState by entrepreneurshipViewModel.entrepreneurshipUiState.collectAsState()
    val queryActionState by viewModel.queryActionState.collectAsState()

    val filterState by viewModel.filterState.collectAsState()
    val state by viewModel.uiState.collectAsState()

    val focusManager = LocalFocusManager.current
    var tempSearchQuery by remember { mutableStateOf(filterState.searchQuery) }

    LaunchedEffect(entrepreneurshipUiState.id) {
        viewModel.initialize(entrepreneurshipUiState.id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
                tempSearchQuery = filterState.searchQuery
            }
    ) {
        InfiniteScrollList(
            items = state.products,
            onLoadMore = { viewModel.loadMoreProducts(entrepreneurshipUiState.id) },
            isLoading = queryActionState is EntrepreneurshipQueryProductsActionState.Loading,
            itemContent = { product ->
                ProductItem(
                    product = product,
                    onViewClick = {
                        manager.navController.navigate(
                            Routes.ProductView.createRoute(product.id!!.toString())
                        )
                    }
                )
            },
            emptyContent = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (viewModel.hasActiveFilters()) {
                            "No se encontraron productos con los filtros aplicados"
                        } else {
                            "Todavía no tienes productos"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            },
            headerContent = {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Tus productos",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp).padding(top = 50.dp)
                    )

                    Filter(
                        viewModel = viewModel
                    )
                }
            }
        )

        FloatingActionButton(
            onClick = {
                manager.navController.navigate(
                    Routes.CreateProduct.createRoute(entrepreneurshipUiState.id.toString())
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar producto"
            )
        }
    }
}