package com.codeoflegends.unimarket.features.product.ui.screens.productFormScreen.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.ui.viewModel.ProductFormViewModel
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.draw.clip
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SecondaryButton
import com.codeoflegends.unimarket.features.product.data.model.VariantImage

@Composable
fun ProductVariants(viewModel: ProductFormViewModel) {
    val state by viewModel.uiState.collectAsState()
    val formData = state.formData
    
    var showDialog by remember { mutableStateOf(false) }
    var editingVariant by remember { mutableStateOf<ProductVariant?>(null) }

    // Campos para nueva variante
    var name by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var images by remember { mutableStateOf<List<VariantImage>>(emptyList()) }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            images = images + VariantImage(imageUrl=it.toString())
        }
    }

    fun resetFields() {
        name = ""
        stock = ""
        images = emptyList()
        editingVariant = null
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
                text = "Variantes",
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
                    contentDescription = "Agregar Variante",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de variantes existentes
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (formData.variants.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Agrega al menos una variante",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                formData.variants.forEach { variant ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Imagen principal de la variante (si existe)
                            if (variant.variantImages.isNotEmpty()) {
                                Image(
                                    painter = rememberAsyncImagePainter(variant.variantImages.first().imageUrl.toUri()),
                                    contentDescription = "Imagen variante",
                                    modifier = Modifier.size(64.dp),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(Color.LightGray, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Sin imagen", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(variant.name, style = MaterialTheme.typography.titleMedium)
                                Text("Stock: ${variant.stock}", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = {
                                editingVariant = variant
                                name = variant.name
                                stock = variant.stock.toString()
                                images = variant.variantImages
                                showDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = { viewModel.removeVariant(variant.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo para agregar/editar variante
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                resetFields()
            },
            confirmButton = {
                MainButton(
                    text = if (editingVariant == null) "Agregar" else "Guardar",
                    onClick = {
                        val variant = ProductVariant(
                            id = editingVariant?.id,
                            name = name,
                            stock = stock.toIntOrNull() ?: 0,
                            variantImages = images
                        )
                        if (editingVariant == null) {
                            viewModel.addVariant(variant)
                        } else {
                            viewModel.updateVariant(variant)
                        }
                        showDialog = false
                        resetFields()
                    },
                    enabled = name.isNotBlank() && stock.isNotBlank()
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
                Text(if (editingVariant == null) "Nueva Variante" else "Editar Variante")
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SimpleTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Nombre de la variante"
                    )
                    SimpleTextField(
                        value = stock,
                        onValueChange = { stock = it.filter { c -> c.isDigit() } },
                        label = "Stock",
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                            Text("Seleccionar Imagen")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        if (images.isNotEmpty()) {
                            Text("${images.size} imagen(es) seleccionada(s)", style = MaterialTheme.typography.bodySmall)
                        } else {
                            Text("Opcional", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    }
                    // Previsualización de imágenes seleccionadas
                    if (images.isNotEmpty()) {
                        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                            images.forEach { image ->
                                Image(
                                    painter = rememberAsyncImagePainter(image.imageUrl.toUri()),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .padding(2.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
} 