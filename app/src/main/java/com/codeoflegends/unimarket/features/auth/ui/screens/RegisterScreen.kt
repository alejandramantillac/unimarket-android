package com.codeoflegends.unimarket.features.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.CollectAsEventEffect
import com.codeoflegends.unimarket.core.extension.navigateIfAuthorized
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.core.ui.components.MessageSnackbar

@Composable
fun RegisterScreen(
    manager: NavigationManager = NavigationManager(rememberNavController(), viewModel()),
    next: String = "/"
) {
    val isLoading by manager.authViewModel.authState.collectAsState()
        .run { remember { derivedStateOf { value.isLoading } } }

    var showMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    manager.authViewModel.registerEvent.CollectAsEventEffect {
        when (it) {
            is AuthResult.Success -> {
                message = "Registro exitoso"
                isError = false
                showMessage = true
            }

            is AuthResult.Error -> {
                message = it.exception.message ?: "Error al registrarse"
                isError = true
                showMessage = true
            }

            else -> {}
        }
    }

    MainLayout(
        barOptions = AppBarOptions(
            show = true, showBackButton = true, onBackClick = {
                manager.navController.popBackStack()
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                text = "Regístrate",
                style = MaterialTheme.typography.headlineMedium
            )

            SimpleTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f),
            )

            SimpleTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f),
            )

            MainButton(
                text = "Registrar",
                isLoading = isLoading,
                onClick = {
                    manager.authViewModel.register(email, password)
                },
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth(0.8f),
            )

            ClickableTextLink(
                text = "¿Ya tienes una cuenta? Inicia sesión",
                onClick = {
                    manager.navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Register.route) { inclusive = true }
                    }
                },
            )
            
            if (showMessage) {
                MessageSnackbar(
                    message = message,
                    isError = isError,
                    actionLabel = if (!isError) "Ir a Login" else null,
                    onAction = {
                        if (!isError) {
                            manager.navController.navigate(Routes.Login.route) {
                                popUpTo(Routes.Register.route) { inclusive = true }
                            }
                        }
                    },
                    onDismiss = { showMessage = false }
                )
            }
        }
    }
}