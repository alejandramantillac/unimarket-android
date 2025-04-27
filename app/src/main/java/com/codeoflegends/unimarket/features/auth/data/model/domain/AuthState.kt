package com.codeoflegends.unimarket.features.auth.data.model.domain

data class AuthState(
    val state: AuthStateType = AuthStateType.IDLE,
    val isLoading: Boolean = false,
    val error: String? = null,
    val authorities: String = "",
    val userId: String = "",
) {
    fun hasPermission(permission: String): Boolean {
        return authorities.contains(permission)
    }
}

enum class AuthStateType {
    AUTHENTICATED, ANONYMOUS, EXPIRED, IDLE
}