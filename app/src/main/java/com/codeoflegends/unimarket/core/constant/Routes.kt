package com.codeoflegends.unimarket.core.constant

sealed class Routes(val route: String, val requiredPermission: String? = null) {
    // Auth Routes
    data object Login : Routes("/login")
    data object Register : Routes("/register")
    data object ForgotPassword : Routes("/forgot_password")

    // Home Routes
    data object Home : Routes("/")
    data object HomeSearch : Routes("/home/search")

    // Cart Route
    data object Cart : Routes("/cart")

    // Product Routes
    data object ManageProduct : Routes("/manage/product/{id}") {
        val base get() = "/manage/product/"
        fun createRoute(id: String): String {
            return "/manage/product/$id"
        }
    }

    data object CreateProduct : Routes("/create/product/{entrepreneurshipId}") {
        val base get() = "/create/product/"
        fun createRoute(entrepreneurshipId: String): String {
            return "/create/product/$entrepreneurshipId"
        }
    }

    data object ProductView : Routes("/product/{id}") {
        val base get() = "/product/"
        fun createRoute(id: String): String {
            return "/product/$id"
        }
    }

    data object ProductBuyerView : Routes("/product_buyer/{id}") {
        val base get() = "/product_buyer/"
        fun createRoute(id: String): String {
            return "/product_buyer/$id"
        }
    }

    // Order Routes

    data object ManageOrder : Routes("/manage/order") {
        val base get() = "/manage/order/"
    }

    data object OrderSummary : Routes("/order/{id}") {
        val base get() = "/order/"
        fun createRoute(id: String): String {
            return "/order/$id"
        }
    }

        // Role Selection Route
        data object RoleSelection : Routes("/role_selection")

        data object ManageEntrepreneurship : Routes("/manage/entrepreneurship/{id}") {
            val base get() = "/manage/entrepreneurship/"
            fun createRoute(id: String): String {
                return "/manage/entrepreneurship/$id"
            }
        }

        data object EntrepreneurshipView : Routes("/entrepreneurship/{id}") {
            val base get() = "/entrepreneurship/"
            fun createRoute(id: String): String {
                return "/entrepreneurship/$id"
            }
        }

        data object EditProfile : Routes("/edit_profile/{id}") {
            val base get() = "/edit_profile/"
            fun createRoute(id: String): String {
                return "/edit_profile/$id"
            }
        }

        data object EntrepreneurshipForm : Routes("/entrepreneurship/form")

        data object EntrepreneurProfile : Routes("/entrepreneur")

        data object Collaborators : Routes("/entrepreneurship/{id}/collaborators", "partner.read") {
            val base get() = "/entrepreneurship/"
            fun createRoute(id: String): String {
                return "/entrepreneurship/$id/collaborators"
            }
        }

        companion object {
            fun fromRoute(route: String): Routes? = when (route) {
                Login.route -> Login
                Register.route -> Register
                ForgotPassword.route -> ForgotPassword
                Home.route -> Home
                ManageProduct.route -> ManageProduct
                CreateProduct.route -> CreateProduct
                ProductView.route -> ProductView
                ProductBuyerView.route -> ProductBuyerView
                RoleSelection.route -> RoleSelection
                ManageEntrepreneurship.route -> ManageEntrepreneurship
                EntrepreneurshipView.route -> EntrepreneurshipView
                EntrepreneurshipForm.route -> EntrepreneurshipForm
                EntrepreneurProfile.route -> EntrepreneurProfile
                Collaborators.route -> Collaborators
                HomeSearch.route -> HomeSearch
                else -> {
                    // Handle dynamic routes
                    when {
                        route.startsWith("/manage/product/") -> ManageProduct
                        route.startsWith("/create/product/") -> CreateProduct
                        route.startsWith("/product/") -> ProductView
                        route.startsWith("/product_buyer/") -> ProductBuyerView
                        route.startsWith("/entrepreneurship/") && route.endsWith("/collaborators") -> Collaborators
                        else -> null
                    }
                }
            }
        }
    }