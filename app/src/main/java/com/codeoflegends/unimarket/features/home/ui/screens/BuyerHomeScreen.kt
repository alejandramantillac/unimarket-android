package com.codeoflegends.unimarket.features.home.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType

@Composable
fun BuyerHomeScreen(
    manager: NavigationManager = NavigationManager(rememberNavController(), viewModel()),
) {
    val authState by manager.authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido a UniMarket!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (authState.state == AuthStateType.AUTHENTICATED) {
                    Text(
                        text = "Usuario: ${authState.userId}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Rol: ${authState.authorities}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "No estás autenticado",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        MainButton(
            onClick = { manager.navController.navigate(Routes.ProductForm.route) },
            modifier = Modifier.fillMaxWidth(),
            text = "Crear/Editar Producto"
        )

        MainButton(
            onClick = { manager.authViewModel.logout() },
            modifier = Modifier.fillMaxWidth(),
            text = "Cerrar sesión"
        )
    }
}
