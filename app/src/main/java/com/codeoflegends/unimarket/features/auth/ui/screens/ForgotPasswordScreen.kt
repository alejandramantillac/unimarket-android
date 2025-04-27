package com.codeoflegends.unimarket.features.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(manager: NavigationManager, route: String) {

    var email by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    fun onSend(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = manager.authViewModel.sendForgotPasswordRequest(email)
            when (result) {
                is AuthResult.Success -> {
                    successMessage = "Correo enviado exitosamente. Revisa tu bandeja de entrada."
                    errorMessage = ""
                }
                is AuthResult.Error -> {
                    errorMessage = "Error al enviar la solicitud: ${result.exception.message ?: "Error desconocido"}"
                    successMessage = ""
                }

                AuthResult.Loading -> TODO()
            }
        }
    }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Forgot Password", style = MaterialTheme.typography.headlineLarge)

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        onSend(email)
                    }, shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send")
                }

                if (successMessage.isNotEmpty()) {
                    Text(text = successMessage, color = MaterialTheme.colorScheme.primary)
                }

                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}