package com.codeoflegends.unimarket.features.auth.data.model.request

data class RegisterRequest(
    val email: String,
    val password: String,
    val first_name: String,
    val last_name: String
)