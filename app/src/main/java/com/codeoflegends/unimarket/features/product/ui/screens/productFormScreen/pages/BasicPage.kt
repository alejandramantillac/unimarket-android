package com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductFormViewModel

@Composable
fun ProductBasic(viewModel: ProductFormViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val formData = state.formData
    val formOptions = state.formOptions
    val isEdit = state.uiState.isEdit
    
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp), // Espacio extra al final para evitar que el último elemento se oculte
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            DropdownMenuBox(
                label = "Categoría",
                options = formOptions.categoryOptions.map { it.name },
                selectedOption = formData.selectedCategory?.name,
                onOptionSelected = { viewModel.onCategorySelected(it) },
                modifier = Modifier.fillMaxWidth()
            )
            
            SimpleTextField(
                value = formData.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = "Nombre del Producto"
            )
            
            SimpleTextField(
                value = formData.description,
                onValueChange = { viewModel.onDescriptionChanged(it) },
                label = "Descripción"
            )
            
            SimpleTextField(
                value = formData.price,
                onValueChange = { viewModel.onPriceChanged(it) },
                label = "Precio",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            
            SimpleTextField(
                value = formData.lowStockAlert,
                onValueChange = { viewModel.onLowStockAlertChanged(it) },
                label = "Alerta de Stock Bajo",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            
            Text(
                text = "Serás notificado cuando el stock caiga por debajo de este número",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        "Publicado", 
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Switch(
                        checked = formData.published,
                        onCheckedChange = { viewModel.onPublishedChanged(it) }
                    )
                }
            }
        }
    }
}