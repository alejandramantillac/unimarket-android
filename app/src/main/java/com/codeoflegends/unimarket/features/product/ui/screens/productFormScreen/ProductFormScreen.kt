package com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen

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
import com.codeoflegends.unimarket.core.ui.state.ToastHandler
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductBasic
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductDetails
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductVariants
import com.codeoflegends.unimarket.core.validation.FormField
import com.codeoflegends.unimarket.core.validation.FormState
import com.codeoflegends.unimarket.core.validation.validators.NotEmptyValidator
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductActionState
import com.codeoflegends.unimarket.core.navigation.NavigationManager

@Composable
fun ProductFormScreen(
    productId: String? = null,
    viewModel: ProductViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    // Cargar el producto si estamos en modo edición
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    val formState = remember(state) {
        FormState(
            fields = mapOf(
                "business" to FormField(state.selectedBusiness ?: "", listOf(NotEmptyValidator("Selecciona un emprendimiento"))),
                "category" to FormField(state.selectedCategory ?: "", listOf(NotEmptyValidator("Selecciona una categoría"))),
                "name" to FormField(state.name, listOf(NotEmptyValidator("El nombre es obligatorio"))),
                "description" to FormField(state.description, listOf(NotEmptyValidator("La descripción es obligatoria"))),
                "price" to FormField(state.price, listOf(NotEmptyValidator("El precio es obligatorio"))),
                "lowStockAlert" to FormField(state.lowStockAlert, listOf(NotEmptyValidator("La alerta de stock es obligatoria")))
            )
        )
    }
    val isFormValid = remember(state) { formState.validateAll() }

    val canSaveProduct = isFormValid && state.variants.isNotEmpty()

    LaunchedEffect(actionState) {
        when (actionState) {
            is ProductActionState.Success -> {
                ToastHandler.showSuccess("Operación exitosa!!", dismissTimeout = 3f)
                manager?.navController?.popBackStack()
            }
            is ProductActionState.Error -> {
                ToastHandler.handleError(
                    message = (actionState as ProductActionState.Error).message,
                )
            }
            else -> {}
        }
    }

    // Mostrar indicador de carga durante la carga inicial de datos
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
            text = if (state.isEdit) "Editar Producto" else "Crear Producto",
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
                    Tab("Detalles") { ProductDetails(viewModel) }),
                selectedTabIndex = state.selectedTab,
                onTabSelected = { viewModel.onTabSelected(it) },
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { viewModel.saveProduct() },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                enabled = canSaveProduct
            ) {
                Text(if (state.isEdit) "Guardar Cambios" else "Crear Producto")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = { 
                    if (state.isEdit) {
                        viewModel.deleteProduct()
                    } else {
                        manager?.navController?.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (state.isEdit) "Eliminar Producto" else "Cancelar"
                )
            }
        }
    }
}