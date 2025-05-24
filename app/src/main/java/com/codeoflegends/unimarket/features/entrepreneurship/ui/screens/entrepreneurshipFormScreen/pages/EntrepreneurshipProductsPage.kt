package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.product.ui.components.ProductItem
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductViewModel
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipProductsActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipProductsViewModel
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.tooling.preview.Preview
import com.codeoflegends.unimarket.core.ui.components.Filter

@Composable
fun EntrepreneurshipProductsPage(
    viewModel: EntrepreneurshipProductsViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel(),
    basicState: EntrepreneurshipBasicUiState
) {
    val actionState by viewModel.actionState.collectAsState()
    val state by viewModel.uiState.collectAsState()

    val focusManager = LocalFocusManager.current
    var tempSearchQuery by remember { mutableStateOf(state.searchQuery) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
                tempSearchQuery = state.searchQuery
            }
    ) {
        InfiniteScrollList(
            items = state.products,
            onLoadMore = { viewModel.loadMoreProducts(basicState.id) },
            isLoading = actionState is EntrepreneurshipProductsActionState.Loading,
            itemContent = { product ->
                ProductItem(
                    product = product,
                    onEditClick = {},
                    onViewClick = {}
                )
            },
            modifier = Modifier
                .padding(top = 56.dp),
            headerContent = {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    /* Título
                    Text(
                        text = "Productos",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(16.dp)
                    )

                     */

                    Filter(
                        viewModel = viewModel
                    )
                }
            }
        )
    }
}