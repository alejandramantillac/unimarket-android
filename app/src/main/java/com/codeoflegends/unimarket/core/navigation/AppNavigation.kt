package com.codeoflegends.unimarket.core.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.extension.secureComposable
import com.codeoflegends.unimarket.features.auth.navigation.authNavigation
import com.codeoflegends.unimarket.features.auth.ui.viewModel.AuthViewModel
import com.codeoflegends.unimarket.features.auth.ui.components.RequirePermission
import com.codeoflegends.unimarket.features.home.navigation.homeNavigation
import com.codeoflegends.unimarket.features.product.navigation.productNavigation

@Composable
fun AppNavigation(
    manager: NavigationManager,
    startDestination: String,
) {
    NavHost(
        navController = manager.navController,
        startDestination = startDestination
    ) {

        // Auth routes
        authNavigation(manager)
        homeNavigation(manager)
        productNavigation(manager)

        /* Ejemplos de uso de secureComposable
        secureComposable(
            route = Routes.Home.route,
            manager = manager
        ) {
            /* Aquí se pondría la pantalla, dejé este ejemplo simple para que se vea el uso */
            Text("Home")
            Button(onClick = {
                manager.authViewModel.logout()
            }) {
                Text("Logout")
            }
        }

         */

        /*Es lo mismo de arriba, solo que con permisos requeridos, solo para que se vea el uso */
        secureComposable(
            route = Routes.UserProfile.route,
            requiredPermission = Routes.UserProfile.requiredPermission,
            manager = manager,
            fallbackRoute = Routes.Home.route
        ) {
            Text("Perfil de Usuario")
            /* Componente para renderizar el contenido solo si el usuario tiene ciertos permisos */
            RequirePermission(
                permission = "EDIT_PROFILE",
                manager = manager,
                fallback = { Text("No tienes permisos para editar") }
            ) {
                Button(onClick = {}) {
                    Text("Editar Perfil")
                }
            }
        }
    }
}

class NavigationManager(
    val navController: NavHostController,
    val authViewModel: AuthViewModel
)