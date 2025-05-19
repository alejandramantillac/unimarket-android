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
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductActionState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ProductHeader
import com.codeoflegends.unimarket.core.ui.state.ToastHandler
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SecondaryButton

/**
 * Screen for viewing product details
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductViewScreen(
    productId: String?,
    viewModel: ProductViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()
    
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadProduct(productId)
        }
    }

    LaunchedEffect(actionState) {
        when (actionState) {
            is ProductActionState.Error -> {
                ToastHandler.handleError(
                    message = (actionState as ProductActionState.Error).message
                )
            }
            is ProductActionState.Success -> {
                ToastHandler.showSuccess("Operación exitosa")
                manager?.navController?.popBackStack()
            }
            else -> {}
        }
    }
    
    // Delete confirmation dialog
    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar este producto?") },
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MainButton(
                        text = "Eliminar",
                        onClick = {
                            state.selectedProduct?.id?.let { id ->
                                viewModel.deleteProduct(id)
                            }
                            showDeleteConfirmation = false
                        },
                        modifier = Modifier.weight(1f)
                    )
                    SecondaryButton(
                        text = "Cancelar",
                        onClick = { showDeleteConfirmation = false },
                        modifier = Modifier.weight(1f)
                    )
                }
            },
            dismissButton = {},
        )
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
                    IconButton(onClick = { showDeleteConfirmation = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete, 
                            contentDescription = "Eliminar"
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
                                reviewCount = product.reviews?.size ?: 0,
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

