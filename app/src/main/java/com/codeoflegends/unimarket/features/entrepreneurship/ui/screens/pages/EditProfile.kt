package com.codeoflegends.unimarket.features.profile.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.ImagePicker
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipUiState
import com.codeoflegends.unimarket.core.data.dto.UserDto


@Composable
fun EditProfileScreen(viewModel: EntrepreneurshipViewModel, entrepreneurshipId: String) {

    LaunchedEffect(entrepreneurshipId) {
        viewModel.loadEntrepreneurshipWithFounder(entrepreneurshipId)
    }


    val selectedEntrepreneurship by viewModel.selectedEntrepreneurship.collectAsState()


    val user = selectedEntrepreneurship?.founder


    var fullName by remember(user) {
        mutableStateOf("${user?.firstName.orEmpty()} ${user?.lastName.orEmpty()}")
    }
    var email by remember(user) { mutableStateOf(user?.email.orEmpty()) }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SimpleTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = "Nombre Completo",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SimpleTextField(
            value = email,
            onValueChange = { email = it },
            label = "Correo Electrónico",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SimpleTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SimpleTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirmar Contraseña",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        MainButton(
            text = "Guardar Cambios",
            onClick = {

                if (password != confirmPassword) {
                    return@MainButton
                }

                val names = fullName.split(" ", limit = 2)
                val firstName = names.getOrNull(0).orEmpty()
                val lastName = names.getOrNull(1).orEmpty()

                viewModel.updateUserProfile(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = if (password.isBlank()) null else password
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}