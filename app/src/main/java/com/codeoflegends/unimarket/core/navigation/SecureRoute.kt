package com.codeoflegends.unimarket.core.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType

/**
 * Componente que verifica si el usuario tiene los permisos necesarios para acceder a una ruta.
 * Si no los tiene, redirige a la ruta de fallback.
 */
@Composable
fun SecureRoute(
    requiredPermission: String?,
    manager: NavigationManager,
    fallbackRoute: String,
    content: @Composable () -> Unit
) {
    val authState by manager.authViewModel.authState.collectAsState()

    Log.d("SecureRoute", "AuthState: ${authState.authorities}")
    
    // Mostrar contenido solo cuando el estado no es IDLE
    if (authState.state != AuthStateType.IDLE) {
        // Verificar si el usuario está autenticado y tiene los permisos necesarios
        val hasAccess = authState.state == AuthStateType.AUTHENTICATED && 
                       (requiredPermission == null || authState.hasPermission(requiredPermission))
        
        LaunchedEffect(hasAccess) {
            if (!hasAccess) {
                // Redirigir al usuario a la ruta de fallback si no tiene acceso
                manager.navController.navigate(fallbackRoute) {
                    popUpTo(manager.navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        
        if (hasAccess) {
            content()
        }
    } else {
        // Mostrar un indicador de carga mientras el estado es IDLE
        // No hacer ninguna navegación hasta que el estado cambie
        Log.d("SecureRoute", "Esperando a que el estado de autenticación se estabilice...")
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}