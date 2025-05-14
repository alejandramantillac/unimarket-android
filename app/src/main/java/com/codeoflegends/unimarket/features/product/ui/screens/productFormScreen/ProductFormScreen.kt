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
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductImages
import com.codeoflegends.unimarket.core.validation.FormField
import com.codeoflegends.unimarket.core.validation.FormState
import com.codeoflegends.unimarket.core.validation.validators.NotEmptyValidator
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductActionState

@Composable
fun ProductFormScreen(
    productId: String? = null,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    // Cargar el producto si estamos en modo edición
    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadProduct(productId)
        }
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

    LaunchedEffect(actionState) {
        when (actionState) {
            is ProductActionState.Success -> {
                ToastHandler.showSuccess("Operación exitosa!!", dismissTimeout = 3f)
            }
            is ProductActionState.Error -> {
                ToastHandler.handleError(
                    message = (actionState as ProductActionState.Error).message,
                )
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Tabs
        TabSelector(
            tabs = listOf(
                Tab("Básico") { ProductBasic(viewModel) },
                Tab("Imágenes") { ProductImages(viewModel) },
                Tab("Detalles") { ProductDetails(viewModel) }),
            selectedTabIndex = state.selectedTab,
            onTabSelected = { viewModel.onTabSelected(it) },
        )

        Column {
            Button(
                onClick = { viewModel.saveProduct() },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                enabled = isFormValid
            ) {
                Text(if (state.isEdit) "Guardar Cambios" else "Crear Producto")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = { 
                    if (state.isEdit) {
                        viewModel.deleteProduct()
                    } else {
                        viewModel.cancelOperation()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    if (state.isEdit) "Eliminar Producto" else "Cancelar",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}