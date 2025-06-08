package com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductViewModel
import com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen.pages.ProductBuyerDetailPage
import com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen.pages.ProductBuyerReviewsPage
import com.codeoflegends.unimarket.features.product.ui.screens.productBuyerViewScreen.pages.ProductBuyerReviewFormPage
import com.codeoflegends.unimarket.core.ui.state.ToastHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import java.util.*
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductActionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductBuyerViewScreen(
    productId: String?,
    manager: NavigationManager
) {
    val viewModel: ProductViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val context = LocalContext.current

    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadProduct(productId)
            viewModel.loadProductReviews(UUID.fromString(productId), 1, 10)
        }
    }

    LaunchedEffect(actionState) {
        when (actionState) {
            is ProductActionState.Success -> {
                Toast.makeText(context, "Reseña creada exitosamente", Toast.LENGTH_SHORT).show()
            }
            is ProductActionState.Error -> {
                Toast.makeText(context, (actionState as ProductActionState.Error).message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    val product = state.selectedProduct
    val entrepreneurship = product?.entrepreneurship
    val reviews = state.reviews

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: "Producto") },
                navigationIcon = {
                    IconButton(onClick = { manager.navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
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
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                product == null -> {
                    Text("No se encontró el producto", modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        TabSelector(
                            tabs = listOf(
                                Tab("Detalles") {
                                    Column {
                                        ProductBuyerDetailPage(product, reviews)
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Button(
                                            onClick = { /* TODO: Acción de añadir al carrito */ },
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text("Añadir al carrito")
                                        }
                                    }
                                },
                                Tab("Reseñas") { ProductBuyerReviewsPage(reviews, onRateClick = { viewModel.showRatingModal() }) }
                            ),
                            selectedTabIndex = selectedTab,
                            onTabSelected = { selectedTab = it }
                        )
                    }
                }
            }
        }
    }

    if (state.showRatingModal) {
        AlertDialog(
            onDismissRequest = { viewModel.hideRatingModal() },
            title = { Text("Calificar Producto") },
            text = {
                ProductBuyerReviewFormPage { rating, comment ->
                    viewModel.rateProduct(rating, comment)
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
} 