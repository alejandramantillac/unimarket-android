package com.codeoflegends.unimarket.features.auth.data.service

import com.codeoflegends.unimarket.features.auth.data.model.request.ForgotPasswordRequest
import com.codeoflegends.unimarket.features.auth.data.model.request.LoginRequest
import com.codeoflegends.unimarket.features.auth.data.model.request.RegisterRequest
import com.codeoflegends.unimarket.features.auth.data.model.response.LoginResponse
import com.codeoflegends.unimarket.features.auth.data.model.request.RefreshTokenRequest
import com.codeoflegends.unimarket.features.auth.data.model.response.ForgotPasswordResponse
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): LoginResponse

    @POST("/users/register")
    suspend fun register(@Body request: RegisterRequest): Unit

    @POST("/auth/forgot-password")
    suspend fun forgotPassword(@Body response: ForgotPasswordRequest): ForgotPasswordResponse
}