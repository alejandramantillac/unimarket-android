package com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductViewModel
import java.util.UUID
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField

@Composable
fun ProductSpecifications(viewModel: ProductViewModel) {
    val state by viewModel.uiState.collectAsState()
    val formData = state.formData
    
    var showDialog by remember { mutableStateOf(false) }
    var editingSpec by remember { mutableStateOf<ProductSpecification?>(null) }
    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    fun resetFields() {
        key = ""
        value = ""
        editingSpec = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text(
                text = "Detalles",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    resetFields()
                    showDialog = true
                },
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Especificación", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }

        // Lista de especificaciones existentes
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (formData.specifications.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Agrega al menos una especificación",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                formData.specifications.forEach { spec ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SimpleTextField(
                            value = spec.key,
                            onValueChange = {},
                            label = "Característica",
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SimpleTextField(
                            value = spec.value,
                            onValueChange = {},
                            label = "Detalle",
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            editingSpec = spec
                            key = spec.key
                            value = spec.value
                            showDialog = true
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Editar")
                        }
                        IconButton(onClick = { viewModel.removeSpecification(spec.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }

    // Diálogo para agregar/editar especificación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                resetFields()
            },
            confirmButton = {
                Button(
                    onClick = {
                        val spec = ProductSpecification(
                            id = editingSpec?.id ?: UUID.randomUUID(),
                            key = key,
                            value = value,
                        )
                        if (editingSpec == null) {
                            viewModel.addSpecification(spec)
                        } else {
                            viewModel.updateSpecification(spec)
                        }
                        showDialog = false
                        resetFields()
                    },
                    enabled = key.isNotBlank() && value.isNotBlank()
                ) {
                    Text(if (editingSpec == null) "Agregar" else "Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showDialog = false
                    resetFields()
                }) {
                    Text("Cancelar")
                }
            },
            title = {
                Text(if (editingSpec == null) "Nueva Especificación" else "Editar Especificación")
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SimpleTextField(
                        value = key,
                        onValueChange = { key = it },
                        label = "Característica"
                    )
                    SimpleTextField(
                        value = value,
                        onValueChange = { value = it },
                        label = "Detalle"
                    )
                }
            },
            shape = MaterialTheme.shapes.large
        )
    }
}