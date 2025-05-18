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
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductVariants
import com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages.ProductSpecifications
import com.codeoflegends.unimarket.core.validation.FormField
import com.codeoflegends.unimarket.core.validation.FormState
import com.codeoflegends.unimarket.core.validation.validators.NotEmptyValidator
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductActionState
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.validation.validators.NotNullValidator

@Composable
fun ProductFormScreen(
    productId: String? = null,
    viewModel: ProductViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {
    val state by viewModel.uiState.collectAsState()
    val formData = state.formData
    val isEdit = state.uiState.isEdit
    val formValid = state.uiState.isFormValid
    val selectedTab = state.uiState.selectedTab
    val actionState by viewModel.actionState.collectAsState()

    // Cargar el producto si estamos en modo edición
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    // Build validation form state
    val formState = remember(formData, isEdit) {
        FormState(
            fields = buildMap {
                // Only validate selectedBusiness in create mode
                if (!isEdit) {
                    put("business", FormField(formData.selectedBusiness, listOf(NotNullValidator("Selecciona un emprendimiento"))))
                }
                
                put("category", FormField(formData.selectedCategory, listOf(NotNullValidator("Selecciona una categoría"))))
                put("name", FormField(formData.name, listOf(NotEmptyValidator("El nombre es obligatorio"))))
                put("description", FormField(formData.description, listOf(NotEmptyValidator("La descripción es obligatoria"))))
                put("price", FormField(formData.price, listOf(NotEmptyValidator("El precio es obligatorio"))))
                put("lowStockAlert", FormField(formData.lowStockAlert, listOf(NotEmptyValidator("La alerta de stock es obligatoria"))))
            }
        )
    }
    val isFormFieldsValid = remember(formData) { formState.validateAll() }

    val canSaveProduct = formValid && 
                        formData.variants.isNotEmpty() && 
                        formData.specifications.isNotEmpty()

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
            Button(
                onClick = { viewModel.saveProduct() },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                enabled = canSaveProduct
            ) {
                Text(if (isEdit) "Guardar Cambios" else "Crear Producto")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedButton(
                onClick = { 
                    if (isEdit) {
                        viewModel.deleteProduct()
                    } else {
                        manager?.navController?.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (isEdit) "Eliminar Producto" else "Cancelar"
                )
            }
        }
    }
}