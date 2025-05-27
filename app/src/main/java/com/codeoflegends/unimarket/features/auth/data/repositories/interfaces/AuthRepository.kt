package com.codeoflegends.unimarket.features.auth.data.repositories.interfaces

import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthState
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult<Unit>
    suspend fun register(email: String, password: String): AuthResult<Unit>
    suspend fun logout(): AuthResult<Unit>
    fun observeAuthState(): Flow<AuthState>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun forgotPassword(email: String): AuthResult<Unit>
    suspend fun getCurrentUserId(): UUID?
}