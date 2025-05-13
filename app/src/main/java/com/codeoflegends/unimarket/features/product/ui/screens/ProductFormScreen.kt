package com.codeoflegends.unimarket.features.product.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.product.viewmodel.ProductViewModel
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.core.ui.components.DropdownMenuBox
import com.codeoflegends.unimarket.core.ui.state.ToastHandler

@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

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
            .padding(16.dp)
    ) {
        // Tabs
        TabSelector(
            tabs = listOf("Básico", "Imágenes", "Detalles"),
            selectedTabIndex = state.selectedTab,
            onTabSelected = { viewModel.onTabSelected(it) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (state.selectedTab == 0) {
            Row(modifier = Modifier.fillMaxWidth()) {
                DropdownMenuBox(
                    label = "Emprendimiento",
                    options = state.businessOptions,
                    selectedOption = state.selectedBusiness,
                    onOptionSelected = { viewModel.onBusinessSelected(it) },
                    modifier = Modifier.weight(0.7f)
                )
                Spacer(modifier = Modifier.width(14.dp))
                DropdownMenuBox(
                    label = "Categoría",
                    options = state.categoryOptions,
                    selectedOption = state.selectedCategory,
                    onOptionSelected = { viewModel.onCategorySelected(it) },
                    modifier = Modifier.weight(0.7f)
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            SimpleTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = "Nombre del Producto",
            )
            Spacer(modifier = Modifier.height(14.dp))
            SimpleTextField(
                value = state.description,
                onValueChange = { viewModel.onDescriptionChanged(it) },
                label = "Descripción"
            )
            Spacer(modifier = Modifier.height(14.dp))
            SimpleTextField(
                value = state.sku,
                onValueChange = { viewModel.onSkuChanged(it) },
                label = "SKU (Código del Producto)"
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                SimpleTextField(
                    value = state.price,
                    onValueChange = { viewModel.onPriceChanged(it) },
                    label = "Precio",
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                Spacer(modifier = Modifier.width(14.dp))
                SimpleTextField(
                    value = state.quantity,
                    onValueChange = { viewModel.onQuantityChanged(it) },
                    label = "Cantidad",
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            SimpleTextField(
                value = state.lowStockAlert,
                onValueChange = { viewModel.onLowStockAlertChanged(it) },
                label = "Alerta de Stock Bajo",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Serás notificado cuando el stock caiga por debajo de este número",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Publicado", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = state.published,
                    onCheckedChange = { viewModel.onPublishedChanged(it) }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.saveProduct() },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Guardar Cambios")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { viewModel.deleteProduct() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Eliminar Producto", color = MaterialTheme.colorScheme.primary)
        }
    }
}