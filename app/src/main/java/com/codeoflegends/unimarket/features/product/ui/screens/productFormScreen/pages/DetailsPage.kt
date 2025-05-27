package com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductFormViewModel
import java.util.UUID
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SecondaryButton

@Composable
fun ProductSpecifications(viewModel: ProductFormViewModel) {
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
                    .size(32.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar Especificación",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
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
                        Text(
                            text = spec.key,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            editingSpec = spec
                            key = spec.key
                            value = spec.value
                            showDialog = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
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
                MainButton(
                    text = if (editingSpec == null) "Agregar" else "Guardar",
                    onClick = {
                        val spec = ProductSpecification(
                            id = editingSpec?.id,
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
                )
            },
            dismissButton = {
                SecondaryButton(
                    text = "Cancelar",
                    onClick = {
                        showDialog = false
                        resetFields()
                    }
                )
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