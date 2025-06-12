package com.codeoflegends.unimarket.features.home.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.MainLayout

@Composable
fun SettingsScreen(
    manager: NavigationManager
) {

    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            showBackButton = true,
            title = "Configuración",
            onBackClick = { manager.navController.navigateUp() }
        ),
        addPadding = true
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                // Card para ver mis ordenes
                Card(
                    shape = MaterialTheme.shapes.large,
                    onClick = {
                        manager.navController.navigate(Routes.ManageHistory.route)
                    }
                ) {
                    Text(
                        text = "Mis órdenes",
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            MainButton("Cerrar sesión", onClick = {
                manager.authViewModel.logout()
            })
        }
    }

}