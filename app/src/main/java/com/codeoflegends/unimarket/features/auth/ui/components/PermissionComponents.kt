package com.codeoflegends.unimarket.features.auth.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType

/**
 * Componente que renderiza su contenido solo si el usuario tiene el permiso especificado.
 * 
 * @param permission El permiso requerido para mostrar el contenido
 * @param manager El NavigationManager que contiene el AuthViewModel
 * @param modifier Modificador opcional para el contenido
 * @param fallback Contenido opcional a mostrar si el usuario no tiene el permiso requerido
 * @param content El contenido a mostrar si el usuario tiene el permiso requerido
 */
@Composable
fun RequirePermission(
    permission: String,
    manager: NavigationManager,
    modifier: Modifier = Modifier,
    fallback: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val authState by manager.authViewModel.authState.collectAsState()
    
    if (authState.state == AuthStateType.AUTHENTICATED && authState.hasPermission(permission)) {
        content()
    } else {
        fallback?.invoke()
    }
}

/**
 * Componente que renderiza su contenido solo si el usuario está autenticado.
 * 
 * @param manager El NavigationManager que contiene el AuthViewModel
 * @param modifier Modificador opcional para el contenido
 * @param fallback Contenido opcional a mostrar si el usuario no está autenticado
 * @param content El contenido a mostrar si el usuario está autenticado
 */
@Composable
fun RequireAuth(
    manager: NavigationManager,
    modifier: Modifier = Modifier,
    fallback: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val authState by manager.authViewModel.authState.collectAsState()
    
    if (authState.state == AuthStateType.AUTHENTICATED) {
        content()
    } else {
        fallback?.invoke()
    }
}