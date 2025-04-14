package com.codeoflegends.unimarket.features.auth.data.repositories.interfaces

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()
    fun getAccessTokenFlow(): Flow<String?>
    suspend fun hasValidToken(): Boolean
}