package com.codeoflegends.unimarket.features.auth.data.repositories.impl

import com.codeoflegends.unimarket.features.auth.data.model.request.LoginRequest
import com.codeoflegends.unimarket.features.auth.data.service.AuthService
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthState
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType
import com.codeoflegends.unimarket.features.auth.data.model.request.RegisterRequest
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult<Unit> {
        return try {
            val response = authService.login(LoginRequest(email, password))

            tokenRepository.saveTokens(
                response.data.access_token,
                response.data.refresh_token
            )

            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            authService.register(RegisterRequest(email, password))
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun logout(): AuthResult<Unit> {
        return try {
            tokenRepository.clearTokens()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override fun observeAuthState(): Flow<AuthState> {
        return tokenRepository.getAccessTokenFlow().map { token ->
            if (token != null) {
                AuthState(AuthStateType.AUTHENTICATED)
            } else {
                AuthState(AuthStateType.ANONYMOUS)
            }
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return tokenRepository.hasValidToken()
    }
}