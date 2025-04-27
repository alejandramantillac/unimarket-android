package com.codeoflegends.unimarket.features.auth.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.CollectAsEventEffect
import com.codeoflegends.unimarket.core.extension.navigateIfAuthorized
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.codeoflegends.unimarket.features.home.components.AuthButton
import com.codeoflegends.unimarket.features.home.components.AuthTextField
import com.codeoflegends.unimarket.features.home.components.ClickableTextLink
import com.codeoflegends.unimarket.features.home.components.ErrorDialog

@Composable
fun LoginScreen(
    manager: NavigationManager = NavigationManager(rememberNavController(), viewModel()),
    next: String = "/"
) {
    val isLoading by manager.authViewModel.authState.collectAsState()
        .run { remember { derivedStateOf { value.isLoading } } }

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    manager.authViewModel.loginEvent.CollectAsEventEffect {
        when (it) {
            is AuthResult.Success -> {
                manager.navController.navigateIfAuthorized(next, manager) {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
            }
            is AuthResult.Error -> {
                errorMessage = it.exception.message ?: "Error de autenticación"
                showErrorDialog = true
            }
            else -> {}
        }
    }

    ErrorDialog(show = showErrorDialog, message = errorMessage) {
        showErrorDialog = false
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¡Bienvenido a Unimarket!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )

                Text(
                    text = "Inicia sesión para continuar con tus compras",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
                )

                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo"
                )

                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    visualTransformation = PasswordVisualTransformation()
                )

                ClickableTextLink(
                    text = "¿Olvidaste tu contraseña?",
                    onClick = { manager.navController.navigate(Routes.ForgotPassword.route) },
                    alignEnd = true
                )

                AuthButton(
                    text = "Iniciar Sesión",
                    onClick = { manager.authViewModel.login(email, password) }
                )

                ClickableTextLink(
                    text = "¿No tienes una cuenta? Regístrate",
                    onClick = { manager.navController.navigate(Routes.Register.route) }
                )
            }

            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
