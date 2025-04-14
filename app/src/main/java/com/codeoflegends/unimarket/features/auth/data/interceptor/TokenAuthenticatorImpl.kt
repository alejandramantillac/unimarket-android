package com.codeoflegends.unimarket.features.auth.data.interceptor

import com.codeoflegends.unimarket.config.TokenAuthenticator
import com.codeoflegends.unimarket.features.auth.data.service.AuthService
import com.codeoflegends.unimarket.features.auth.data.model.request.RefreshTokenRequest
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticatorImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authService: AuthService
) : TokenAuthenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Retry-Auth") != null) {
            return null
        }

        val newToken = runBlocking {
            try {
                val refreshToken = tokenRepository.getRefreshToken() ?: return@runBlocking null

                val refreshResponse = authService.refreshToken(RefreshTokenRequest(refreshToken))

                tokenRepository.saveTokens(
                    refreshResponse.data.access_token,
                    refreshResponse.data.refresh_token
                )

                refreshResponse.data.access_token
            } catch (e: Exception) {
                tokenRepository.clearTokens()
                null
            }
        }

        return if (newToken.isNullOrEmpty()) {
            null
        } else {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .header("Retry-Auth", "true")
                .build()
        }
    }
}
