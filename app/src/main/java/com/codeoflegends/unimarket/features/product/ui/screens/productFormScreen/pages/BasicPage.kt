package com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.ui.components.DropdownMenuBox
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductViewModel

@Composable
fun ProductBasic(viewModel: ProductViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DropdownMenuBox(
            label = "Emprendimiento",
            options = state.businessOptions,
            selectedOption = state.selectedBusiness,
            onOptionSelected = { viewModel.onBusinessSelected(it) },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenuBox(
            label = "Categoría",
            options = state.categoryOptions,
            selectedOption = state.selectedCategory,
            onOptionSelected = { viewModel.onCategorySelected(it) },
            modifier = Modifier.fillMaxWidth()
        )
        SimpleTextField(
            value = state.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = "Nombre del Producto"
        )
        SimpleTextField(
            value = state.description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            label = "Descripción"
        )
        SimpleTextField(
            value = state.price,
            onValueChange = { viewModel.onPriceChanged(it) },
            label = "Precio",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        SimpleTextField(
            value = state.lowStockAlert,
            onValueChange = { viewModel.onLowStockAlertChanged(it) },
            label = "Alerta de Stock Bajo",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = "Serás notificado cuando el stock caiga por debajo de este número",
            style = MaterialTheme.typography.bodySmall
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Publicado", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = state.published,
                onCheckedChange = { viewModel.onPublishedChanged(it) }
            )
        }
    }
}