package com.codeoflegends.unimarket.features.auth.data.service

import com.codeoflegends.unimarket.features.auth.data.model.request.LoginRequest
import com.codeoflegends.unimarket.features.auth.data.model.response.LoginResponse
import com.codeoflegends.unimarket.features.auth.data.model.request.RefreshTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): LoginResponse
}