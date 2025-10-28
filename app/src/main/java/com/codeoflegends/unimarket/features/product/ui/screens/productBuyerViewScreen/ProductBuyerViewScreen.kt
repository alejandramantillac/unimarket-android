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
import com.codeoflegends.unimarket.core.ui.state.MessageManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import java.util.*
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductActionState
import com.codeoflegends.unimarket.features.cart.ui.viewmodel.CartViewModel
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.constant.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductBuyerViewScreen(
    productId: String?,
    manager: NavigationManager
) {
    val viewModel: ProductViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val cartState by cartViewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val context = LocalContext.current

    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadProduct(productId)
            runCatching { UUID.fromString(productId) }
                .onSuccess { uuid ->
                    viewModel.loadProductReviews(uuid, 1, 10)
                }
                .onFailure { e ->
                    MessageManager.showError("ID de producto inválido")
                }
        }
    }

    LaunchedEffect(actionState) {
        when (actionState) {
            is ProductActionState.Success -> {
                MessageManager.showSuccess("Reseña creada exitosamente")
            }
            is ProductActionState.Error -> {
                MessageManager.showError((actionState as ProductActionState.Error).message)
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
                title = { Text(product?.name ?: "Detalles del producto") },
                navigationIcon = {
                    IconButton(onClick = { manager.navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    // Botón del carrito con badge
                    BadgedBox(
                        badge = {
                            if (cartState.cart.totalItems > 0) {
                                Badge { Text(cartState.cart.totalItems.toString()) }
                            }
                        }
                    ) {
                        IconButton(onClick = { manager.navController.navigate(Routes.Cart.route) }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Ver carrito"
                            )
                        }
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
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                    ) {
                                        ProductBuyerDetailPage(product, reviews)
                                        Spacer(modifier = Modifier.height(16.dp))
                                        
                                        // Si el producto tiene variantes, mostrar el botón de añadir al carrito
                                        if (product.variants.isNotEmpty()) {
                                            val firstVariant = product.variants.first()
                                            MainButton(
                                                text = "Añadir al carrito",
                                                onClick = { 
                                                    cartViewModel.addToCart(product, firstVariant, 1)
                                                    MessageManager.showSuccess("Producto añadido al carrito")
                                                },
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        } else {
                                            Text(
                                                text = "Este producto no tiene variantes disponibles",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.padding(vertical = 8.dp)
                                            )
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