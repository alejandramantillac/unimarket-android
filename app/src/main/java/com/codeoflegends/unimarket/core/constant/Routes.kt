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
    data object ProductForm : Routes("/product_form")
    
    companion object {
        fun fromRoute(route: String): Routes? = when(route) {
            Login.route -> Login
            Register.route -> Register
            ForgotPassword.route -> ForgotPassword
            Home.route -> Home
            UserProfile.route -> UserProfile
            ProductForm.route -> ProductForm
            else -> null
        }
    }
}