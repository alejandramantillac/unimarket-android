package com.codeoflegends.unimarket.features.auth.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.R
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.foundation.isSystemInDarkTheme
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.features.auth.ui.components.RequirePermission

@Composable
fun RoleSelectionScreen(
    manager: NavigationManager,
) {
    val isDark = isSystemInDarkTheme()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "¡Bienvenido!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "Selecciona tu rol",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 0.dp, bottom = 20.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = "Flecha",
            modifier = Modifier
                .size(60.dp)
                .padding(vertical = 8.dp),
            colorFilter = ColorFilter.tint(
                if (isDark) MaterialTheme.colorScheme.onBackground else Color.Black
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Opción Cliente
        Image(
            painter = painterResource(id = R.drawable.ic_buyer),
            contentDescription = "Cliente",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable {
                    manager.authViewModel.selectRole("Shopper")
                    manager.navController.navigate(Routes.Home.route)
                }
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Opción Emprendedor
        RequirePermission(
            "Entrepreneur",
            manager,
            fallback = { VerifyButton() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_entrepreneur),
                contentDescription = "Emprendedor",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable {
                        manager.authViewModel.selectRole("Entrepreneur")
                        manager.navController.navigate(Routes.ManageProduct.base)
                    }
            )
        }
    }
}

@Composable
fun VerifyButton() {
    ClickableTextLink("¿Deseas comenzar a emprender?",
        onClick = {
            //TODO: Navegar a las pantallas de navegación
        },
    )
}