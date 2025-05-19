package com.codeoflegends.unimarket.features.profile.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.ImagePicker

@Composable
fun ProfileScreenPreview() {
    var fullName by remember { mutableStateOf("Juan Pérez") }
    var email by remember { mutableStateOf("juan.perez@email.com") }
    var password by remember { mutableStateOf("123456") }
    var confirmPassword by remember { mutableStateOf("123456") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImagePicker(
            selectedImageUri = profileImageUri,
            onImageSelected = { profileImageUri = it },
            modifier = Modifier
                .size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            onClick = { /* acción de ejemplo */ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreenPreview()
}
