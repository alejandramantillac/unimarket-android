package com.codeoflegends.unimarket.features.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.CollectAsEventEffect
import com.codeoflegends.unimarket.core.extension.navigateIfAuthorized
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.core.ui.components.MessageSnackbar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import com.codeoflegends.unimarket.R
import com.codeoflegends.unimarket.features.auth.ui.components.PasswordTextField

@Composable
fun LoginScreen(
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

    manager.authViewModel.loginEvent.CollectAsEventEffect {
        when (it) {
            is AuthResult.Success -> {
                manager.navController.navigateIfAuthorized(next, manager) {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
            }

            is AuthResult.Error -> {
                message = it.exception.message ?: "Error de autenticación"
                isError = true
                showMessage = true
            }

            else -> {}
        }
    }

    MainLayout(pageLoading=isLoading) {
        Box(
            modifier = Modifier
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
                Image(
                    painter = painterResource(id = R.drawable.logo_unimarket_playstore),
                    contentDescription = "Logo Unimarket",
                    modifier = Modifier
                        .size(130.dp)
                        .padding(bottom = 24.dp)
                )

                Text(
                    text = "¡Bienvenido a Unimarket!",
                    style = MaterialTheme.typography.headlineMedium,
                )

                Text(
                    text = "Inicia sesión para continuar con tus compras",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
                )

                SimpleTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo"
                )

                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                )

                ClickableTextLink(
                    text = "¿Olvidaste tu contraseña?",
                    onClick = { manager.navController.navigate(Routes.ForgotPassword.route) },
                    alignment = Alignment.CenterEnd
                )

                MainButton(
                    text = "Iniciar Sesión",
                    onClick = { manager.authViewModel.login(email, password) }
                )

                ClickableTextLink(
                    text = "¿No tienes una cuenta? Regístrate",
                    onClick = { manager.navController.navigate(Routes.Register.route) }
                )
                
                if (showMessage) {
                    MessageSnackbar(
                        message = message,
                        isError = isError,
                        actionLabel = null,
                        onDismiss = { showMessage = false }
                    )
                }
            }
        }
    }
}