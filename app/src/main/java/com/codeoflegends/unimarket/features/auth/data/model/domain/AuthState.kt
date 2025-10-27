package com.codeoflegends.unimarket.features.auth.data.model.domain

import android.util.Log

data class AuthState(
    val state: AuthStateType = AuthStateType.IDLE,
    val isLoading: Boolean = false,
    val error: String? = null,
    val authorities: String = "",
    val userId: String = "",
) {
    fun hasPermission(permission: String): Boolean {
        val hasIt = authorities.contains(permission)
        Log.d("AuthState", "=== Verificando permiso ===")
        Log.d("AuthState", "Permiso requerido: '$permission'")
        Log.d("AuthState", "Authorities actuales: '$authorities'")
        Log.d("AuthState", "¿Tiene permiso?: $hasIt")
        return hasIt
    }
}

enum class AuthStateType {
    AUTHENTICATED, ANONYMOUS, EXPIRED, IDLE
}