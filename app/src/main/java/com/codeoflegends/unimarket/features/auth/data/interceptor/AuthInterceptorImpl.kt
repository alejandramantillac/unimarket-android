package com.codeoflegends.unimarket.features.auth.data.interceptor

import com.codeoflegends.unimarket.config.AuthInterceptor
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptorImpl @Inject constructor(
    private val tokenRepository: TokenRepository
) : AuthInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (isAuthenticationRequest(originalRequest)) {
            return chain.proceed(originalRequest)
        }

        val token = runBlocking { tokenRepository.getAccessToken() }

        return if (token.isNullOrEmpty()) {
            chain.proceed(originalRequest)
        } else {
            val authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(authenticatedRequest)
        }
    }

    private fun isAuthenticationRequest(request: Request): Boolean {
        val url = request.url.toString()
        return url.contains("/auth/login") || url.contains("/auth/refresh")
    }
}