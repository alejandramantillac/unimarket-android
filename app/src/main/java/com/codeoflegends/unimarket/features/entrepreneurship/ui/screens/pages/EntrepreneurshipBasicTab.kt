package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.DropdownMenuBox
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.ImagePicker
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel

@Composable
fun EntrepreneurshipBasicTab(state: EntrepreneurshipUiState, viewModel: EntrepreneurshipViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {

        SimpleTextField(
            value = state.entrepreneurshipName,
            onValueChange = { viewModel.onNameChanged(it) },
            label = "Nombre del Emprendimiento",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SimpleTextField(
            value = state.entrepreneurshipSlogan,
            onValueChange = { viewModel.onSloganChanged(it) },
            label = "Slogan",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SimpleTextField(
            value = state.entrepreneurshipDescription,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            label = "Descripción del Emprendimiento",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        SimpleTextField(
            value = state.entrepreneurshipEmail,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = "Correo electrónico",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SimpleTextField(
            value = state.entrepreneurshipPhone,
            onValueChange = { viewModel.onPhoneChanged(it) },
            label = "Teléfono de contacto",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuBox(
            label = "Categoría",
            options = state.categoryOptions,
            selectedOption = state.selectedCategory,
            onOptionSelected = { viewModel.onCategorySelected(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ImagePicker(
            selectedImageUri = state.entrepreneurshipImageUri,
            onImageSelected = { viewModel.onImageSelected(it) }
        )

    }
}
