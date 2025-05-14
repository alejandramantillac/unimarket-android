package com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.product.viewmodel.ProductViewModel
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.core.ui.state.ToastHandler
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductBasic
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductDetails
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductImages
import com.codeoflegends.unimarket.core.validation.FormField
import com.codeoflegends.unimarket.core.validation.FormState
import com.codeoflegends.unimarket.core.validation.validators.NotEmptyValidator

@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    // Definir los campos del formulario usando tu sistema de validación
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
            is com.codeoflegends.unimarket.features.product.viewmodel.ProductActionState.Success -> {
                ToastHandler.showSuccess("Operación exitosa!!", dismissTimeout = 3f)
            }
            is com.codeoflegends.unimarket.features.product.viewmodel.ProductActionState.Error -> {
                ToastHandler.handleError(
                    message = (actionState as com.codeoflegends.unimarket.features.product.viewmodel.ProductActionState.Error).message,
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
                enabled = isFormValid // Solo habilitado si el formulario es válido
            ) {
                Text("Guardar Cambios")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { viewModel.deleteProduct() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Eliminar Producto", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}