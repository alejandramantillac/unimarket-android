package com.codeoflegends.unimarket.core.constant

sealed class Routes(val route: String, val requiredPermission: String? = null) {
    // Auth Routes
    data object Login : Routes("/login")
    data object Register : Routes("/register")
    data object ForgotPassword : Routes("/forgot_password")

    // Home Routes
    data object Home : Routes("/")

    // Product Routes
    data object ManageProduct : Routes("/manage/product/{id}") {
        val base get() = "/manage/product/"
        fun createRoute(id: String): String {
            return "/manage/product/$id"
        }
    }

    data object ProductView : Routes("/product/{id}") {
        val base get() = "/product/"
        fun createRoute(id: String): String {
            return "/product/$id"
        }
    }

    companion object {
        fun fromRoute(route: String): Routes? = when (route) {
            Login.route -> Login
            Register.route -> Register
            ForgotPassword.route -> ForgotPassword
            Home.route -> Home
            ManageProduct.route -> ManageProduct
            ProductView.route -> ProductView
            else -> {
                // Handle dynamic routes
                when {
                    route.startsWith("/manage/product/") -> ManageProduct
                    route.startsWith("/product/") -> ProductView
                    else -> null
                }
            }
        }
    }
}