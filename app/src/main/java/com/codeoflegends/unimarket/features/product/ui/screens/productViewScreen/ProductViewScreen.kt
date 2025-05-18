package com.codeoflegends.unimarket.features.product.ui.screens.productViewScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductViewModel
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.features.product.ui.screens.productViewScreen.pages.ProductDetailPage
import com.codeoflegends.unimarket.features.product.ui.screens.productViewScreen.pages.ProductInventoryPage
import com.codeoflegends.unimarket.features.product.ui.screens.productViewScreen.pages.ProductSalesPage
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductActionState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ProductHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductViewScreen(
    productId: String?,
    viewModel: ProductViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadProduct(productId)
        }
    }

    LaunchedEffect(actionState) {
        when (actionState) {
            is ProductActionState.Error -> {
                // Mostrar error
            }
            is ProductActionState.Success -> {
                // Navegar de vuelta
                manager?.navController?.popBackStack()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.selectedProduct?.name ?: "Producto") },
                navigationIcon = {
                    IconButton(onClick = { manager?.navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { 
                            state.selectedProduct?.id?.let { id ->
                                manager?.navController?.navigate(
                                    Routes.ManageProduct.createRoute(id.toString())
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                actionState is ProductActionState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.selectedProduct == null -> {
                    Text(
                        text = "No se encontró el producto",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Mostrar ProductHeader solo si hay producto
                        state.selectedProduct?.let { product ->
                            ProductHeader(
                                product = product,
                                variantImages = product.variants.flatMap { it.variantImages },
                                reviewCount = product.reviews.size,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )
                        }
                        TabSelector(
                            tabs = listOf(
                                Tab("Detalles") { ProductDetailPage(viewModel) },
                                Tab("Inventario") { ProductInventoryPage(viewModel) },
                                Tab("Ventas") { ProductSalesPage(viewModel) }
                            ),
                            selectedTabIndex = state.uiState.selectedTab,
                            onTabSelected = { viewModel.onTabSelected(it) }
                        )
                    }
                }
            }
        }
    }
}

