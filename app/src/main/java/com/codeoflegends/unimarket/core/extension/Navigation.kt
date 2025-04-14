package com.codeoflegends.unimarket.core.extension

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.navigation.SecureRoute
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType

/**
 * Extensión de NavGraphBuilder que agrega una ruta segura que verifica permisos antes de permitir la navegación.
 *
 * @param route La ruta a la que se aplica la seguridad
 * @param requiredPermission El permiso requerido para acceder a la ruta (opcional)
 * @param manager El NavigationManager que contiene el AuthViewModel
 * @param fallbackRoute La ruta a la que redirigir si el usuario no tiene los permisos necesarios
 * @param content El contenido a mostrar si el usuario tiene los permisos necesarios
 */
fun NavGraphBuilder.secureComposable(
    route: String,
    requiredPermission: String? = null,
    manager: NavigationManager,
    fallbackRoute: String = Routes.Login.route,
    content: @Composable () -> Unit
) {
    composable(route) {
        SecureRoute(
            requiredPermission = requiredPermission,
            manager = manager,
            fallbackRoute = fallbackRoute,
            content = content
        )
    }
}

/**
 * Extensión de NavHostController que navega a una ruta solo si el usuario tiene los permisos necesarios.
 *
 * @param route La ruta a la que navegar
 * @param manager El NavigationManager que contiene el AuthViewModel
 * @param navOptions Lambda opcional con opciones de navegación
 * @return true si la navegación fue exitosa, false en caso contrario
 */
fun NavHostController.navigateIfAuthorized(
    route: String,
    manager: NavigationManager,
    navOptions: (androidx.navigation.NavOptionsBuilder.() -> Unit)? = null
): Boolean {
    val routeObj = Routes.fromRoute(route) ?: return false
    val authState = manager.authViewModel.authState.value

    // Si el estado es IDLE, no tomar decisiones de navegación todavía
    if (authState.state == AuthStateType.IDLE) {
        Log.d("NavigateIfAuthorized", "Estado de autenticación en IDLE, esperando...")
        return false
    }

    val hasAccess = authState.state == AuthStateType.AUTHENTICATED &&
            (routeObj.requiredPermission == null || authState.hasPermission(routeObj.requiredPermission))

    if (hasAccess) {
        if (navOptions != null) {
            this.navigate(route, navOptions)
        } else {
            this.navigate(route)
        }
        return true
    }

    return false
}