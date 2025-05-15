package com.codeoflegends.unimarket.core.constant

sealed class Routes(val route: String, val requiredPermission: String? = null) {
    // Auth Routes
    data object Login : Routes("/login")
    data object Register : Routes("/register")
    data object ForgotPassword : Routes("/forgot_password")
    
    // Home Routes
    data object Home : Routes("/")
    
    // User Routes
    data object UserProfile : Routes("/user/{id}", "VIEW_PROFILE")
    
    // Product Routes
    data object ProductFormCreate : Routes("/product/create")
    data object ProductFormEdit : Routes("/product/edit/{id}")
    
    companion object {
        fun fromRoute(route: String): Routes? = when(route) {
            Login.route -> Login
            Register.route -> Register
            ForgotPassword.route -> ForgotPassword
            Home.route -> Home
            UserProfile.route -> UserProfile
            ProductFormCreate.route -> ProductFormCreate
            else -> {
                when {
                    route.startsWith("/product/edit/") -> ProductFormEdit
                    route.startsWith("/user/") -> UserProfile
                    else -> null
                }
            }
        }
    }
}