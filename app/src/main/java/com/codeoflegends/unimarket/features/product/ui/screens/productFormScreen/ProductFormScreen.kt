package com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductFormViewModel
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.core.ui.state.ToastHandler
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductBasic
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductVariants
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductSpecifications
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductActionState
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.product.ui.viewModel.validation.ProductValidation
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SecondaryButton

/**
 * Screen for creating or editing a product
 */
@Composable
fun ProductFormScreen(
    productId: String? = null,
    viewModel: ProductFormViewModel = hiltViewModel(),
    manager: NavigationManager
) {
    val state by viewModel.uiState.collectAsState()
    val formData = state.formData
    val isEdit = state.uiState.isEdit
    val selectedTab = state.uiState.selectedTab
    val actionState by viewModel.actionState.collectAsState()

    // Load the product if we're in edit mode
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    // Get the form validity from the validation system
    val canSaveProduct = remember(formData, isEdit) { 
        ProductValidation.validateForm(formData, isEdit)
    }

    LaunchedEffect(actionState) {
        when (actionState) {
            is ProductActionState.Success -> {
                ToastHandler.showSuccess("Operación exitosa!!", dismissTimeout = 3f)
                manager.navController.popBackStack()
            }
            is ProductActionState.Error -> {
                ToastHandler.handleError(
                    message = (actionState as ProductActionState.Error).message,
                )
            }
            else -> {}
        }
    }

    // Show loading indicator during initial data loading
    if (actionState is ProductActionState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (isEdit) "Editar Producto" else "Crear Producto",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        ) {
            TabSelector(
                tabs = listOf(
                    Tab("Básico") { ProductBasic(viewModel) },
                    Tab("Variantes") { ProductVariants(viewModel) },
                    Tab("Detalles") { ProductSpecifications(viewModel) }),
                selectedTabIndex = selectedTab,
                onTabSelected = { viewModel.onTabSelected(it) },
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainButton(
                text = if (isEdit) "Guardar Cambios" else "Crear Producto",
                onClick = { viewModel.saveProduct() },
                enabled = canSaveProduct
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            SecondaryButton(
                text = if (isEdit) "Eliminar Producto" else "Cancelar",
                onClick = { 
                    if (isEdit) {
                        viewModel.deleteProduct()
                    } else {
                        manager.navController.popBackStack()
                    }
                }
            )
        }
    }
}