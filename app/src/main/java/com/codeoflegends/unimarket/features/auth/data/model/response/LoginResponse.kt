package com.codeoflegends.unimarket.features.auth.data.model.response

data class LoginResponse (
    val data: LoginResponseData,
)

data class LoginResponseData (
    val expires: Int,
    val refresh_token: String,
    val access_token: String,
)