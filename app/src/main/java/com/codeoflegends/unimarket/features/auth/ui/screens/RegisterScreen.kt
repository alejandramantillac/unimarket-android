package com.codeoflegends.unimarket.features.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.CollectAsEventEffect
import com.codeoflegends.unimarket.core.extension.navigateIfAuthorized
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import com.codeoflegends.unimarket.features.home.components.AuthButton
import com.codeoflegends.unimarket.features.home.components.AuthTextField
import com.codeoflegends.unimarket.features.home.components.ClickableTextLink
import com.codeoflegends.unimarket.features.home.components.ErrorDialog

@Composable
fun RegisterScreen(
    manager: NavigationManager = NavigationManager(rememberNavController(), viewModel()),
    next: String = "/"
) {
    val isLoading by manager.authViewModel.authState.collectAsState()
        .run { remember { derivedStateOf { value.isLoading } } }

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    manager.authViewModel.registerEvent.CollectAsEventEffect {
        when(it) {
            is AuthResult.Success -> {
                manager.navController.navigateIfAuthorized(next, manager) {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
            }
            is AuthResult.Error -> {
                errorMessage = it.exception.message ?: "Error al registrarse"
                showErrorDialog = true
            }
            else -> {}
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

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                modifier = Modifier.padding(8.dp).fillMaxWidth(0.8f),
            )

            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                modifier = Modifier.padding(8.dp).fillMaxWidth(0.8f),
            )

            AuthButton(
                text = if (isLoading) "Cargando..." else "Registrar",
                onClick = {
                    if (!isLoading) manager.authViewModel.register(email, password)
                },
                modifier = Modifier.padding(14.dp).fillMaxWidth(0.8f),
            )

            ClickableTextLink(
                text = "¿Ya tienes una cuenta? Inicia sesión",
                onClick = { manager.navController.navigate(Routes.Login.route) },
                alignEnd = false
            )
        }
    }


    ErrorDialog(
        show = showErrorDialog,
        message = errorMessage,
        onDismiss = { showErrorDialog = false }
    )
}
