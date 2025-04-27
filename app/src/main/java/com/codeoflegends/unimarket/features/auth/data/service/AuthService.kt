package com.codeoflegends.unimarket.features.auth.data.service

import com.codeoflegends.unimarket.features.auth.data.model.request.LoginRequest
import com.codeoflegends.unimarket.features.auth.data.model.request.RegisterRequest
import com.codeoflegends.unimarket.features.auth.data.model.response.LoginResponse
import com.codeoflegends.unimarket.features.auth.data.model.request.RefreshTokenRequest
import com.codeoflegends.unimarket.features.auth.data.model.response.RoleResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): LoginResponse

    @POST("/users/register")
    suspend fun register(@Body request: RegisterRequest): Unit

    @GET("/roles")
    suspend fun getRoles(): RoleResponse
}