package com.codeoflegends.unimarket.features.auth.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme
import com.codeoflegends.unimarket.R
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.features.auth.ui.components.RequirePermission

@Composable
fun RoleSelectionScreen(
    manager: NavigationManager,
) {
    val isDark = isSystemInDarkTheme()
    
    MainLayout {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Header Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Bienvenido!",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "Selecciona tu rol",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        ),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Arrow Image
                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "Flecha",
                        modifier = Modifier.size(40.dp),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.primary
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Role Selection Images
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Cliente Image
                    Image(
                        painter = painterResource(id = R.drawable.ic_buyer),
                        contentDescription = "Cliente",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clickable {
                                manager.authViewModel.selectRole("Shopper")
                                manager.navController.navigate(Routes.Home.route)
                            }
                    )
                    
                    // Emprendedor Image
                    RequirePermission(
                        "Emprendedor",
                        manager,
                        fallback = { 
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                VerifyButton()
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_entrepreneur),
                            contentDescription = "Emprendedor",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clickable {
                                    manager.authViewModel.selectRole("Emprendedor")
                                    manager.navController.navigate(Routes.EntrepreneurProfile.route)
                                }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Logout Button
                MainButton(
                    text = "Cerrar Sesión",
                    onClick = { 
                        manager.authViewModel.logout()
                        manager.navController.navigate(Routes.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    height = 48
                )
            }
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