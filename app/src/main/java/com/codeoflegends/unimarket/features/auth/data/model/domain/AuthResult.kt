package com.codeoflegends.unimarket.features.auth.data.model.domain

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val exception: Exception) : AuthResult<Nothing>()
    data object Loading : AuthResult<Nothing>()
}