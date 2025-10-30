package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.ImageUploadCard
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicFormViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicFormActionState
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrepreneurshipFormScreen(
    viewModel: EntrepreneurshipBasicFormViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {
    val state by viewModel.state.collectAsState()
    val actionState by viewModel.actionState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(actionState) {
        when (actionState) {
            is EntrepreneurshipBasicFormActionState.Success -> {
                manager?.navController?.popBackStack()
            }
            is EntrepreneurshipBasicFormActionState.Error -> {
                // TODO: Mostrar error
            }
            else -> {}
        }
    }

    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            showBackButton = true,
            title = "Nuevo Emprendimiento",
            onBackClick = { manager?.navController?.popBackStack() }
        ),
        pageLoading = actionState is EntrepreneurshipBasicFormActionState.Loading,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(vertical = 52.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Nombre del emprendimiento
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { viewModel.updateName(it) },
                    label = { Text("Nombre del emprendimiento") },
                    leadingIcon = { Icon(Icons.Default.Store, contentDescription = null) },
                    supportingText = { Text("${state.name.length}/50") },
                    isError = state.name.length > 50,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Slogan
                OutlinedTextField(
                    value = state.slogan,
                    onValueChange = { viewModel.updateSlogan(it) },
                    label = { Text("Slogan") },
                    leadingIcon = { Icon(Icons.Default.Tag, contentDescription = null) },
                    supportingText = { Text("${state.slogan.length}/100") },
                    isError = state.slogan.length > 100,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Descripción
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { viewModel.updateDescription(it) },
                    label = { Text("Descripción") },
                    leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                    supportingText = { Text("${state.description.length}/500") },
                    isError = state.description.length > 500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5
                )

                // Correo electrónico
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = { Text("Correo electrónico") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    isError = state.email.isNotEmpty() && !state.email.contains("@"),
                    supportingText = {
                        if (state.email.isNotEmpty() && !state.email.contains("@")) {
                            Text("Ingrese un correo electrónico válido")
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Teléfono
                OutlinedTextField(
                    value = state.phone,
                    onValueChange = { viewModel.updatePhone(it) },
                    label = { Text("Teléfono de contacto") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    supportingText = { Text("Formato: +XX XXX XXX XXXX") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Categoría con búsqueda
                ExposedDropdownMenuBox(
                    expanded = state.isCategoryExpanded,
                    onExpandedChange = { viewModel.toggleCategoryExpanded() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            viewModel.toggleCategoryExpanded()
                        },
                        label = { Text("Categoría") },
                        leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isCategoryExpanded) },
                        readOnly = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = state.isCategoryExpanded,
                        onDismissRequest = { viewModel.toggleCategoryExpanded() }
                    ) {
                        viewModel.getFilteredCategories(searchQuery).forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    viewModel.updateSelectedCategory(category)
                                    searchQuery = category.name
                                    viewModel.toggleCategoryExpanded()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sección de imágenes
                Text(
                    text = "Personalización",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Banner
                ImageUploadCard(
                    title = "Imagen de Banner",
                    imageUri = state.bannerImageUri,
                    onImageSelected = { viewModel.updateBannerImage(it) },
                    aspectRatio = 16f / 9f,
                    height = 180
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Profile Image
                ImageUploadCard(
                    title = "Imagen de Perfil",
                    imageUri = state.profileImageUri,
                    onImageSelected = { viewModel.updateProfileImage(it) },
                    aspectRatio = 1f,
                    height = 200
                )

                Spacer(modifier = Modifier.height(24.dp))

                MainButton(
                    text = "Crear Emprendimiento",
                    onClick = { viewModel.saveEntrepreneurship() },
                    enabled = state.name.isNotEmpty() &&
                            state.email.isNotEmpty() &&
                            state.selectedCategory != null,
                    leftIcon = Icons.Default.Save
                )
            }
        }
    )
}

