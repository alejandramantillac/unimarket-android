package com.codeoflegends.unimarket.features.home.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType

@Composable
fun BuyerHomeScreen(
    manager: NavigationManager = NavigationManager(rememberNavController(), viewModel()),
    next: String = "/"
) {
    val isLoading by manager.authViewModel.authState.collectAsState()
        .run { remember { derivedStateOf { value.isLoading } } }

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val authState by manager.authViewModel.authState.collectAsState()

    // Mostrar información del token solo si el usuario está autenticado
    val logedUserInfo = if (authState.state == AuthStateType.AUTHENTICATED) {
        "User ID: ${authState.userId}\nRoles: ${authState.authorities}"
    } else {
        "No estás autenticado"
    }

    Text(
        text = logedUserInfo,
        modifier = Modifier.fillMaxWidth()
    )


}