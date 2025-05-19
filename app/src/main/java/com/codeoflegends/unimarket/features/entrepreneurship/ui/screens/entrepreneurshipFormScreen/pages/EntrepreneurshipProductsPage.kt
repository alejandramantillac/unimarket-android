package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel
import com.codeoflegends.unimarket.features.product.ui.components.ProductItem
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductViewModel
import androidx.compose.runtime.*


@Composable
fun EntrepreneurshipProductsPage(
    viewModel: EntrepreneurshipViewModel,
    manager: NavigationManager,
    productViewModel: ProductViewModel = hiltViewModel()
) {

    val products by productViewModel.products.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        MainButton(
            onClick = { manager.navController.navigate(Routes.ManageProduct.base) },
            modifier = Modifier.fillMaxWidth(),
            text = "Crear Producto"
        )

        // Lista de productos disponibles
        if (products.isNotEmpty()) {
            Text(
                text = "Productos disponibles",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onEditClick = {
                            manager.navController.navigate(
                                Routes.ManageProduct.createRoute(product.id!!.toString())
                            )
                        },
                        onViewClick = {
                            manager.navController.navigate(
                                Routes.ProductView.createRoute(product.id!!.toString())
                            )
                        }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay productos disponibles",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}